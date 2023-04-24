package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

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
class MetricAccessesControllerTest {

    @Mock
    MetricAccessController metricAccessController;

    @InjectMocks
    MetricAccessesController metricAccessesController;

    @Test
    void evaluate() {
        Subject subject = new Subject("subject");
        Team team = new Team("team", "sem", subject);
        Metric metric = new Metric("m1");
        metricAccessesController.setParams("m1");
        List<MetricAccess> metricAccessList = new ArrayList<>();
        MetricAccess metricAccess = new MetricAccess();
        metricAccess.setMetrics(List.of(metric));
        metricAccessList.add(metricAccess);
        Mockito.when(metricAccessController.getAllByTeamAndMetric(team, metric.getId())).thenReturn(metricAccessList);

        double result = metricAccessesController.evaluate(team);
        assertEquals(1, result);
        Mockito.verify(metricAccessController, Mockito.times(1)).getAllByTeamAndMetric(team, metric.getId());
    }
}