package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SessionController {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    TeamController teamController;

    @Autowired
    LogController logController;

    public void createSession(long startTimestamp, String teamId) {
        String semester = teamController.getSemester();
        Team team = teamController.getTeam(teamId, semester);
        if (team != null) {
            Session session = new Session(team, startTimestamp);
            sessionRepository.save(session);
        }
    }

    public void updateSession(long endTimestamp, String teamId) {
        String semester = teamController.getSemester();
        Team team = teamController.getTeam(teamId, semester);
        Iterable<Session> sessions = sessionRepository.findByTeamIdAndTeamSemester(teamId, semester);
        List<Session> sessionList = new ArrayList<>();
        sessions.forEach(sessionList::add);
        for (Session s : sessionList) {
            if (s.getEndTimestamp() == 0) {
                s.setEndTimestamp(endTimestamp);
                long duration = (endTimestamp - s.getStartTimestamp()) / 1000;
                s.setDuration(duration);
                sessionRepository.save(s);
            }
        }
    }

    public void setnInteractions(Session s) {
        int nInteractions = s.getLogs().size() - 2;
        s.setnInteractions(nInteractions);
    }
}
