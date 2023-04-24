package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.LogController;
import com.upc.gessi.loganalytics.app.domain.models.Log;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PageAccessesController implements Strategy {

    @Autowired
    LogController logController;

    private String page;

    @Override
    public double evaluate(Team team) {
        List<Log> logs = logController.getAllByPageAndTeam(page, team.getId());
        return logs.size();
    }

    @Override
    public double evaluate(Subject subject) {
        return -1.0;
    }

    @Override
    public double evaluate() {
        return -1.0;
    }

    @Override
    public void setParams(String entity) {
        this.page = entity;
    }
}
