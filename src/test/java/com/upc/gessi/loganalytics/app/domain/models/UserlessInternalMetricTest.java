package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserlessInternalMetricTest {

    private UserlessInternalMetric uim;

    @BeforeEach
    void setUp() {
        uim = new UserlessInternalMetric("im", "inName", "param",
            "paramName", "controller", "controllerName", true, null, "id", "name");
    }

    @AfterEach
    void tearDown() {
        uim = null;
    }

    @Test
    void getUserlessId() {
        assertEquals("id", uim.getUserlessId());
    }

    @Test
    void setUserlessId() {
        uim.setUserlessId("id2");
        assertEquals("id2", uim.getUserlessId());
    }

    @Test
    void getUserlessName() {
        assertEquals("name", uim.getUserlessName());
    }

    @Test
    void setUserlessName() {
        uim.setUserlessName("name2");
        assertEquals("name2", uim.getUserlessName());
    }

    @Test
    void testToString() {
        String result = "UserlessInternalMetric{userlessId='id', userlessName='name'} " +
            "InternalMetric{id='im', name='inName', param='param', paramName='paramName', " +
            "controller='controller', controllerName='controllerName', groupable=true, teams=null}";
        assertEquals(result, uim.toString());
    }
}