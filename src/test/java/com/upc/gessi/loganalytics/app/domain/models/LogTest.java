package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    private Log log;

    @BeforeEach
    void setUp() {
        log = new Log(0, "testTeam", "testMessage", "testPage");
    }

    @AfterEach
    void tearDown() {
        log = null;
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
    void testToString() {
        String logString = "Log{time=0, team='testTeam', message='testMessage', page='testPage'}";
        assertEquals(log.toString(), logString);
    }
}