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
        internalMetric = new InternalMetric("test") {
            @Override
            public double evaluate(Team team) {
                return 0;
            }
        };
    }

    @AfterEach
    void tearDown() {
        internalMetric = null;
    }

    @Test
    void evaluate() {
        Subject s = new Subject("subject");
        Team team = new Team("team", "sem", s);
        assertEquals(0.0, internalMetric.evaluate(team));
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
        String result = "InternalMetric{name='test'}";
        assertEquals(result, internalMetric.toString());
    }
}