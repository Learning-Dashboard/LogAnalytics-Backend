package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.FactorAccessController;
import com.upc.gessi.loganalytics.app.domain.controllers.IndicatorAccessController;
import com.upc.gessi.loganalytics.app.domain.controllers.MetricAccessController;
import com.upc.gessi.loganalytics.app.domain.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HistoricAccessesControllerTest {

    @Mock
    MetricAccessController metricAccessController;
    @Mock
    FactorAccessController factorAccessController;
    @Mock
    IndicatorAccessController indicatorAccessController;

    @InjectMocks
    HistoricAccessesController historicAccessesController;

    @Test
    void evaluate() {
        historicAccessesController.setParams("Historical");
        Subject subject = new Subject("subject");
        Team team = new Team("team", "sem", subject);

        List<MetricAccess> metricAccessList = new ArrayList<>();
        MetricAccess metricAccess1 = new MetricAccess();
        MetricAccess metricAccess2 = new MetricAccess();
        metricAccessList.add(metricAccess1);
        metricAccessList.add(metricAccess2);
        Mockito.when(metricAccessController.getAllByHistoricAndTeam(true, team)).thenReturn(metricAccessList);

        List<FactorAccess> factorAccessList = new ArrayList<>();
        FactorAccess factorAccess1 = new FactorAccess();
        factorAccessList.add(factorAccess1);
        Mockito.when(factorAccessController.getAllByHistoricAndTeam(true, team)).thenReturn(factorAccessList);

        List<IndicatorAccess> indicatorAccessList = new ArrayList<>();
        IndicatorAccess indicatorAccess1 = new IndicatorAccess();
        IndicatorAccess indicatorAccess2 = new IndicatorAccess();
        IndicatorAccess indicatorAccess3 = new IndicatorAccess();
        indicatorAccessList.add(indicatorAccess1);
        indicatorAccessList.add(indicatorAccess2);
        indicatorAccessList.add(indicatorAccess3);
        Mockito.when(indicatorAccessController.getAllByHistoricAndTeam(true, team)).thenReturn(indicatorAccessList);
        double result = historicAccessesController.evaluate(team);

        Mockito.verify(metricAccessController, Mockito.times(1)).getAllByHistoricAndTeam(true, team);
        Mockito.verify(factorAccessController, Mockito.times(1)).getAllByHistoricAndTeam(true, team);
        Mockito.verify(indicatorAccessController, Mockito.times(1)).getAllByHistoricAndTeam(true, team);
        assertEquals(6, result);
    }
}