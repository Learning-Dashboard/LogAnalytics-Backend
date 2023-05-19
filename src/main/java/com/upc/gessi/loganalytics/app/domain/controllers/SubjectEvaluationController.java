package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.SubjectEvaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectEvaluationRepository;
import com.upc.gessi.loganalytics.app.rest.DTOs.EvaluationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.*;

@Controller
public class SubjectEvaluationController {

    @Autowired
    SubjectEvaluationRepository subjectEvaluationRepository;

    @Autowired
    EvaluationController evaluationController;
    @Autowired
    InternalMetricController internalMetricController;

    public List<EvaluationDTO> getCurrentEvaluations(String subject) {
        SubjectEvaluation latestEvaluation = subjectEvaluationRepository.findFirstBySubjectOrderByDateDesc(subject);
        if (latestEvaluation != null) {
            String latestDate = latestEvaluation.getDate();
            List<SubjectEvaluation> unfilteredEvaluations = subjectEvaluationRepository.findBySubject(subject);
            return filterEvaluations(unfilteredEvaluations, latestDate);
        }
        return new ArrayList<>();
    }

    public List<EvaluationDTO> getHistoricalEvaluations(String subject, String dateBefore, String dateAfter) {
        List<SubjectEvaluation> unfilteredEvaluations = subjectEvaluationRepository.
                findBySubjectAndDateBetween(subject, dateBefore, dateAfter);
        if (!unfilteredEvaluations.isEmpty())
            return filterHistoricalEvaluations(unfilteredEvaluations);
        return new ArrayList<>();
    }

    public EvaluationDTO getHistoricalEvaluationsByParam(String subject, String dateBefore, String dateAfter, String metric, String param) {
        boolean paramNamePresent = internalMetricController.checkParamNameExistence(param);
        List<SubjectEvaluation> unfilteredEvaluations;
        if (paramNamePresent) {
            unfilteredEvaluations = subjectEvaluationRepository.
                findBySubjectAndDateBetweenAndInternalMetricControllerNameAndInternalMetricParamName
                (subject, dateBefore, dateAfter, metric, param);
        }
        else {
            unfilteredEvaluations = subjectEvaluationRepository.
                findBySubjectAndDateBetweenAndInternalMetricControllerNameAndInternalMetricParam
                (subject, dateBefore, dateAfter, metric, param);
        }
        if (!unfilteredEvaluations.isEmpty())
            return filterHistoricalEvaluationsByParam(unfilteredEvaluations);
        return null;
    }

    public List<EvaluationDTO> filterEvaluations(List<SubjectEvaluation> unfilteredEvaluations, String latestDate) {
        List<EvaluationDTO> result = new ArrayList<>();
        for (SubjectEvaluation e : unfilteredEvaluations) {
            if (!e.getInternalMetric().isGroupable()) {
                if (Objects.equals(e.getDate(), latestDate))
                    result.add(new EvaluationDTO(e));
            }
            else {
                boolean found = false;
                for (int i = 0; i < result.size() && !found; ++i) {
                    if (Objects.equals(result.get(i).getInternalMetric().getController(), e.getInternalMetric().getController())) {
                        Double oldValue = result.get(i).getEntities().get(evaluationController.getEntityName(e.getInternalMetric()));
                        if (oldValue == null) oldValue = 0.0;
                        double newValue = oldValue + e.getValue();
                        Map<String,Double> oldEntities = result.get(i).getEntities();
                        oldEntities.put(evaluationController.getEntityName(e.getInternalMetric()), newValue);
                        result.get(i).setEntities(oldEntities);
                        found = true;
                    }
                }
                if (!found) {
                    Map<String,Double> entities = new HashMap<>();
                    entities.put(evaluationController.getEntityName(e.getInternalMetric()), e.getValue());
                    EvaluationDTO eDTO = new EvaluationDTO(e);
                    eDTO.setValue(0.0);
                    eDTO.setEntities(entities);
                    result.add(eDTO);
                }
            }
        }
        return result;
    }

    public List<EvaluationDTO> filterHistoricalEvaluations(List<SubjectEvaluation> unfilteredEvaluations) {
        List<EvaluationDTO> result = new ArrayList<>();
        for (SubjectEvaluation e : unfilteredEvaluations) {
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
                        Double oldValue = result.get(i).getEntities().get(evaluationController.getEntityName(e.getInternalMetric()));
                        if (oldValue == null) oldValue = 0.0;
                        double newValue = oldValue + e.getValue();
                        Map<String,Double> oldEntities = result.get(i).getEntities();
                        oldEntities.put(evaluationController.getEntityName(e.getInternalMetric()), newValue);
                        result.get(i).setEntities(oldEntities);
                        found = true;
                    }
                }
                if (!found) {
                    Map<String,Double> entities = new HashMap<>();
                    entities.put(evaluationController.getEntityName(e.getInternalMetric()), e.getValue());
                    EvaluationDTO eDTO = new EvaluationDTO(e);
                    eDTO.setValue(0.0);
                    eDTO.setEntities(entities);
                    result.add(eDTO);
                }
            }
        }
        return result;
    }

    public EvaluationDTO filterHistoricalEvaluationsByParam(List<SubjectEvaluation> unfilteredEvaluations) {
        EvaluationDTO result = null;
        for (SubjectEvaluation e : unfilteredEvaluations) {
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
}
