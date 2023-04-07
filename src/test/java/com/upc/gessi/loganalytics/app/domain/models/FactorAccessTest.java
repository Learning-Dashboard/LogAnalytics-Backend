package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FactorAccessTest {

    private FactorAccess factorAccess;
    private Session s;
    private Factor f;
    private List<Factor> factors;

    @BeforeEach
    void setUp() {
        s = new Session();
        f = new Factor("f1");
        factors = new ArrayList<>();
        factors.add(f);
        factorAccess = new FactorAccess(0, "pes11a",
                "testMessage", "testPage", s,
                true, "testViewFormat", factors);
    }

    @AfterEach
    void tearDown() {
        s = null;
        f = null;
        factorAccess = null;
        factors = new ArrayList<>();
    }

    @Test
    void isHistoric() {
        assertTrue(factorAccess.isHistoric());
    }

    @Test
    void setHistoric() {
        factorAccess.setHistoric(false);
        assertFalse(factorAccess.isHistoric());
    }

    @Test
    void getViewFormat() {
        assertEquals(factorAccess.getViewFormat(), "testViewFormat");
    }

    @Test
    void setViewFormat() {
        factorAccess.setViewFormat("testViewFormat2");
        assertEquals(factorAccess.getViewFormat(), "testViewFormat2");
    }

    @Test
    void getFactors() {
        assertEquals(factorAccess.getFactors(), factors);
    }

    @Test
    void setFactors() {
        Factor f2 = new Factor("f2");
        factors.add(f2);
        factorAccess.setFactors(factors);
        assertEquals(factorAccess.getFactors(), factors);
    }

    @Test
    void testToString() {
        String result = "FactorAccess{" +
            "historic=true, viewFormat='testViewFormat', " +
            "factors=[Factor{id='f1'}]} Log{time=0, team='pes11a', " +
            "message='testMessage', page='testPage', " +
            "session=Session{id='null', team=null, startTimestamp=0, endTimestamp=0, " +
            "duration=0.0, nInteractions=0}}";
        assertEquals(result, factorAccess.toString());
    }
}