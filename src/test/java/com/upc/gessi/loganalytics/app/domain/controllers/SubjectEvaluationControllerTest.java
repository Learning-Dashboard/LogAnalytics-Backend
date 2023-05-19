package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.SubjectEvaluation;
import com.upc.gessi.loganalytics.app.domain.models.UserlessInternalMetric;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectEvaluationRepository;
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
class SubjectEvaluationControllerTest {

    @Mock
    SubjectEvaluationRepository subjectEvaluationRepository;
    @Mock
    EvaluationController evaluationController;
    @Mock
    InternalMetricController internalMetricController;

    @InjectMocks
    SubjectEvaluationController subjectEvaluationController;

    @Test
    void getCurrentEvaluations() {
        InternalMetric im = new InternalMetric("im", "im");
        SubjectEvaluation e = new SubjectEvaluation("2023-05-12", im, "test", 5.0);
        Mockito.when(subjectEvaluationRepository.findFirstBySubjectOrderByDateDesc("test")).thenReturn(e);
        Mockito.when(subjectEvaluationRepository.findBySubject("test")).thenReturn(List.of(e));

        subjectEvaluationController.getCurrentEvaluations("test");
        Mockito.verify(subjectEvaluationRepository, Mockito.times(1)).findFirstBySubjectOrderByDateDesc("test");
        Mockito.verify(subjectEvaluationRepository, Mockito.times(1)).findBySubject("test");
    }

    @Test
    void getHistoricalEvaluations() {
        InternalMetric im = new InternalMetric("im", "im");
        SubjectEvaluation e = new SubjectEvaluation("2023-05-12", im, "test", 5.0);
        Mockito.when(subjectEvaluationRepository.findBySubjectAndDateBetween("test", "2023-05-10", "2023-05-15")).thenReturn(List.of(e));

        subjectEvaluationController.getHistoricalEvaluations("test", "2023-05-10", "2023-05-15");
        Mockito.verify(subjectEvaluationRepository, Mockito.times(1))
            .findBySubjectAndDateBetween("test", "2023-05-10", "2023-05-15");
    }

    @Test
    void getHistoricalEvaluationsByParam() {
        InternalMetric im = new InternalMetric("im", "im");
        SubjectEvaluation e = new SubjectEvaluation("2023-05-12", im, "test", 5.0);
        Mockito.when(subjectEvaluationRepository.findBySubjectAndDateBetweenAndInternalMetricControllerNameAndInternalMetricParam(
                "test", "2023-05-10", "2023-05-15", "name", "param")).thenReturn(List.of(e));

        subjectEvaluationController.getHistoricalEvaluationsByParam
                ("test", "2023-05-10", "2023-05-15", "name", "param");
        Mockito.verify(subjectEvaluationRepository, Mockito.times(1))
            .findBySubjectAndDateBetweenAndInternalMetricControllerNameAndInternalMetricParam(
            "test", "2023-05-10", "2023-05-15", "name", "param");
    }

    @Test
    void filterEvaluations() {
        InternalMetric noG = new InternalMetric("im", "im", null, null, null, null, false);
        SubjectEvaluation noG1 = new SubjectEvaluation("2023-05-11", noG, "test", 10.0);
        SubjectEvaluation noG2 = new SubjectEvaluation("2023-05-10", noG, "test", 6.0);
        EvaluationDTO eDTOnoG = new EvaluationDTO(noG1);

        InternalMetric G1 = new InternalMetric("im1", "im1", "p1", "p1", "c1", "c1", true);
        InternalMetric G2 = new InternalMetric("im2", "im2", "p2", "p2", "c1", "c1", true);
        SubjectEvaluation eG1 = new SubjectEvaluation("2023-05-11", G1, "test", 10.0);
        SubjectEvaluation eG2 = new SubjectEvaluation("2023-05-11", G2, "test", 5.0);
        SubjectEvaluation eG3 = new SubjectEvaluation("2023-05-10", G2, "test", 20.0);

        EvaluationDTO eDTOG = new EvaluationDTO(eG1);
        eDTOG.setValue(0.0);
        Map<String, Double> entities = new HashMap<>();
        entities.put("p1", 10.0);
        entities.put("p2", 25.0);
        eDTOG.setEntities(entities);

        Mockito.when(evaluationController.getEntityName(G1)).thenReturn("p1");
        Mockito.when(evaluationController.getEntityName(G2)).thenReturn("p2");

        List<SubjectEvaluation> inputList = new ArrayList<>();
        inputList.add(noG1); inputList.add(noG2);
        inputList.add(eG1); inputList.add(eG2); inputList.add(eG3);
        List<EvaluationDTO> outputList = new ArrayList<>();
        outputList.add(eDTOnoG); outputList.add(eDTOG);
        List<EvaluationDTO> output = subjectEvaluationController.filterEvaluations(inputList, "2023-05-11");
        assertEquals(outputList, output);
    }

    @Test
    void filterHistoricalEvaluations() {
        InternalMetric noG = new InternalMetric("im", "im", null, null, null, null, false);
        SubjectEvaluation noG1 = new SubjectEvaluation("2023-05-11", noG, "test", 10.0);
        SubjectEvaluation noG2 = new SubjectEvaluation("2023-05-10", noG, "test", 6.0);
        EvaluationDTO eDTOnoG = new EvaluationDTO(noG1);
        eDTOnoG.setValue(0.0);
        Map<String, Double> entities = new HashMap<>();
        entities.put("2023-05-11", 10.0);
        entities.put("2023-05-10", 6.0);
        eDTOnoG.setEntities(entities);

        InternalMetric G1 = new InternalMetric("im1", "im1", "p1", "p1", "c1", "c1", true);
        InternalMetric G2 = new InternalMetric("im2", "im2", "p2", "p2", "c1", "c1", true);
        SubjectEvaluation eG1 = new SubjectEvaluation("2023-05-11", G1, "test", 10.0);
        SubjectEvaluation eG2 = new SubjectEvaluation("2023-05-11", G2, "test", 5.0);
        SubjectEvaluation eG3 = new SubjectEvaluation("2023-05-10", G2, "test", 20.0);

        Mockito.when(evaluationController.getEntityName(G1)).thenReturn("p1");
        Mockito.when(evaluationController.getEntityName(G2)).thenReturn("p2");

        EvaluationDTO eDTOG = new EvaluationDTO(eG1);
        eDTOG.setValue(0.0);
        entities = new HashMap<>();
        entities.put("p1", 10.0);
        entities.put("p2", 25.0);
        eDTOG.setEntities(entities);

        List<SubjectEvaluation> inputList = new ArrayList<>();
        inputList.add(noG1); inputList.add(noG2);
        inputList.add(eG1); inputList.add(eG2); inputList.add(eG3);
        List<EvaluationDTO> outputList = new ArrayList<>();
        outputList.add(eDTOnoG); outputList.add(eDTOG);
        List<EvaluationDTO> output = subjectEvaluationController.filterHistoricalEvaluations(inputList);
        assertEquals(outputList, output);
    }

    @Test
    void filterHistoricalEvaluationsByParam() {
        InternalMetric G1 = new InternalMetric("im1", "im1", "p1", "p1", "c1", "c1", true);
        SubjectEvaluation eG1 = new SubjectEvaluation("2023-05-12", G1, "test", 10.0);
        SubjectEvaluation eG2 = new SubjectEvaluation("2023-05-11", G1, "test", 15.0);
        SubjectEvaluation eG3 = new SubjectEvaluation("2023-05-10", G1, "test", 20.0);

        EvaluationDTO eDTOG = new EvaluationDTO(eG1);
        eDTOG.setValue(0.0);
        Map<String,Double> entities = new HashMap<>();
        entities.put("2023-05-12", 10.0);
        entities.put("2023-05-11", 15.0);
        entities.put("2023-05-10", 20.0);
        eDTOG.setEntities(entities);

        List<SubjectEvaluation> inputList = new ArrayList<>();
        inputList.add(eG1); inputList.add(eG2); inputList.add(eG3);
        EvaluationDTO output = subjectEvaluationController.filterHistoricalEvaluationsByParam(inputList);
        assertEquals(eDTOG, output);
    }

    @Test
    void groupMetrics() {
        InternalMetric im1 = new InternalMetric("im1", "name1", "p1", "pName1", "c1", "cName1", true);
        UserlessInternalMetric im2 = new UserlessInternalMetric("im2", "name2", "p2", "pName2", "c2", "cName2", true, null, null);
        UserlessInternalMetric im3 = new UserlessInternalMetric("im3", "name3", "p3", "pName3", "c3", "cName3", true, "id", "name");
        UserlessInternalMetric im4 = new UserlessInternalMetric("im4", "name4", "p4", "pName4", "c3", "cName3", true, "id", "name");

        SubjectEvaluation e1 = new SubjectEvaluation("2023-05-19", im1, "PES", 10.0);
        SubjectEvaluation e2 = new SubjectEvaluation("2023-05-20", im1, "PES", 10.5);
        SubjectEvaluation e3 = new SubjectEvaluation("2023-05-19", im2, "PES", 1.0);
        SubjectEvaluation e4 = new SubjectEvaluation("2023-05-20", im2, "PES", 1.5);
        SubjectEvaluation e5 = new SubjectEvaluation("2023-05-19", im3, "PES", 5.0);
        SubjectEvaluation e6 = new SubjectEvaluation("2023-05-19", im4, "PES", 5.5);

        List<SubjectEvaluation> input = new ArrayList<>();
        input.add(e1); input.add(e2); input.add(e3);
        input.add(e4); input.add(e5); input.add(e6);

        List<SubjectEvaluation> output = subjectEvaluationController.groupMetrics(input);
        List<SubjectEvaluation> expectedOutput = new ArrayList<>();
        expectedOutput.add(e1); expectedOutput.add(e2);
        expectedOutput.add(e3); expectedOutput.add(e4);
        expectedOutput.add(new SubjectEvaluation("2023-05-19", im3, "PES", 10.5));

        assertEquals(expectedOutput, output);

    }
}