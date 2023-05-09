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
        List<String> teams = new ArrayList<>();
        teams.add("testTeam");
        internalMetric = new InternalMetric("test", "testName", "testParam", "testController", "testControllerName", false, teams);
    }

    @AfterEach
    void tearDown() {
        internalMetric = null;
    }

    @Test
    void getId() {
        assertEquals("test", internalMetric.getId());
    }

    @Test
    void setId() {
        internalMetric.setId("test2");
        assertEquals("test2", internalMetric.getId());
    }

    @Test
    void getName() {
        assertEquals("testName", internalMetric.getName());
    }

    @Test
    void setName() {
        internalMetric.setName("testName2");
        assertEquals("testName2", internalMetric.getName());
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
    void getControllerName() {
        assertEquals("testControllerName", internalMetric.getControllerName());
    }

    @Test
    void setControllerName() {
        internalMetric.setControllerName("testControllerName2");
        assertEquals("testControllerName2", internalMetric.getControllerName());
    }

    @Test
    void getGroupable() {
        assertFalse(internalMetric.isGroupable());
    }

    @Test
    void setGroupable() {
        internalMetric.setGroupable(true);
        assertTrue(internalMetric.isGroupable());
    }

    @Test
    void getTeams() {
        assertEquals("testTeam", internalMetric.getTeams().get(0));
        assertEquals(1, internalMetric.getTeams().size());
    }

    @Test
    void setTeams() {
        List<String> teams = new ArrayList<>();
        teams.add("testTeam2");
        internalMetric.setTeams(teams);
        assertEquals("testTeam2", internalMetric.getTeams().get(0));
        assertEquals(1, internalMetric.getTeams().size());
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
        String result = "InternalMetric{id='test', name='testName', " +
            "param='testParam', controller='testController', " +
            "controllerName='testControllerName', groupable=false, teams=[testTeam]}";
        assertEquals(result, internalMetric.toString());
    }
}