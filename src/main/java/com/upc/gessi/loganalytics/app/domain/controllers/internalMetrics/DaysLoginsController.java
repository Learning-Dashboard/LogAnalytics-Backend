package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.SessionController;
import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DaysLoginsController implements Strategy {

    @Autowired
    SessionController sessionController;

    private Integer days;

    @Override
    public double evaluate(Team team) {
        long today = System.currentTimeMillis();
        long milliseconds = today - (days * 86400000L);
        List<Session> sessions = sessionController.getAllAfterTimestamp(milliseconds, team);
        return sessions.size();
    }

    @Override
    public double evaluate(Subject subject) { return -1.0; }

    @Override
    public double evaluate() { return -1.0; }

    @Override
    public void setParams(String entity) {
        this.days = Integer.parseInt(entity);
    }
}
