package com.upc.gessi.loganalytics.app.domain.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    private Subject subject;

    @BeforeEach
    void setUp() {
        subject = new Subject("PES");
    }

    @AfterEach
    void tearDown() {
        subject = null;
    }

    @Test
    void getAcronym() {
        assertEquals("PES", subject.getAcronym());
    }

    @Test
    void setAcronym() {
        subject.setAcronym("ASW");
        assertEquals("ASW", subject.getAcronym());
    }

    @Test
    void getTeams() {
        assertNull(subject.getTeams());
    }

    @Test
    void setTeams() {
        List<Team> teams = new ArrayList<>();
        Team t = new Team("pes11a", "sem", subject);
        teams.add(t);
        subject.setTeams(teams);
        assertEquals(teams, subject.getTeams());
    }

    @Test
    void testToString() {
        String result = "Subject{acronym='PES'}";
        assertEquals(result, subject.toString());
    }
}