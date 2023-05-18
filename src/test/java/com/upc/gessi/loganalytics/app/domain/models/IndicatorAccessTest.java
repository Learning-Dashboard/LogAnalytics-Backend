package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IndicatorAccessTest {

    private IndicatorAccess indicatorAccess;
    private Session s;
    private Indicator i;
    private List<Indicator> indicators;

    @BeforeEach
    void setUp() {
        s = new Session();
        i = new Indicator("i1");
        indicators = new ArrayList<>();
        indicators.add(i);
        indicatorAccess = new IndicatorAccess(0, "pes11a",
                "testMessage", "testPage", s,
                true, "testViewFormat", indicators);
    }

    @AfterEach
    void tearDown() {
        s = null;
        i = null;
        indicatorAccess = null;
        indicators = new ArrayList<>();
    }

    @Test
    void isHistoric() {
        assertTrue(indicatorAccess.isHistoric());
    }

    @Test
    void setHistoric() {
        indicatorAccess.setHistoric(false);
        assertFalse(indicatorAccess.isHistoric());
    }

    @Test
    void getViewFormat() {
        assertEquals(indicatorAccess.getViewFormat(), "testViewFormat");
    }

    @Test
    void setViewFormat() {
        indicatorAccess.setViewFormat("testViewFormat2");
        assertEquals(indicatorAccess.getViewFormat(), "testViewFormat2");
    }

    @Test
    void getIndicators() {
        assertEquals(indicatorAccess.getIndicators(), indicators);
    }

    @Test
    void setIndicators() {
        Indicator i2 = new Indicator("i2");
        indicators.add(i2);
        indicatorAccess.setIndicators(indicators);
        assertEquals(indicatorAccess.getIndicators(), indicators);
    }

    @Test
    void testToString() {
        String result = "IndicatorAccess{" +
                "historic=true, viewFormat='testViewFormat', " +
                "indicators=[Indicator{id='i1', name='null'}]} Log{time=0, team='pes11a', " +
                "message='testMessage', page='testPage', " +
                "session=Session{id='null', team=null, startTimestamp=0, endTimestamp=0, " +
                "duration=0.0, nInteractions=0}}";
        assertEquals(result, indicatorAccess.toString());
    }
}