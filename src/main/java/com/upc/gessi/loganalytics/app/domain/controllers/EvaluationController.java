package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics.Strategy;
import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.EvaluationRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectEvaluationRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamEvaluationRepository;
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
    private ApplicationContext applicationContext;

    private Strategy strategy;

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    public void evaluateMetrics() {
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(today);

        Iterable<Evaluation> currentEvaluations = evaluationRepository.findByDate(date);
        List<Evaluation> evaluationList = new ArrayList<>();
        currentEvaluations.forEach(evaluationList::add);
        if (evaluationList.size() > 0)
            evaluationRepository.deleteAll(currentEvaluations);

        List<InternalMetric> internalMetrics = internalMetricController.getAll();
        List<Team> teams = teamController.getStoredTeams();
        List<Subject> subjects = subjectController.getAll();

        for (InternalMetric im : internalMetrics) {
            HashMap<Subject, Double> subjectHashMap = new HashMap<>();
            for (Subject s : subjects) subjectHashMap.put(s, 0.0);
            double globalValue = 0.0;
            for (Team t : teams) {
                setStrategy(im);
                double value = strategy.evaluate(t);
                globalValue += value;
                Subject s = t.getSubject();
                double valueSubject = subjectHashMap.get(s);
                subjectHashMap.put(s, valueSubject + value);
                TeamEvaluation teamEvaluation = new TeamEvaluation(date, im, t.getId(), value);
                teamEvaluationRepository.save(teamEvaluation);
            }
            for (Subject s : subjects) {
                double value = strategy.evaluate(s);
                if (value == -1.0) value = subjectHashMap.get(s);
                SubjectEvaluation subjectEvaluation = new SubjectEvaluation(date, im, s.getAcronym(), value);
                subjectEvaluationRepository.save(subjectEvaluation);
            }
            double value = strategy.evaluate();
            if (value != -1) globalValue = value;
            Evaluation evaluation = new Evaluation(date, im, globalValue);
            evaluationRepository.save(evaluation);
        }
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
                if (constructorArgs.length != 0) {
                    if (constructorArgs[0] instanceof Integer)
                        this.strategy.setParams((Integer) constructorArgs[0]);
                    else this.strategy.setParams((String) constructorArgs[0]);
                }
            } else {
                throw new IllegalArgumentException("Class is not a @Controller");
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    private Object[] createConstructorArgs(InternalMetric im) {
        String name = im.getName();
        if (name.contains("DaysLogins")) {
            String daysString = name.replaceAll("DaysLogins", "");
            int days = Integer.parseInt(daysString);
            return new Object[]{days};
        }
        else if (name.contains("FactorAccesses")) {
            String factor = name.replaceAll("FactorAccesses", "");
            return new Object[]{factor};
        }
        else if (name.contains("IndicatorAccesses")) {
            String indicator = name.replaceAll("IndicatorAccesses", "");
            return new Object[]{indicator};
        }
        else if (name.contains("MetricAccesses")) {
            String metric = name.replaceAll("MetricAccesses", "");
            return new Object[]{metric};
        }
        else if (name.contains("PageAccesses")) {
            String metric = name.replaceAll("PageAccesses", "");
            return new Object[]{metric};
        }
        else if (name.contains("HistoricAccesses")) {
            String historic = "true";
            return new Object[]{historic};
        }
        else if (name.contains("CurrentAccesses")) {
            String historic = "false";
            return new Object[]{historic};
        }
        else if (name.contains("IViewAccesses")) {
            String view = name.replaceAll("IViewAccesses", "");
            return new Object[]{view};
        }
        else if (name.contains("FViewAccesses")) {
            String view = name.replaceAll("FViewAccesses", "");
            return new Object[]{view};
        }
        else if (name.contains("MViewAccesses")) {
            String view = name.replaceAll("MViewAccesses", "");
            return new Object[]{view};
        }
        else if (name.contains("QModViewAccesses")) {
            String view = name.replaceAll("QModViewAccesses", "");
            return new Object[]{view};
        }
        return new Object[]{};
    }

    private String getControllerName(InternalMetric im) {
        String name = im.getName();
        if (name.contains("DaysLogins"))
            return "DaysLogins";
        else if (name.contains("FactorAccesses"))
            return "FactorAccesses";
        else if (name.contains("IndicatorAccesses"))
            return "IndicatorAccesses";
        else if (name.contains("MetricAccesses"))
            return "MetricAccesses";
        else if (name.contains("PageAccesses"))
            return "PageAccesses";
        else if (name.contains("HistoricAccesses"))
            return "HistoricAccesses";
        else if (name.contains("CurrentAccesses"))
            return "HistoricAccesses";
        else if (name.contains("IViewAccesses"))
            return "IViewAccesses";
        else if (name.contains("FViewAccesses"))
            return "FViewAccesses";
        else if (name.contains("MViewAccesses"))
            return "MViewAccesses";
        else if (name.contains("QModViewAccesses"))
            return "QModViewAccesses";
        return name;
    }

    private Class<?>[] getConstructorParameterTypes(Object... constructorArgs) {
        Class<?>[] parameterTypes = new Class<?>[constructorArgs.length];
        for (int i = 0; i < constructorArgs.length; i++) {
            parameterTypes[i] = constructorArgs[i].getClass();
        }
        return parameterTypes;
    }
}
