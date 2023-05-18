package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.TeamEvaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamEvaluationRepository;
import com.upc.gessi.loganalytics.app.rest.DTOs.EvaluationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class TeamEvaluationController {

    @Autowired
    TeamEvaluationRepository teamEvaluationRepository;

    @Autowired
    EvaluationController evaluationController;

    public List<EvaluationDTO> getCurrentEvaluations(String team) {
        TeamEvaluation latestEvaluation = teamEvaluationRepository.findFirstByTeamOrderByDateDesc(team);
        if (latestEvaluation != null) {
            String latestDate = latestEvaluation.getDate();
            List<TeamEvaluation> unfilteredEvaluations = teamEvaluationRepository.findByTeam(team);
            return filterEvaluations(unfilteredEvaluations, latestDate);
        }
        return new ArrayList<>();
    }

    public List<EvaluationDTO> getHistoricalEvaluations(String team, String dateBefore, String dateAfter) {
        List<TeamEvaluation> unfilteredEvaluations = teamEvaluationRepository.
                findByTeamAndDateBetween(team, dateBefore, dateAfter);
        if (!unfilteredEvaluations.isEmpty())
            return filterHistoricalEvaluations(unfilteredEvaluations);
        return new ArrayList<>();
    }

    public EvaluationDTO getHistoricalEvaluationsByParam(String team, String dateBefore, String dateAfter, String metric, String param) {
        List<TeamEvaluation> unfilteredEvaluations = teamEvaluationRepository.
                findByTeamAndDateBetweenAndInternalMetricControllerNameAndInternalMetricParam
                        (team, dateBefore, dateAfter, metric, param);
        if (!unfilteredEvaluations.isEmpty())
            return filterHistoricalEvaluationsByParam(unfilteredEvaluations);
        return null;
    }

    public List<EvaluationDTO> filterEvaluations(List<TeamEvaluation> unfilteredEvaluations, String latestDate) {
        List<EvaluationDTO> result = new ArrayList<>();
        for (TeamEvaluation e : unfilteredEvaluations) {
            if (!e.getInternalMetric().isGroupable()) {
                if (Objects.equals(e.getDate(), latestDate))
                    result.add(new EvaluationDTO(e));
            }
            else {
                boolean found = false;
                for (int i = 0; i < result.size() && !found; ++i) {
                    if (Objects.equals(result.get(i).getInternalMetric().getController(), e.getInternalMetric().getController())) {
                        Double oldValue = result.get(i).getEntities().get(e.getInternalMetric().getParam());
                        if (oldValue == null) oldValue = 0.0;
                        double newValue = oldValue + e.getValue();
                        Map<String,Double> oldEntities = result.get(i).getEntities();
                        oldEntities.put(e.getInternalMetric().getParam(), newValue);
                        result.get(i).setEntities(oldEntities);
                        found = true;
                    }
                }
                if (!found) {
                    Map<String,Double> entities = new HashMap<>();
                    entities.put(e.getInternalMetric().getParam(), e.getValue());
                    EvaluationDTO eDTO = new EvaluationDTO(e);
                    eDTO.setValue(0.0);
                    eDTO.setEntities(entities);
                    result.add(eDTO);
                }
            }
        }
        return result;
    }

    public List<EvaluationDTO> filterHistoricalEvaluations(List<TeamEvaluation> unfilteredEvaluations) {
        List<EvaluationDTO> result = new ArrayList<>();
        for (TeamEvaluation e : unfilteredEvaluations) {
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
                        Double oldValue = result.get(i).getEntities().get(e.getInternalMetric().getParam());
                        if (oldValue == null) oldValue = 0.0;
                        double newValue = oldValue + e.getValue();
                        Map<String,Double> oldEntities = result.get(i).getEntities();
                        oldEntities.put(e.getInternalMetric().getParam(), newValue);
                        result.get(i).setEntities(oldEntities);
                        found = true;
                    }
                }
                if (!found) {
                    Map<String,Double> entities = new HashMap<>();
                    entities.put(e.getInternalMetric().getParam(), e.getValue());
                    EvaluationDTO eDTO = new EvaluationDTO(e);
                    eDTO.setValue(0.0);
                    eDTO.setEntities(entities);
                    result.add(eDTO);
                }
            }
        }
        return result;
    }

    public EvaluationDTO filterHistoricalEvaluationsByParam(List<TeamEvaluation> unfilteredEvaluations) {
        EvaluationDTO result = null;
        for (TeamEvaluation e : unfilteredEvaluations) {
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
