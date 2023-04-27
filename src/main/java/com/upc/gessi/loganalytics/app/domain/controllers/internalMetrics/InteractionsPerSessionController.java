package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.SessionController;
import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class InteractionsPerSessionController implements Strategy {

    @Autowired
    SessionController sessionController;

    @Override
    public double evaluate(Team team) {
        List<Session> sessions = sessionController.getAllByTeam(team);
        int nSessions = sessions.size();
        double nInteractions = 0.0;
        for (Session s : sessions)
            nInteractions += s.getnInteractions();
        if (nInteractions == 0.0 && nSessions == 0.0) return 0.0;
        return nInteractions / nSessions;
    }

    @Override
    public double evaluate(Subject subject) {
        List<Session> sessions = sessionController.getAllBySubject(subject);
        int nSessions = sessions.size();
        double nInteractions = 0.0;
        for (Session s : sessions)
            nInteractions += s.getnInteractions();
        if (nInteractions == 0.0 && nSessions == 0.0) return 0.0;
        return nInteractions / nSessions;
    }

    @Override
    public double evaluate() {
        List<Session> sessions = sessionController.getAll();
        int nSessions = sessions.size();
        double nInteractions = 0.0;
        for (Session s : sessions)
            nInteractions += s.getnInteractions();
        if (nInteractions == 0.0 && nSessions == 0.0) return 0.0;
        return nInteractions / nSessions;
    }

    @Override
    public void setParams(String entity) {

    }
}
