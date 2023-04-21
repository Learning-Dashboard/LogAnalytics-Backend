package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.IndicatorAccessController;
import com.upc.gessi.loganalytics.app.domain.models.IndicatorAccess;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class IViewAccessesController implements Strategy {

    private String viewFormat;

    @Autowired
    IndicatorAccessController indicatorAccessController;

    @Override
    public double evaluate(Team team) {
        List<IndicatorAccess> indicatorAccessesList =
                indicatorAccessController.getAllByTeamAndViewFormat(team, viewFormat);
        return indicatorAccessesList.size();
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
