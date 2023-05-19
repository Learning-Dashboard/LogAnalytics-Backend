package com.upc.gessi.loganalytics.app.rest.DTOs;

import com.upc.gessi.loganalytics.app.domain.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationDTOTest {

    private EvaluationDTO evaluationDTO;
    private InternalMetric internalMetric;

    @BeforeEach
    void setUp() {
        internalMetric = new InternalMetric("im", "imName");
        SubjectEvaluation subjectEvaluation = new SubjectEvaluation("2001-07-22", internalMetric, "PES", 10.0);
        evaluationDTO = new EvaluationDTO(subjectEvaluation);
    }

    @Test
    void getName() {
        assertEquals(evaluationDTO.getName(), "imName");
    }

    @Test
    void setName() {
        evaluationDTO.setName("testName");
        assertEquals(evaluationDTO.getName(), "testName");
    }

    @Test
    void getSubject() {
        assertEquals("PES", evaluationDTO.getSubject());
    }

    @Test
    void setSubject() {
        evaluationDTO.setSubject("ASW");
        assertEquals("ASW", evaluationDTO.getSubject());
    }

    @Test
    void getTeam() {
        assertNull(evaluationDTO.getTeam());
    }

    @Test
    void setTeam() {
        evaluationDTO.setTeam("ASW");
        assertEquals("ASW", evaluationDTO.getTeam());
    }

    @Test
    void getDate() {
        assertEquals("2001-07-22", evaluationDTO.getDate());
    }

    @Test
    void setDate() {
        evaluationDTO.setDate("2001-10-20");
        assertEquals("2001-10-20", evaluationDTO.getDate());
    }

    @Test
    void getInternalMetric() {
        assertEquals(internalMetric, evaluationDTO.getInternalMetric());
    }

    @Test
    void setInternalMetric() {
        InternalMetric im = new InternalMetric("im2", "im2Name");
        evaluationDTO.setInternalMetric(im);
        assertEquals(im, evaluationDTO.getInternalMetric());
    }

    @Test
    void getValue() {
        assertEquals(10.0, evaluationDTO.getValue());
    }

    @Test
    void setValue() {
        evaluationDTO.setValue(15.0);
        assertEquals(15.0, evaluationDTO.getValue());
    }

    @Test
    void isGroupable() {
        assertFalse(evaluationDTO.isGroupable());
    }

    @Test
    void setGroupable() {
        evaluationDTO.setGroupable(true);
        assertTrue(evaluationDTO.isGroupable());
    }

    @Test
    void getEntities() {
        assertTrue(evaluationDTO.getEntities().isEmpty());
    }

    @Test
    void setEntities() {
        Map<String, Double> entities = new HashMap<>();
        entities.put("Laura", 1.0);
        entities.put("Laura2", 2.0);
        evaluationDTO.setEntities(entities);
        assertEquals(evaluationDTO.getEntities(), entities);
    }

    @Test
    void testToString() {
        String result = "EvaluationDTO{name='imName', " +
            "subject='PES', team='null', date='2001-07-22', " +
            "value=10.0, groupable=false, entities={}}";
        assertEquals(result, evaluationDTO.toString());
    }
}