package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.MetricAccessController;
import com.upc.gessi.loganalytics.app.domain.models.MetricAccess;
import com.upc.gessi.loganalytics.app.domain.repositories.MetricAccessRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetricAccessRestControllerTest {

    @Mock
    MetricAccessController metricAccessController;

    @InjectMocks
    MetricAccessRestController metricAccessRestController;

    @Test
    void findAllMetricAccesses() {
        List<MetricAccess> metricAccesses = new LinkedList<>();
        metricAccesses.add(new MetricAccess());
        metricAccesses.add(new MetricAccess());
        when(metricAccessController.getAll()).thenReturn(metricAccesses);
        List<MetricAccess> actualMetrics = metricAccessRestController.findAllMetricAccesses();
        assertEquals(metricAccesses, actualMetrics);
    }
}