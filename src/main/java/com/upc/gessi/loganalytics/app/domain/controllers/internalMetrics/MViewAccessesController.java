package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.MetricAccessController;
import com.upc.gessi.loganalytics.app.domain.models.MetricAccess;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MViewAccessesController implements Strategy {

    private String viewFormat;

    @Autowired
    MetricAccessController metricAccessController;


    @Override
    public double evaluate(Team team) {
        List<MetricAccess> metricAccessList =
                metricAccessController.getAllByTeamAndViewFormat(team, viewFormat);
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
    public void setParams(Integer days) { }

    @Override
    public void setParams(String entity) {
        viewFormat = entity;
    }
}
