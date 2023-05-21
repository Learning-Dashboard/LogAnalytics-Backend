package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.SessionRepository;
import com.upc.gessi.loganalytics.app.rest.DTOs.SessionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
        List<Session> sessions = new ArrayList<>();
        List<SessionDTO> sessionDTOS = new ArrayList<>();
        Subject subject = new Subject("PES");
        Team team = new Team("t1", "sem", subject);
        sessionDTOS.add(new SessionDTO(new Session("s1", team, 0)));
        sessionDTOS.add(new SessionDTO(new Session("s2", team, 10)));
        sessions.add(new Session("s1", team, 0));
        sessions.add(new Session("s2", team, 10));
        Mockito.when(sessionRepository.findAll()).thenReturn(sessions);
        List<SessionDTO> actualSessions = sessionController.getAll();
        assertEquals(sessionDTOS, actualSessions);
    }

    @Test
    void getAllFromYesterday() {
        Subject s = new Subject("s");
        Team team = new Team("t1", "22-23-Q1", s);
        Session session = new Session("s", team, 0);
        List<Session> sessions = new ArrayList<>();
        sessions.add(session);
        Mockito.when(sessionRepository.findByStartTimestampLessThan(Mockito.anyLong())).thenReturn(sessions);
        List<Session> actualSessions = sessionController.getAllFromYesterday();
        assertEquals(sessions, actualSessions);
    }

    @Test
    void getAllByTeam() {
        Subject s = new Subject("s");
        Team team1 = new Team("t1", "22-23-Q1", s);
        Session session1 = new Session("s1", team1, 0);
        List<Session> sessions = new ArrayList<>();
        sessions.add(session1);
        Mockito.when(sessionRepository.findByTeamAndStartTimestampLessThan(Mockito.any(), Mockito.anyLong())).thenReturn(sessions);
        List<Session> actualSessions = sessionController.getAllByTeam(team1);
        assertEquals(sessions, actualSessions);
    }

    @Test
    void getAllBySubject() {
        Subject s = new Subject("s");
        Team team1 = new Team("t1", "22-23-Q1", s);
        Session session1 = new Session("s1", team1, 0);
        List<Session> sessions = new ArrayList<>();
        sessions.add(session1);
        Mockito.when(sessionRepository.findByTeamSubjectAndStartTimestampLessThan(Mockito.any(), Mockito.anyLong())).thenReturn(sessions);
        List<Session> actualSessions = sessionController.getAllBySubject(s);
        assertEquals(sessions, actualSessions);
    }

    @Test
    void getAllByTimestampBetweenAndTeam() {
        Subject s = new Subject("s");
        Team team1 = new Team("t1", "22-23-Q1", s);
        Session session1 = new Session("s1", team1, 0, 50, 50, 4);
        List<Session> sessions = new ArrayList<>();
        sessions.add(session1);
        Mockito.when(sessionRepository.findByStartTimestampBetweenAndTeam(0, 100, team1)).thenReturn(sessions);
        List<Session> actualSessions = sessionController.getAllByTimestampBetweenAndTeam(0, 100, team1);
        assertEquals(sessions, actualSessions);
    }
}