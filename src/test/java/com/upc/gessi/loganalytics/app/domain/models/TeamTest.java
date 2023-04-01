package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private Subject subject;
    private Team team;

    @BeforeEach
    void setUp() {
        subject = new Subject("PES");
        team = new Team("pes11a", "sem", subject);
    }

    @AfterEach
    void tearDown() {
        team = null;
        subject = null;
    }

    @Test
    void getId() {
        assertEquals("pes11a", team.getId());
    }

    @Test
    void setId() {
        team.setId("pes11b");
        assertEquals("pes11b", team.getId());
    }

    @Test
    void getSemester() {
        assertEquals("sem", team.getSemester());
    }

    @Test
    void setSemester() {
        team.setSemester("sem2");
        assertEquals("sem2", team.getSemester());
    }

    @Test
    void getSubject() {
        assertEquals(subject, team.getSubject());
    }

    @Test
    void setSubject() {
        Subject subject2 = new Subject("ASW");
        team.setSubject(subject2);
        assertEquals(subject2, team.getSubject());
    }

    @Test
    void getSessions() {
        assertNull(team.getSessions());
    }

    @Test
    void setSessions() {
        List<Session> sessions = new ArrayList<>();
        Session s = new Session(team, 0);
        sessions.add(s);
        team.setSessions(sessions);
        assertEquals(sessions, team.getSessions());
    }

    @Test
    void testToString() {
        String result = "Team{id='pes11a', semester='sem', " +
            "subject='Subject{acronym='PES'}'}";
        assertEquals(result, team.toString());
    }
}