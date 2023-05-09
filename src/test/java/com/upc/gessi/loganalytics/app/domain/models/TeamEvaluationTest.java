package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamEvaluationTest {

    private TeamEvaluation evaluation;
    private InternalMetric internalMetric;

    @BeforeEach
    void setUp() {
        internalMetric = new InternalMetric("test", "Test");
        evaluation = new TeamEvaluation("2023-04-18", internalMetric, "pes", 0.0);
    }

    @AfterEach
    void tearDown() {
        internalMetric = null;
        evaluation = null;
    }

    @Test
    void getTeam() {
        assertEquals("pes", evaluation.getTeam());
    }

    @Test
    void setTeam() {
        evaluation.setTeam("asw");
        assertEquals("asw", evaluation.getTeam());
    }

    @Test
    void getDate() {
        assertEquals(evaluation.getDate(), "2023-04-18");
    }

    @Test
    void setDate() {
        evaluation.setDate("2023-05-18");
        assertEquals(evaluation.getDate(), "2023-05-18");
    }

    @Test
    void getInternalMetric() {
        assertEquals(evaluation.getInternalMetric(), internalMetric);
    }

    @Test
    void setInternalMetric() {
        InternalMetric internalMetric2 = new InternalMetric("test2", "Test 2");
        evaluation.setInternalMetric(internalMetric2);
        assertEquals(evaluation.getInternalMetric(), internalMetric2);
    }

    @Test
    void getValue() {
        assertEquals(0.0, evaluation.getValue());
    }

    @Test
    void setValue() {
        evaluation.setValue(1.5);
        assertEquals(1.5, evaluation.getValue());
    }

    @Test
    void testToString() {
        String result = "TeamEvaluation{date='2023-04-18', " +
            "internalMetric=InternalMetric{id='test', name='Test', " +
            "param='null', controller='null', controllerName='null', " +
            "groupable=false, teams=null}, team='pes', value=0.0}";
        assertEquals(result, evaluation.toString());
    }
}