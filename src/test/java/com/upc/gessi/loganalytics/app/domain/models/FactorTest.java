package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactorTest {

    private Factor f;

    @BeforeEach
    void setUp() {
        f = new Factor("factorTest");
    }

    @AfterEach
    void tearDown() {
        f = null;
    }

    @Test
    void getId() {
        assertEquals(f.getId(), "factorTest");
    }

    @Test
    void setId() {
        f.setId("factorTest2");
        assertEquals(f.getId(), "factorTest2");
    }

    @Test
    void testToString() {
        String factorString = "Factor{id='factorTest'}";
        assertEquals(f.toString(), factorString);
    }
}