package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndicatorTest {

    private Indicator i;

    @BeforeEach
    void setUp() {
        i = new Indicator("indicatorTest", "indicatorName");
    }

    @AfterEach
    void tearDown() {
        i = null;
    }

    @Test
    void getId() {
        assertEquals(i.getId(), "indicatorTest");
    }

    @Test
    void setId() {
        i.setId("indicatorTest2");
        assertEquals(i.getId(), "indicatorTest2");
    }

    @Test
    void getName() {
        assertEquals(i.getName(), "indicatorName");
    }

    @Test
    void setName() {
        i.setName("indicatorName2");
        assertEquals(i.getName(), "indicatorName2");
    }

    @Test
    void testToString() {
        String indicatorString = "Indicator{id='indicatorTest', name='indicatorName'}";
        assertEquals(i.toString(), indicatorString);
    }
}