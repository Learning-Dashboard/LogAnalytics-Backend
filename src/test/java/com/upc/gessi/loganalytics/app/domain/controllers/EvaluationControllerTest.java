package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics.Strategy;
import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.EvaluationRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectEvaluationRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamEvaluationRepository;
import com.upc.gessi.loganalytics.app.rest.DTOs.EvaluationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class EvaluationControllerTest {

    @Mock
    EvaluationRepository evaluationRepository;
    @Mock
    InternalMetricController internalMetricController;
    @Mock
    TeamController teamController;
    @Mock
    SubjectController subjectController;
    @Mock
    TeamEvaluationRepository teamEvaluationRepository;
    @Mock
    SubjectEvaluationRepository subjectEvaluationRepository;
    @Mock
    Strategy strategy;
    @Mock
    IndicatorController indicatorController;
    @Mock
    FactorController factorController;
    @Mock
    MetricController metricController;

    @InjectMocks
    EvaluationController evaluationController;

    @Test
    void evaluateMetrics() {
        InternalMetric internalMetric = new InternalMetric("testInternalMetric", "Test internal metric");
        List<InternalMetric> internalMetrics = List.of(internalMetric);
        Mockito.when(internalMetricController.getAll()).thenReturn(internalMetrics);

        Subject subj1 = new Subject("PES");
        Subject subj2 = new Subject("ASW");
        Team team1 = new Team("pes11a", "sem", subj1);
        Team team2 = new Team("pes11b", "sem", subj1);
        Team team3 = new Team("asw11a", "sem", subj2);
        Team team4 = new Team("asw11b", "sem", subj2);
        List<Team> teams = List.of(team1, team2, team3, team4);
        Mockito.when(teamController.getStoredTeams()).thenReturn(teams);
        List<Subject> subjects = List.of(subj1, subj2);
        Mockito.when(subjectController.getAll()).thenReturn(subjects);

        Mockito.when(strategy.evaluate(Mockito.any(Team.class))).thenReturn(1.0);
        Mockito.when(strategy.evaluate(Mockito.any(Subject.class))).thenReturn(-1.0);
        Mockito.when(strategy.evaluate()).thenReturn(-1.0);
        List<Evaluation> currentEvaluations = List.of(new Evaluation());
        Mockito.when(evaluationRepository.findByDate(Mockito.anyString())).thenReturn(currentEvaluations);
        evaluationController.evaluateMetrics();

        ArgumentCaptor<TeamEvaluation> teamEvaluationCaptor = ArgumentCaptor.forClass(TeamEvaluation.class);
        ArgumentCaptor<SubjectEvaluation> subjectEvaluationCaptor = ArgumentCaptor.forClass(SubjectEvaluation.class);
        ArgumentCaptor<Evaluation> evaluationCaptor = ArgumentCaptor.forClass(Evaluation.class);

        Mockito.verify(evaluationRepository, Mockito.times(1)).deleteAll(currentEvaluations);
        Mockito.verify(teamEvaluationRepository, Mockito.times(4)).save(teamEvaluationCaptor.capture());
        Mockito.verify(subjectEvaluationRepository, Mockito.times(2)).save(subjectEvaluationCaptor.capture());
        Mockito.verify(evaluationRepository, Mockito.times(1)).save(evaluationCaptor.capture());

        TeamEvaluation savedTeamEvaluation = teamEvaluationCaptor.getValue();
        assertEquals(1.0, savedTeamEvaluation.getValue());

        SubjectEvaluation savedSubjectEvaluation = subjectEvaluationCaptor.getValue();
        assertEquals(2.0, savedSubjectEvaluation.getValue());

        Evaluation savedEvaluation = evaluationCaptor.getValue();
        assertEquals(4.0, savedEvaluation.getValue());
    }

    @Test
    void getCurrentEvaluations() {
        InternalMetric im = new InternalMetric("im", "im");
        Evaluation e = new Evaluation("2023-05-12", im, 5.0);
        Mockito.when(evaluationRepository.findFirstByOrderByDateDesc()).thenReturn(e);
        Mockito.when(evaluationRepository.findAll()).thenReturn(List.of(e));

        evaluationController.getCurrentEvaluations();
        Mockito.verify(evaluationRepository, Mockito.times(1)).findFirstByOrderByDateDesc();
        Mockito.verify(evaluationRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getHistoricalEvaluations() {
        InternalMetric im = new InternalMetric("im", "im");
        Evaluation e = new Evaluation("2023-05-12", im, 5.0);
        Mockito.when(evaluationRepository.findByDateBetween("2023-05-10", "2023-05-15")).thenReturn(List.of(e));

        evaluationController.getHistoricalEvaluations("2023-05-10", "2023-05-15");
        Mockito.verify(evaluationRepository, Mockito.times(1)).findByDateBetween("2023-05-10", "2023-05-15");
    }

    @Test
    void getHistoricalEvaluationsByParam() {
        InternalMetric im = new InternalMetric("im", "im");
        Evaluation e = new Evaluation("2023-05-12", im, 5.0);
        Mockito.when(evaluationRepository.findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParam
            ("2023-05-10", "2023-05-15", "name", "param")).thenReturn(List.of(e));

        evaluationController.getHistoricalEvaluationsByParam
            ("2023-05-10", "2023-05-15", "name", "param");
        Mockito.verify(evaluationRepository, Mockito.times(1))
            .findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParam
            ("2023-05-10", "2023-05-15", "name", "param");
    }

    @Test
    void filterEvaluations() {
        InternalMetric noG = new InternalMetric("im", "im", null, null, null, false);
        Evaluation noG1 = new Evaluation("2023-05-11", noG, 10.0);
        Evaluation noG2 = new Evaluation("2023-05-10", noG, 6.0);
        EvaluationDTO eDTOnoG = new EvaluationDTO(noG1);

        InternalMetric G1 = new InternalMetric("im1", "im1", "p1", "c1", "c1", true);
        InternalMetric G2 = new InternalMetric("im2", "im2", "p2", "c1", "c1", true);
        Evaluation eG1 = new Evaluation("2023-05-11", G1, 10.0);
        Evaluation eG2 = new Evaluation("2023-05-11", G2, 5.0);
        Evaluation eG3 = new Evaluation("2023-05-10", G2, 20.0);

        EvaluationDTO eDTOG = new EvaluationDTO(eG1);
        eDTOG.setValue(0.0);
        Map<String, Double> entities = new HashMap<>();
        entities.put("p1", 10.0);
        entities.put("p2", 25.0);
        eDTOG.setEntities(entities);

        List<Evaluation> inputList = new ArrayList<>();
        inputList.add(noG1); inputList.add(noG2);
        inputList.add(eG1); inputList.add(eG2); inputList.add(eG3);
        List<EvaluationDTO> outputList = new ArrayList<>();
        outputList.add(eDTOnoG); outputList.add(eDTOG);

        List<EvaluationDTO> output = evaluationController.filterEvaluations(inputList, "2023-05-11");
        assertEquals(outputList, output);
    }

    @Test
    void filterHistoricalEvaluations() {
        InternalMetric noG = new InternalMetric("im", "im", null, null, null, false);
        Evaluation noG1 = new Evaluation("2023-05-11", noG, 10.0);
        Evaluation noG2 = new Evaluation("2023-05-10", noG, 6.0);
        EvaluationDTO eDTOnoG = new EvaluationDTO(noG1);
        eDTOnoG.setValue(0.0);
        Map<String, Double> entities = new HashMap<>();
        entities.put("2023-05-11", 10.0);
        entities.put("2023-05-10", 6.0);
        eDTOnoG.setEntities(entities);

        InternalMetric G1 = new InternalMetric("im1", "im1", "p1", "c1", "c1", true);
        InternalMetric G2 = new InternalMetric("im2", "im2", "p2", "c1", "c1", true);
        Evaluation eG1 = new Evaluation("2023-05-11", G1, 10.0);
        Evaluation eG2 = new Evaluation("2023-05-11", G2, 5.0);
        Evaluation eG3 = new Evaluation("2023-05-10", G2, 20.0);

        EvaluationDTO eDTOG = new EvaluationDTO(eG1);
        eDTOG.setValue(0.0);
        entities = new HashMap<>();
        entities.put("p1", 10.0);
        entities.put("p2", 25.0);
        eDTOG.setEntities(entities);

        List<Evaluation> inputList = new ArrayList<>();
        inputList.add(noG1); inputList.add(noG2);
        inputList.add(eG1); inputList.add(eG2); inputList.add(eG3);
        List<EvaluationDTO> outputList = new ArrayList<>();
        outputList.add(eDTOnoG); outputList.add(eDTOG);
        List<EvaluationDTO> output = evaluationController.filterHistoricalEvaluations(inputList);
        assertEquals(outputList, output);
    }

    @Test
    void filterHistoricalEvaluationsByParam() {
        InternalMetric G1 = new InternalMetric("im1", "im1", "p1", "c1", "c1", true);
        Evaluation eG1 = new Evaluation("2023-05-12", G1, 10.0);
        Evaluation eG2 = new Evaluation("2023-05-11", G1, 15.0);
        Evaluation eG3 = new Evaluation("2023-05-10", G1, 20.0);

        EvaluationDTO eDTOG = new EvaluationDTO(eG1);
        eDTOG.setValue(0.0);
        Map<String,Double> entities = new HashMap<>();
        entities.put("2023-05-12", 10.0);
        entities.put("2023-05-11", 15.0);
        entities.put("2023-05-10", 20.0);
        eDTOG.setEntities(entities);

        List<Evaluation> inputList = new ArrayList<>();
        inputList.add(eG1); inputList.add(eG2); inputList.add(eG3);
        EvaluationDTO output = evaluationController.filterHistoricalEvaluationsByParam(inputList);
        assertEquals(eDTOG, output);
    }
}