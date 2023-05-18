package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics.Strategy;
import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.EvaluationRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectEvaluationRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamEvaluationRepository;
import com.upc.gessi.loganalytics.app.rest.DTOs.EvaluationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EvaluationController {

    @Autowired
    EvaluationRepository evaluationRepository;
    @Autowired
    SubjectEvaluationRepository subjectEvaluationRepository;
    @Autowired
    TeamEvaluationRepository teamEvaluationRepository;

    @Autowired
    InternalMetricController internalMetricController;

    @Autowired
    TeamController teamController;
    @Autowired
    SubjectController subjectController;

    @Autowired
    IndicatorController indicatorController;
    @Autowired
    FactorController factorController;
    @Autowired
    MetricController metricController;

    @Autowired
    ApplicationContext applicationContext;

    private Strategy strategy;

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    public void evaluateMetrics() {
        Date today = new Date(System.currentTimeMillis() - 86400000L);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(today);

        Iterable<Evaluation> currentEvaluations = evaluationRepository.findByDate(date);
        List<Evaluation> evaluationList = new ArrayList<>();
        currentEvaluations.forEach(evaluationList::add);
        if (evaluationList.size() > 0) evaluationRepository.deleteAll(currentEvaluations);

        List<InternalMetric> internalMetrics = internalMetricController.getAll();
        List<Team> teams = teamController.getStoredTeams();
        List<Subject> subjects = subjectController.getAll();

        for (InternalMetric im : internalMetrics) {
            HashMap<Subject, Double> subjectHashMap = new HashMap<>();
            for (Subject s : subjects) subjectHashMap.put(s, 0.0);
            double globalValue = 0.0;
            setStrategy(im);
            for (Team t : teams) {
                if (im.getTeams() == null || im.getTeams().contains(t.getId())) {
                    double value = strategy.evaluate(t);
                    globalValue += value;
                    Subject s = t.getSubject();
                    double valueSubject = subjectHashMap.get(s);
                    subjectHashMap.put(s, valueSubject + value);
                    TeamEvaluation teamEvaluation = new TeamEvaluation(date, im, t.getId(), value);
                    teamEvaluationRepository.save(teamEvaluation);
                }
            }
            for (Subject s : subjects) {
                List<Team> teamList = s.getTeams();
                boolean containsTeam = false;
                if (im.getTeams() != null && teamList != null) {
                    for (Team t : teamList) {
                        if (im.getTeams().contains(t.getId())) {
                            containsTeam = true;
                            break;
                        }
                    }
                }
                if (im.getTeams() == null || containsTeam) {
                    double value = strategy.evaluate(s);
                    if (value == -1.0) value = subjectHashMap.get(s);
                    SubjectEvaluation subjectEvaluation = new SubjectEvaluation(date, im, s.getAcronym(), value);
                    subjectEvaluationRepository.save(subjectEvaluation);
                }
            }
            double value = strategy.evaluate();
            if (value != -1) globalValue = value;
            Evaluation evaluation = new Evaluation(date, im, globalValue);
            evaluationRepository.save(evaluation);
        }
    }

    public List<EvaluationDTO> getCurrentEvaluations() {
        Evaluation latestEvaluation = evaluationRepository.findFirstByOrderByDateDesc();
        if (latestEvaluation != null) {
            String latestDate = latestEvaluation.getDate();
            List<Evaluation> unfilteredEvaluations = evaluationRepository.findAll();
            return filterEvaluations(unfilteredEvaluations, latestDate);
        }
        return new ArrayList<>();
    }

    public List<EvaluationDTO> getHistoricalEvaluations(String dateBefore, String dateAfter) {
        List<Evaluation> unfilteredEvaluations = evaluationRepository.findByDateBetween(dateBefore, dateAfter);
        if (!unfilteredEvaluations.isEmpty())
            return filterHistoricalEvaluations(unfilteredEvaluations);
        return new ArrayList<>();
    }

    public EvaluationDTO getHistoricalEvaluationsByParam(String dateBefore, String dateAfter, String metric, String param) {
        List<Evaluation> unfilteredEvaluations = evaluationRepository.
            findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParam
            (dateBefore, dateAfter, metric, param);
        if (!unfilteredEvaluations.isEmpty())
            return filterHistoricalEvaluationsByParam(unfilteredEvaluations);
        return null;
    }

    public List<EvaluationDTO> filterEvaluations(List<Evaluation> unfilteredEvaluations, String latestDate) {
        List<EvaluationDTO> result = new ArrayList<>();
        for (Evaluation e : unfilteredEvaluations) {
            if (!e.getInternalMetric().isGroupable()) {
                if (Objects.equals(e.getDate(), latestDate))
                    result.add(new EvaluationDTO(e));
            }
            else {
                boolean found = false;
                for (int i = 0; i < result.size() && !found; ++i) {
                    if (Objects.equals(result.get(i).getInternalMetric().getController(), e.getInternalMetric().getController())) {
                        Double oldValue = result.get(i).getEntities().get(getEntityName(e.getInternalMetric().getParam()));
                        if (oldValue == null) oldValue = 0.0;
                        double newValue = oldValue + e.getValue();
                        Map<String,Double> oldEntities = result.get(i).getEntities();
                        oldEntities.put(getEntityName(e.getInternalMetric().getParam()), newValue);
                        result.get(i).setEntities(oldEntities);
                        found = true;
                    }
                }
                if (!found) {
                    Map<String,Double> entities = new HashMap<>();
                    entities.put(getEntityName(e.getInternalMetric().getParam()), e.getValue());
                    EvaluationDTO eDTO = new EvaluationDTO(e);
                    eDTO.setValue(0.0);
                    eDTO.setEntities(entities);
                    result.add(eDTO);
                }
            }
        }
        return result;
    }

    public List<EvaluationDTO> filterHistoricalEvaluations(List<Evaluation> unfilteredEvaluations) {
        List<EvaluationDTO> result = new ArrayList<>();
        for (Evaluation e : unfilteredEvaluations) {
            boolean found = false;
            if (!e.getInternalMetric().isGroupable()) {
                for (int i = 0; i < result.size() && !found; ++i) {
                    if (Objects.equals(result.get(i).getInternalMetric().getId(), e.getInternalMetric().getId())) {
                        Map<String, Double> oldEntities = result.get(i).getEntities();
                        oldEntities.put(e.getDate(), e.getValue());
                        result.get(i).setEntities(oldEntities);
                        found = true;
                    }
                }
                if (!found) {
                    Map<String,Double> entities = new HashMap<>();
                    entities.put(e.getDate(), e.getValue());
                    EvaluationDTO eDTO = new EvaluationDTO(e);
                    eDTO.setValue(0.0);
                    eDTO.setEntities(entities);
                    result.add(eDTO);
                }
            }
            else {
                for (int i = 0; i < result.size() && !found; ++i) {
                    if (Objects.equals(result.get(i).getInternalMetric().getController(), e.getInternalMetric().getController())) {
                        Double oldValue = result.get(i).getEntities().get(getEntityName(e.getInternalMetric().getParam()));
                        if (oldValue == null) oldValue = 0.0;
                        double newValue = oldValue + e.getValue();
                        Map<String,Double> oldEntities = result.get(i).getEntities();
                        oldEntities.put(getEntityName(e.getInternalMetric().getParam()), newValue);
                        result.get(i).setEntities(oldEntities);
                        found = true;
                    }
                }
                if (!found) {
                    Map<String,Double> entities = new HashMap<>();
                    entities.put(getEntityName(e.getInternalMetric().getParam()), e.getValue());
                    EvaluationDTO eDTO = new EvaluationDTO(e);
                    eDTO.setValue(0.0);
                    eDTO.setEntities(entities);
                    result.add(eDTO);
                }
            }
        }
        return result;
    }

    public EvaluationDTO filterHistoricalEvaluationsByParam(List<Evaluation> unfilteredEvaluations) {
        EvaluationDTO result = null;
        for (Evaluation e : unfilteredEvaluations) {
            if (result == null) {
                Map<String,Double> entities = new HashMap<>();
                entities.put(e.getDate(), e.getValue());
                EvaluationDTO eDTO = new EvaluationDTO(e);
                eDTO.setValue(0.0);
                eDTO.setEntities(entities);
                result = eDTO;
            }
            else {
                Map<String, Double> oldEntities = result.getEntities();
                oldEntities.put(e.getDate(), e.getValue());
                result.setEntities(oldEntities);
            }
        }
        return result;
    }

    public String getEntityName(String paramName) {
        Indicator i = indicatorController.getIndicator(paramName);
        if (i != null) return i.getName();
        Factor f = factorController.getFactor(paramName);
        if (f != null) return f.getName();
        Metric m = metricController.getMetric(paramName);
        if (m != null) return m.getName();
        return paramName;
    }

    private void setStrategy(InternalMetric im) {
        Object[] constructorArgs = createConstructorArgs(im);
        try {
            String packageName = getClass().getPackage().getName();
            String imName = getControllerName(im);
            String className = packageName + ".internalMetrics."
                + StringUtils.capitalize(imName) + "Controller";
            Class<?> clazz = Class.forName(className);
            if (clazz.isAnnotationPresent(Controller.class)) {
                String beanName = StringUtils.uncapitalize(clazz.getSimpleName());
                this.strategy = (Strategy) applicationContext.getBean(beanName);
                if (constructorArgs.length != 0)
                    this.strategy.setParams((String) constructorArgs[0]);
            }
            else throw new IllegalArgumentException("Class is not a @Controller");
        } catch (ClassNotFoundException e) {
            if (!Objects.equals(e.getMessage(),
                "com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics.TestInternalMetricController"))
                logger.error(e.getMessage());
        }
    }

    private Object[] createConstructorArgs(InternalMetric im) {
        String paramName = im.getParam();
        if (paramName == null) return new Object[]{};
        else return new Object[]{paramName};
    }

    private String getControllerName(InternalMetric im) {
        String controllerName = im.getController();
        if (controllerName == null) return im.getId();
        else return controllerName;
    }

    private Class<?>[] getConstructorParameterTypes(Object... constructorArgs) {
        Class<?>[] parameterTypes = new Class<?>[constructorArgs.length];
        for (int i = 0; i < constructorArgs.length; i++) {
            parameterTypes[i] = constructorArgs[i].getClass();
        }
        return parameterTypes;
    }
}
