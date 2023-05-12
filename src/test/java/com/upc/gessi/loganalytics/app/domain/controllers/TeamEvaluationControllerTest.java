package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.SubjectEvaluation;
import com.upc.gessi.loganalytics.app.domain.models.TeamEvaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamEvaluationRepository;
import com.upc.gessi.loganalytics.app.rest.DTOs.EvaluationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TeamEvaluationControllerTest {

    @Mock
    TeamEvaluationRepository teamEvaluationRepository;

    @InjectMocks
    TeamEvaluationController teamEvaluationController;

    @Test
    void getCurrentEvaluations() {
        InternalMetric im = new InternalMetric("im", "im");
        TeamEvaluation e = new TeamEvaluation("2023-05-12", im, "test", 5.0);
        Mockito.when(teamEvaluationRepository.findFirstByTeamOrderByDateDesc("test")).thenReturn(e);
        Mockito.when(teamEvaluationRepository.findByTeam("test")).thenReturn(List.of(e));

        teamEvaluationController.getCurrentEvaluations("test");
        Mockito.verify(teamEvaluationRepository, Mockito.times(1)).findFirstByTeamOrderByDateDesc("test");
        Mockito.verify(teamEvaluationRepository, Mockito.times(1)).findByTeam("test");
    }

    @Test
    void getHistoricalEvaluations() {
        InternalMetric im = new InternalMetric("im", "im");
        TeamEvaluation e = new TeamEvaluation("2023-05-12", im, "test", 5.0);
        Mockito.when(teamEvaluationRepository.findByTeamAndDateBetween("test", "2023-05-10", "2023-05-15")).thenReturn(List.of(e));

        teamEvaluationController.getHistoricalEvaluations("test", "2023-05-10", "2023-05-15");
        Mockito.verify(teamEvaluationRepository, Mockito.times(1))
            .findByTeamAndDateBetween("test", "2023-05-10", "2023-05-15");
    }

    @Test
    void getHistoricalEvaluationsByParam() {
        InternalMetric im = new InternalMetric("im", "im");
        TeamEvaluation e = new TeamEvaluation("2023-05-12", im, "test", 5.0);
        Mockito.when(teamEvaluationRepository.findByTeamAndDateBetweenAndInternalMetricControllerNameAndInternalMetricParam(
            "test", "2023-05-10", "2023-05-15", "name", "param")).thenReturn(List.of(e));

        teamEvaluationController.getHistoricalEvaluationsByParam
            ("test", "2023-05-10", "2023-05-15", "name", "param");
        Mockito.verify(teamEvaluationRepository, Mockito.times(1))
            .findByTeamAndDateBetweenAndInternalMetricControllerNameAndInternalMetricParam(
            "test", "2023-05-10", "2023-05-15", "name", "param");
    }

    @Test
    void filterEvaluations() {
        InternalMetric noG = new InternalMetric("im", "im", null, null, null, false);
        TeamEvaluation noG1 = new TeamEvaluation("2023-05-11", noG, "test", 10.0);
        TeamEvaluation noG2 = new TeamEvaluation("2023-05-10", noG, "test", 6.0);
        EvaluationDTO eDTOnoG = new EvaluationDTO(noG1);

        InternalMetric G1 = new InternalMetric("im1", "im1", "p1", "c1", "c1", true);
        InternalMetric G2 = new InternalMetric("im2", "im2", "p2", "c1", "c1", true);
        TeamEvaluation eG1 = new TeamEvaluation("2023-05-11", G1, "test", 10.0);
        TeamEvaluation eG2 = new TeamEvaluation("2023-05-11", G2, "test", 5.0);
        TeamEvaluation eG3 = new TeamEvaluation("2023-05-10", G2, "test", 20.0);

        EvaluationDTO eDTOG = new EvaluationDTO(eG1);
        eDTOG.setValue(0.0);
        Map<String, Double> entities = new HashMap<>();
        entities.put("p1", 10.0);
        entities.put("p2", 25.0);
        eDTOG.setEntities(entities);

        List<TeamEvaluation> inputList = new ArrayList<>();
        inputList.add(noG1); inputList.add(noG2);
        inputList.add(eG1); inputList.add(eG2); inputList.add(eG3);
        List<EvaluationDTO> outputList = new ArrayList<>();
        outputList.add(eDTOnoG); outputList.add(eDTOG);
        List<EvaluationDTO> output = teamEvaluationController.filterEvaluations(inputList, "2023-05-11");
        assertEquals(outputList, output);
    }

    @Test
    void filterHistoricalEvaluations() {
        InternalMetric noG = new InternalMetric("im", "im", null, null, null, false);
        TeamEvaluation noG1 = new TeamEvaluation("2023-05-11", noG, "test", 10.0);
        TeamEvaluation noG2 = new TeamEvaluation("2023-05-10", noG, "test", 6.0);
        EvaluationDTO eDTOnoG = new EvaluationDTO(noG1);
        eDTOnoG.setValue(0.0);
        Map<String, Double> entities = new HashMap<>();
        entities.put("2023-05-11", 10.0);
        entities.put("2023-05-10", 6.0);
        eDTOnoG.setEntities(entities);

        InternalMetric G1 = new InternalMetric("im1", "im1", "p1", "c1", "c1", true);
        InternalMetric G2 = new InternalMetric("im2", "im2", "p2", "c1", "c1", true);
        TeamEvaluation eG1 = new TeamEvaluation("2023-05-11", G1, "test", 10.0);
        TeamEvaluation eG2 = new TeamEvaluation("2023-05-11", G2, "test", 5.0);
        TeamEvaluation eG3 = new TeamEvaluation("2023-05-10", G2, "test", 20.0);

        EvaluationDTO eDTOG = new EvaluationDTO(eG1);
        eDTOG.setValue(0.0);
        entities = new HashMap<>();
        entities.put("p1", 10.0);
        entities.put("p2", 25.0);
        eDTOG.setEntities(entities);

        List<TeamEvaluation> inputList = new ArrayList<>();
        inputList.add(noG1); inputList.add(noG2);
        inputList.add(eG1); inputList.add(eG2); inputList.add(eG3);
        List<EvaluationDTO> outputList = new ArrayList<>();
        outputList.add(eDTOnoG); outputList.add(eDTOG);
        List<EvaluationDTO> output = teamEvaluationController.filterHistoricalEvaluations(inputList);
        assertEquals(outputList, output);
    }

    @Test
    void filterHistoricalEvaluationsByParam() {
        InternalMetric G1 = new InternalMetric("im1", "im1", "p1", "c1", "c1", true);
        TeamEvaluation eG1 = new TeamEvaluation("2023-05-12", G1, "test", 10.0);
        TeamEvaluation eG2 = new TeamEvaluation("2023-05-11", G1, "test", 15.0);
        TeamEvaluation eG3 = new TeamEvaluation("2023-05-10", G1, "test", 20.0);

        EvaluationDTO eDTOG = new EvaluationDTO(eG1);
        eDTOG.setValue(0.0);
        Map<String,Double> entities = new HashMap<>();
        entities.put("2023-05-12", 10.0);
        entities.put("2023-05-11", 15.0);
        entities.put("2023-05-10", 20.0);
        eDTOG.setEntities(entities);

        List<TeamEvaluation> inputList = new ArrayList<>();
        inputList.add(eG1); inputList.add(eG2); inputList.add(eG3);
        EvaluationDTO output = teamEvaluationController.filterHistoricalEvaluationsByParam(inputList);
        assertEquals(eDTOG, output);
    }
}