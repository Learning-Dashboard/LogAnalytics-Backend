package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    private Subject s;
    private Team t;
    private Session session;

    @BeforeEach
    void setUp() {
        s = new Subject("sub");
        t = new Team("pes11a", "sem", s);
        session = new Session(t, 0, 5, 5, 1);
    }

    @AfterEach
    void tearDown() {
        s = null;
        t = null;
        session = null;
    }

    @Test
    void getTeam() {
        assertEquals(t, session.getTeam());
    }

    @Test
    void setTeam() {
        t = new Team("pes11b", "sem", s);
        session.setTeam(t);
        assertEquals(t, session.getTeam());
    }

    @Test
    void getStartTimestamp() {
        assertEquals(0, session.getStartTimestamp());
    }

    @Test
    void setStartTimestamp() {
        session.setStartTimestamp(1);
        assertEquals(1, session.getStartTimestamp());
    }

    @Test
    void getEndTimestamp() {
        assertEquals(5, session.getEndTimestamp());
    }

    @Test
    void setEndTimestamp() {
        session.setEndTimestamp(10);
        assertEquals(10, session.getEndTimestamp());
    }

    @Test
    void getDuration() {
        assertEquals(5, session.getDuration());
    }

    @Test
    void setDuration() {
        session.setDuration(10);
        assertEquals(10, session.getDuration());
    }

    @Test
    void getnInteractions() {
        assertEquals(1, session.getnInteractions());
    }

    @Test
    void setnInteractions() {
        session.setnInteractions(20);
        assertEquals(20, session.getnInteractions());
    }

    @Test
    void getLogs() {
        assertNull(session.getLogs());
    }

    @Test
    void setLogs() {
        List<Log> logs = new ArrayList<>();
        Log l = new Log(0, "testTeam", "testMessage", "testPage");
        session.setLogs(logs);
        assertEquals(logs, session.getLogs());
    }

    @Test
    void testToString() {
        String result = "Session{team=Team{id='pes11a', semester='sem', " +
            "subject='Subject{acronym='sub'}'}, startTimestamp=0, " +
            "endTimestamp=5, duration=5.0, nInteractions=1}";
        assertEquals(result, session.toString());
    }
}