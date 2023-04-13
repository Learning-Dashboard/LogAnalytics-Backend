package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Log;
import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.SessionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {

    @Mock
    SessionRepository sessionRepository;
    @Mock
    TeamController teamController;

    @InjectMocks
    SessionController sessionController;

    @Test
    void createSession() {
        long startTimestamp = 0;
        String teamId = "t1";
        Subject s = new Subject("s");
        Team team = new Team("t1", "22-23-Q1", s);
        Mockito.when(teamController.getSemester()).thenReturn("22-23-Q1");
        Mockito.when(teamController.getTeam(Mockito.eq(teamId), Mockito.eq("22-23-Q1"))).thenReturn(team);
        sessionController.createSession("s", startTimestamp, teamId);
        Mockito.verify(sessionRepository, Mockito.times(1)).save(Mockito.any(Session.class));
    }

    @Test
    void updateSession() {
        long startTimestamp = 0;
        String teamId = "t1";
        Subject s = new Subject("s");
        Team team = new Team("t1", "22-23-Q1", s);
        Session session = new Session("s", team, startTimestamp);
        Mockito.when(sessionRepository.findById("s")).thenReturn(Optional.of(session));
        sessionController.updateSession("s", 1);
        Mockito.verify(sessionRepository, Mockito.times(1)).save(Mockito.any(Session.class));
    }

    @Test
    void getSessionToStoreLog() {
        Subject s = new Subject("s");
        Team team = new Team("t1", "22-23-Q1", s);
        Session session = new Session("s", team, 0);
        session.setEndTimestamp(1);
        Mockito.when(sessionRepository.findById("s")).thenReturn(Optional.of(session));
        Session result = sessionController.getSessionToStoreLog("s");
        Mockito.verify(sessionRepository, Mockito.times(1)).save(Mockito.any(Session.class));
        assertEquals(result, session);
    }

    @Test
    void getAll() {
        Subject s = new Subject("s");
        Team team = new Team("t1", "22-23-Q1", s);
        Session session = new Session("s", team, 0);
        List<Session> sessions = new ArrayList<>();
        sessions.add(session);
        Mockito.when(sessionRepository.findAll()).thenReturn(sessions);
        List<Session> actualSessions = sessionController.getAll();
        assertEquals(sessions, actualSessions);
    }
}