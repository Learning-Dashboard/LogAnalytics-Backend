package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetricAccessTest {

    private MetricAccess metricAccess;
    private Session s;
    private Metric m;
    private List<Metric> metrics;

    @BeforeEach
    void setUp() {
        s = new Session();
        m = new Metric("m1");
        metrics = new ArrayList<>();
        metrics.add(m);
        metricAccess = new MetricAccess(0, "pes11a",
                "testMessage", "testPage", s,
                true, "testViewFormat", metrics);
    }

    @AfterEach
    void tearDown() {
        s = null;
        m = null;
        metricAccess = null;
        metrics = new ArrayList<>();
    }

    @Test
    void isHistoric() {
        assertTrue(metricAccess.isHistoric());
    }

    @Test
    void setHistoric() {
        metricAccess.setHistoric(false);
        assertFalse(metricAccess.isHistoric());
    }

    @Test
    void getViewFormat() {
        assertEquals(metricAccess.getViewFormat(), "testViewFormat");
    }

    @Test
    void setViewFormat() {
        metricAccess.setViewFormat("testViewFormat2");
        assertEquals(metricAccess.getViewFormat(), "testViewFormat2");
    }

    @Test
    void getMetrics() {
        assertEquals(metricAccess.getMetrics(), metrics);
    }

    @Test
    void setMetrics() {
        Metric m2 = new Metric("m2");
        metrics.add(m2);
        metricAccess.setMetrics(metrics);
        assertEquals(metricAccess.getMetrics(), metrics);
    }

    @Test
    void testToString() {
        String result = "MetricAccess{" +
                "historic=true, viewFormat='testViewFormat', " +
                "metrics=[Metric{id='m1'}]} Log{time=0, team='pes11a', " +
                "message='testMessage', page='testPage', " +
                "session=Session{id='null', team=null, startTimestamp=0, endTimestamp=0, " +
                "duration=0.0, nInteractions=0}}";
        assertEquals(result, metricAccess.toString());
    }
}