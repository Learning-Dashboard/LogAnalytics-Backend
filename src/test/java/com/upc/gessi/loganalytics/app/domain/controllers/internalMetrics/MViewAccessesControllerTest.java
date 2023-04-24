package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.MetricAccessController;
import com.upc.gessi.loganalytics.app.domain.models.Metric;
import com.upc.gessi.loganalytics.app.domain.models.MetricAccess;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
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
class MViewAccessesControllerTest {

    @Mock
    MetricAccessController metricAccessController;

    @InjectMocks
    MViewAccessesController mViewAccessesController;

    @Test
    void evaluate() {
        Subject subject = new Subject("subject");
        Team team = new Team("team", "sem", subject);
        Metric metric = new Metric("m1");
        mViewAccessesController.setParams("GaugeChart");
        List<MetricAccess> metricAccessList = new ArrayList<>();
        MetricAccess metricAccess = new MetricAccess();
        metricAccess.setMetrics(List.of(metric));
        metricAccessList.add(metricAccess);
        Mockito.when(metricAccessController.getAllByTeamAndViewFormat(team, "GaugeChart")).thenReturn(metricAccessList);

        double result = mViewAccessesController.evaluate(team);
        assertEquals(1, result);
        Mockito.verify(metricAccessController, Mockito.times(1)).getAllByTeamAndViewFormat(team, "GaugeChart");
    }
}