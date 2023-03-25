package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndicatorTest {

    private Indicator i;

    @BeforeEach
    void setUp() {
        i = new Indicator("indicatorTest");
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
    void testToString() {
        String indicatorString = "Indicator{id='indicatorTest'}";
        assertEquals(i.toString(), indicatorString);
    }
}