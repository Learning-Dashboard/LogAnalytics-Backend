package com.upc.gessi.loganalytics.app.domain.models;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetricTest {

    private Metric m;

    @BeforeEach
    public void setUp() {
        m = new Metric("metricTest", "metricName",
            "metricNoUserId", "metricNoUserName");
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
    void getName() {
        assertEquals(m.getName(), "metricName");
    }

    @Test
    void setName() {
        m.setName("metricName2");
        assertEquals(m.getName(), "metricName2");
    }

    @Test
    void getNoUserId() {
        assertEquals(m.getNoUserId(), "metricNoUserId");
    }

    @Test
    void setNoUserId() {
        m.setNoUserId("metricNoUserId2");
        assertEquals(m.getNoUserId(), "metricNoUserId2");
    }

    @Test
    void getNoUserName() {
        assertEquals(m.getNoUserName(), "metricNoUserName");
    }

    @Test
    void setNoUserName() {
        m.setNoUserName("metricNoUserName2");
        assertEquals(m.getNoUserName(), "metricNoUserName2");
    }

    @Test
    void testToString() {
        String metricString = "Metric{id='metricTest', name='metricName', " +
            "noUserId='metricNoUserId', noUserName='metricNoUserName'}";
        assertEquals(m.toString(), metricString);
    }
}