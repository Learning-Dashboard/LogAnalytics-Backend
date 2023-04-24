package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.MetricAccessController;
import com.upc.gessi.loganalytics.app.domain.models.MetricAccess;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MetricAccessesController implements Strategy {

    @Autowired
    MetricAccessController metricAccessController;

    private String metric;

    @Override
    public double evaluate(Team team) {
        List<MetricAccess> metricAccessList =
            metricAccessController.getAllByTeamAndMetric(team, metric);
        return metricAccessList.size();
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
        metric = entity;
    }
}
