package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    private Log log;
    private Session s;
    private Team t;
    private Subject sub;

    @BeforeEach
    void setUp() {
        sub = new Subject("sub");
        t = new Team("pes11a", "sem", sub);
        s = new Session(t, 0);
        log = new Log(0, "testTeam", "testMessage", "testPage", s);
    }

    @AfterEach
    void tearDown() {
        log = null;
        s = null;
        t = null;
        sub = null;
    }

    @Test
    void getTime() {
        assertEquals(0, log.getTime());
    }

    @Test
    void setTime() {
        log.setTime(1);
        assertEquals(1, log.getTime());
    }

    @Test
    void getTeam() {
        assertEquals("testTeam", log.getTeam());
    }

    @Test
    void setTeam() {
        log.setTeam("testTeam2");
        assertEquals("testTeam2", log.getTeam());
    }

    @Test
    void getMessage() {
        assertEquals("testMessage", log.getMessage());
    }

    @Test
    void setMessage() {
        log.setMessage("testMessage2");
        assertEquals("testMessage2", log.getMessage());
    }

    @Test
    void getPage() {
        assertEquals("testPage", log.getPage());
    }

    @Test
    void setPage() {
        log.setPage("testPage2");
        assertEquals("testPage2", log.getPage());
    }

    @Test
    void getSession() {
        assertEquals(s, log.getSession());
    }

    @Test
    void setSession() {
        s = new Session(t, 1);
        log.setSession(s);
        assertEquals(s, log.getSession());
    }

    @Test
    void testToString() {
        String logString = "Log{time=0, team='testTeam', message='testMessage', " +
            "page='testPage', session=Session{team=Team{id='pes11a', semester='sem', " +
            "subject='Subject{acronym='sub'}'}, startTimestamp=0, " +
            "endTimestamp=0, duration=0.0, nInteractions=0}}";
        assertEquals(log.toString(), logString);
    }
}