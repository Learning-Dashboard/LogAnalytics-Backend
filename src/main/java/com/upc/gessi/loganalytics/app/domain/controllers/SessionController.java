package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SessionController {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    TeamController teamController;

    public Session createSession(String sessionId, long startTimestamp, String teamId) {
        String semester = teamController.getSemester();
        Team team = teamController.getTeam(teamId, semester);
        if (team != null) {
            Session session = new Session(sessionId, team,  startTimestamp);
            session.setnInteractions(1);
            sessionRepository.save(session);
            return session;
        }
        return null;
    }

    public Session updateSession(String sessionId, long endTimestamp) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isPresent()) {
            Session s = sessionOptional.get();
            s.setEndTimestamp(endTimestamp);
            long duration = (endTimestamp - s.getStartTimestamp()) / 1000;
            s.setDuration(duration);
            int n = s.getnInteractions() + 1;
            s.setnInteractions(n);
            sessionRepository.save(s);
            return s;
        }
        return null;
    }

    public Session getSessionToStoreLog(String sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (sessionOptional.isPresent()) {
            Session s = sessionOptional.get();
            int n = s.getnInteractions() + 1;
            s.setnInteractions(n);
            sessionRepository.save(s);
            return s;
        }
        return null;
    }

    public List<Session> getAll() {
        Iterable<Session> sessionIterable = sessionRepository.findAll();
        List<Session> sessionList = new ArrayList<>();
        sessionIterable.forEach(sessionList::add);
        return sessionList;
    }

    public List<Session> getAllByTeam(Team team) {
        Iterable<Session> sessionIterable = sessionRepository.findByTeam(team);
        List<Session> sessionList = new ArrayList<>();
        sessionIterable.forEach(sessionList::add);
        return sessionList;
    }

    public List<Session> getAllFromLastWeekByTeam(long epoch, Team team) {
        Iterable<Session> sessionIterable = sessionRepository.findByStartTimestampGreaterThanEqualAndTeam(epoch, team);
        List<Session> sessionList = new ArrayList<>();
        sessionIterable.forEach(sessionList::add);
        return sessionList;
    }
}
