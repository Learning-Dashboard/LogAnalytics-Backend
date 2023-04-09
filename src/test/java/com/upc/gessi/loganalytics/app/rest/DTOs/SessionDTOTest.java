package com.upc.gessi.loganalytics.app.rest.DTOs;

import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionDTOTest {

    private Subject s;
    private Team t;
    private Session session;
    private SessionDTO sessionDTO;

    @BeforeEach
    void setUp() {
        s = new Subject("sub");
        t = new Team("pes11a", "sem", s);
        session = new Session("s", t, 0, 5, 5, 1);
        sessionDTO = new SessionDTO(session);
    }

    @Test
    void getId() {
        assertEquals("s", sessionDTO.getId());
    }

    @Test
    void setId() {
        sessionDTO.setId("s2");
        assertEquals("s2", sessionDTO.getId());
    }

    @Test
    void getStartTimestamp() {
        assertEquals(0, sessionDTO.getStartTimestamp());
    }

    @Test
    void setStartTimestamp() {
        sessionDTO.setStartTimestamp(10);
        assertEquals(10, sessionDTO.getStartTimestamp());
    }

    @Test
    void getEndTimestamp() {
        assertEquals(5, sessionDTO.getEndTimestamp());
    }

    @Test
    void setEndTimestamp() {
        sessionDTO.setEndTimestamp(15);
        assertEquals(15, sessionDTO.getEndTimestamp());
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
}