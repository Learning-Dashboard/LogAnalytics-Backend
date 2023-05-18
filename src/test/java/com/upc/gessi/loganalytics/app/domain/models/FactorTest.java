package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactorTest {

    private Factor f;

    @BeforeEach
    void setUp() {
        f = new Factor("factorTest", "factorName");
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
    void getName() {
        assertEquals(f.getName(), "factorName");
    }

    @Test
    void setName() {
        f.setName("factorName2");
        assertEquals(f.getName(), "factorName2");
    }

    @Test
    void testToString() {
        String factorString = "Factor{id='factorTest', name='factorName'}";
        assertEquals(f.toString(), factorString);
    }
}