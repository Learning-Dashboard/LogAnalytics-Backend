package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;

public interface Strategy {
    double evaluate(Team team);

    double evaluate(Subject subject);

    double evaluate();

    void setParams(String entity);
}
