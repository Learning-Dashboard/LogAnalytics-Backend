package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.repositories.InternalMetricRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InternalMetricControllerTest {

    @Mock
    InternalMetricRepository internalMetricRepository;

    @InjectMocks
    InternalMetricController internalMetricController;

    @Test
    void getAll() {
        List<InternalMetric> internalMetrics = new ArrayList<>();
        internalMetrics.add(new InternalMetric("test1"));
        internalMetrics.add(new InternalMetric("test2"));
        Mockito.when(internalMetricRepository.findAll()).thenReturn(internalMetrics);
        List<InternalMetric> actualInternalMetrics = internalMetricController.getAll();
        assertEquals(internalMetrics, actualInternalMetrics);
    }

    @Test
    void createPageMetric() {
        String page = "pageTest";
        String name = page + "PageAccesses";
        Mockito.when(internalMetricRepository.findById(name)).thenReturn(Optional.empty());
        internalMetricController.createPageMetric(page);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(page) &&
            internalMetric.getController().equals("PageAccesses")));
    }

    @Test
    void createFactorMetric() {
        String factor = "factorTest";
        String name = factor + "FactorAccesses";
        Mockito.when(internalMetricRepository.findById(name)).thenReturn(Optional.empty());
        internalMetricController.createFactorMetric(factor);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(factor) &&
            internalMetric.getController().equals("FactorAccesses")));
    }

    @Test
    void createIndicatorMetric() {
        String indicator = "indicatorTest";
        String name = indicator + "IndicatorAccesses";
        Mockito.when(internalMetricRepository.findById(name)).thenReturn(Optional.empty());
        internalMetricController.createIndicatorMetric(indicator);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(indicator) &&
            internalMetric.getController().equals("IndicatorAccesses")));
    }

    @Test
    void createMetricMetric() {
        String metric = "metricTest";
        String name = metric + "MetricAccesses";
        Mockito.when(internalMetricRepository.findById(name)).thenReturn(Optional.empty());
        internalMetricController.createMetricMetric(metric);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(metric) &&
            internalMetric.getController().equals("MetricAccesses")));
    }

    @Test
    void createIndicatorViewMetric() {
        String indicator = "indicatorTest";
        String name = indicator + "IViewAccesses";
        Mockito.when(internalMetricRepository.findById(name)).thenReturn(Optional.empty());
        internalMetricController.createIndicatorViewMetric(indicator);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(indicator) &&
            internalMetric.getController().equals("IViewAccesses")));
    }

    @Test
    void createFactorViewMetric() {
        String factor = "factorTest";
        String name = factor + "FViewAccesses";
        Mockito.when(internalMetricRepository.findById(name)).thenReturn(Optional.empty());
        internalMetricController.createFactorViewMetric(factor);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(factor) &&
            internalMetric.getController().equals("FViewAccesses")));
    }

    @Test
    void createMetricViewMetric() {
        String metric = "metricTest";
        String name = metric + "MViewAccesses";
        Mockito.when(internalMetricRepository.findById(name)).thenReturn(Optional.empty());
        internalMetricController.createMetricViewMetric(metric);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(metric) &&
            internalMetric.getController().equals("MViewAccesses")));
    }

    @Test
    void createQModelViewMetric() {
        String qModel = "qModelTest";
        String name = qModel + "QModViewAccesses";
        Mockito.when(internalMetricRepository.findById(name)).thenReturn(Optional.empty());
        internalMetricController.createQModelViewMetric(qModel);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(qModel) &&
            internalMetric.getController().equals("QModViewAccesses")));
    }
}