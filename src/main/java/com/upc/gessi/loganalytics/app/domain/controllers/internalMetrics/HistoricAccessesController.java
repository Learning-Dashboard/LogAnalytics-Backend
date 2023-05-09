package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.FactorAccessController;
import com.upc.gessi.loganalytics.app.domain.controllers.IndicatorAccessController;
import com.upc.gessi.loganalytics.app.domain.controllers.MetricAccessController;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HistoricAccessesController implements Strategy {

    @Autowired
    MetricAccessController metricAccessController;

    @Autowired
    FactorAccessController factorAccessController;

    @Autowired
    IndicatorAccessController indicatorAccessController;

    private boolean historic;

    @Override
    public double evaluate(Team team) {
        int metrics = metricAccessController.getAllByHistoricAndTeam(historic, team).size();
        int factors = factorAccessController.getAllByHistoricAndTeam(historic, team).size();
        int indicators = indicatorAccessController.getAllByHistoricAndTeam(historic, team).size();
        return metrics + factors + indicators;
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
        historic = entity.equals("Historical");
    }
}
