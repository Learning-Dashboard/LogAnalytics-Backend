package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.FactorAccessController;
import com.upc.gessi.loganalytics.app.domain.models.FactorAccess;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class FViewAccessesController implements Strategy {

    @Autowired
    FactorAccessController factorAccessController;

    private String viewFormat;

    @Override
    public double evaluate(Team team) {
        List<FactorAccess> factorAccessList =
                factorAccessController.getAllByTeamAndViewFormat(team, viewFormat);
        return factorAccessList.size();
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
