package com.upc.gessi.loganalytics.app.domain.models;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetricTest {

    private Metric m;

    @BeforeEach
    public void setUp() {
        m = new Metric("metricTest");
    }

    @AfterEach
    public void tearDown() {
        m = null;
    }

    @Test
    void getId() {
        assertEquals(m.getId(), "metricTest");
    }

    @Test
    void setId() {
        m.setId("metricTest2");
        assertEquals(m.getId(), "metricTest2");
    }

    @Test
    void testToString() {
        String metricString = "Metric{id='metricTest'}";
        assertEquals(m.toString(), metricString);
    }
}