package com.upc.gessi.loganalytics.app.rest.DTOs;

import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionDTOTest {

    private Subject s;
    private Team t;
    private SessionDTO sessionDTO;

    @BeforeEach
    void setUp() {
        s = new Subject("sub");
        t = new Team("pes11a", "sem", s);
        Session session = new Session("s", t, 0, 5, 5, 1);
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
        assertEquals(t, sessionDTO.getTeam());
    }

    @Test
    void setTeam() {
        t = new Team("pes11b", "sem", s);
        sessionDTO.setTeam(t);
        assertEquals(t, sessionDTO.getTeam());
    }

    @Test
    void getDuration() {
        assertEquals(5, sessionDTO.getDuration());
    }

    @Test
    void setDuration() {
        sessionDTO.setDuration(10);
        assertEquals(10, sessionDTO.getDuration());
    }

    @Test
    void getnInteractions() {
        assertEquals(1, sessionDTO.getnInteractions());
    }

    @Test
    void setnInteractions() {
        sessionDTO.setnInteractions(20);
        assertEquals(20, sessionDTO.getnInteractions());
    }
}