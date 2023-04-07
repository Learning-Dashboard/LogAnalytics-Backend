package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QModelAccessTest {

    private QModelAccess qModelAccess;
    private Session s;

    @BeforeEach
    void setUp() {
        s = new Session();
        qModelAccess = new QModelAccess(0, "pes11a",
            "testMessage", "testPage", s,
            "testViewFormat");
    }

    @AfterEach
    void tearDown() {
        s = null;
        qModelAccess = null;
    }

    @Test
    void testToString() {
        String result = "QModelAccess{" +
                "viewFormat='testViewFormat'} " +
                "Log{time=0, team='pes11a', " +
                "message='testMessage', page='testPage', " +
                "session=Session{id='null', team=null, startTimestamp=0, endTimestamp=0, " +
                "duration=0.0, nInteractions=0}}";
        assertEquals(result, qModelAccess.toString());
    }
}