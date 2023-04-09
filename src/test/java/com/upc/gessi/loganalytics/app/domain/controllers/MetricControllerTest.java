package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Metric;
import com.upc.gessi.loganalytics.app.domain.repositories.MetricRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class MetricControllerTest {

    @Mock
    private MetricRepository metricRepository;
    @InjectMocks
    private MetricController metricController;

    @Test
    void storeAllMetrics() throws NoSuchMethodException {
        Method getCurrentLDMetricsMethod = MetricController.class.getDeclaredMethod("getCurrentLDMetrics");
        getCurrentLDMetricsMethod.setAccessible(true);
        Metric m1 = new Metric("m1Test");
        Metric m2 = new Metric("m2Test");
        Set<Metric> metricSet = new HashSet<>();
        metricSet.add(m1); metricSet.add(m2);
        metricController.storeAllMetrics();
        Mockito.verify(metricRepository, Mockito.times(1)).findAll();
    }
}