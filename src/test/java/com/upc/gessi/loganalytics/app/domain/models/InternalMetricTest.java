package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InternalMetricTest {

    private InternalMetric internalMetric;

    @BeforeEach
    void setUp() {
        internalMetric = new InternalMetric("test", "testParam", "testController");
    }

    @AfterEach
    void tearDown() {
        internalMetric = null;
    }

    @Test
    void getName() {
        assertEquals("test", internalMetric.getName());
    }

    @Test
    void setName() {
        internalMetric.setName("test2");
        assertEquals("test2", internalMetric.getName());
    }

    @Test
    void getParam() {
        assertEquals("testParam", internalMetric.getParam());
    }

    @Test
    void setParam() {
        internalMetric.setParam("testParam2");
        assertEquals("testParam2", internalMetric.getParam());
    }

    @Test
    void getController() {
        assertEquals("testController", internalMetric.getController());
    }

    @Test
    void setController() {
        internalMetric.setController("testController2");
        assertEquals("testController2", internalMetric.getController());
    }

    @Test
    void getEvaluations() {
        assertNull(internalMetric.getEvaluations());
    }

    @Test
    void setEvaluations() {
        Evaluation evaluation = new Evaluation("2023-04-18", internalMetric, 0.0);
        List<Evaluation> evaluationList = new ArrayList<>();
        evaluationList.add(evaluation);
        internalMetric.setEvaluations(evaluationList);
        assertEquals(internalMetric.getEvaluations().get(0), evaluation);
    }

    @Test
    void getTeamEvaluations() {
        assertNull(internalMetric.getTeamEvaluations());
    }

    @Test
    void setTeamEvaluations() {
        TeamEvaluation evaluation = new TeamEvaluation("2023-04-18", internalMetric, "team", 0.0);
        List<TeamEvaluation> evaluationList = new ArrayList<>();
        evaluationList.add(evaluation);
        internalMetric.setTeamEvaluations(evaluationList);
        assertEquals(internalMetric.getTeamEvaluations().get(0), evaluation);
    }

    @Test
    void getSubjectEvaluations() {
        assertNull(internalMetric.getSubjectEvaluations());
    }

    @Test
    void setSubjectEvaluations() {
        SubjectEvaluation evaluation = new SubjectEvaluation("2023-04-18", internalMetric, "pes", 0.0);
        List<SubjectEvaluation> evaluationList = new ArrayList<>();
        evaluationList.add(evaluation);
        internalMetric.setSubjectEvaluations(evaluationList);
        assertEquals(internalMetric.getSubjectEvaluations().get(0), evaluation);
    }

    @Test
    void testToString() {
        String result = "InternalMetric{name='test', param='testParam', controller='testController'}";
        assertEquals(result, internalMetric.toString());
    }
}