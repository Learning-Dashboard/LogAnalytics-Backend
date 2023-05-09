package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationTest {

    private Evaluation evaluation;

    private InternalMetric internalMetric;

    @BeforeEach
    void setUp() {
        internalMetric = new InternalMetric("test", "test");
        evaluation = new Evaluation("2023-04-18", internalMetric, 0.0);
    }

    @AfterEach
    void tearDown() {
        internalMetric = null;
        evaluation = null;
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
        InternalMetric internalMetric2 = new InternalMetric("test2", "test2");
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
        String result = "Evaluation{date=2023-04-18, internalMetric=InternalMetric" +
            "{id='test', name='test', param='null', controller='null', " +
            "controllerName='null', groupable=false, teams=null}, value=0.0}";
        assertEquals(result, evaluation.toString());
    }
}