package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionRestControllerTest {

    @Mock
    SessionRepository sessionRepository;

    @InjectMocks
    SessionRestController sessionRestController;

    @Test
    void findAllSessions() {
        List<Session> sessions = new ArrayList<>();
        Subject subject = new Subject("PES");
        Team team = new Team("t1", "sem", subject);
        sessions.add(new Session("s1", team, 0));
        sessions.add(new Session("s2", team, 10));
        when(sessionRepository.findAll()).thenReturn(sessions);
        List<Session> actualSessions = sessionRestController.findAllSessions();
        assertEquals(sessions, actualSessions);
    }
}