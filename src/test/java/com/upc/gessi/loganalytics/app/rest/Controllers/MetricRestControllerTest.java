package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.MetricController;
import com.upc.gessi.loganalytics.app.domain.models.Metric;
import com.upc.gessi.loganalytics.app.domain.repositories.MetricRepository;
import com.upc.gessi.loganalytics.app.rest.Controllers.MetricRestController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetricRestControllerTest {

    @Mock
    MetricController metricController;
    @InjectMocks
    MetricRestController metricRestController;

    @Test
    void findAllMetrics() {
        List<Metric> metrics = new LinkedList<>();
        metrics.add(new Metric("m1"));
        metrics.add(new Metric("m2"));
        when(metricController.getAll()).thenReturn(metrics);
        List<Metric> actualMetrics = metricRestController.findAllMetrics();
        assertEquals(metrics, actualMetrics);
    }

    @Test
    void importMetrics() {
        metricRestController.importMetrics();
        verify(metricController, Mockito.times(1)).storeAllMetrics();
    }
}