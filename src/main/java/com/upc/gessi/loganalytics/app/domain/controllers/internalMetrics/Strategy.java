package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;

public interface Strategy {
    public double evaluate(Team team);

    public double evaluate(Subject subject);

    public double evaluate();

    public void setParams(String entity);
}
