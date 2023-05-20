package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.InternalMetricRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.UserlessInternalMetricRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    @Mock
    UserlessInternalMetricRepository userlessInternalMetricRepository;
    @Mock
    CategoryController categoryController;

    @InjectMocks
    InternalMetricController internalMetricController;

    private Category c;

    @BeforeEach
    void setUp() {
        c = new Category("testCategory");
    }

    @AfterEach
    void tearDown() {
        c = null;
    }

    @Test
    void getAll() {
        List<InternalMetric> internalMetrics = new ArrayList<>();
        internalMetrics.add(new InternalMetric("test1", "test1"));
        internalMetrics.add(new InternalMetric("test2", "test2"));
        Mockito.when(internalMetricRepository.findAll()).thenReturn(internalMetrics);
        List<InternalMetric> actualInternalMetrics = internalMetricController.getAll();
        assertEquals(internalMetrics, actualInternalMetrics);
    }

    @Test
    void checkParamNameExistence() {
        Mockito.when(internalMetricRepository.existsByParamName("s1")).thenReturn(true);
        Mockito.when(internalMetricRepository.existsByParamName("s2")).thenReturn(false);
        assertTrue(internalMetricController.checkParamNameExistence("s1"));
        assertFalse(internalMetricController.checkParamNameExistence("s2"));
    }

    @Test
    void createPageMetric() {
        String page = "pageTest";
        String name = page + " page accesses";
        String id = page + "PageAccesses";
        Mockito.when(internalMetricRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(categoryController.getByName(Mockito.anyString())).thenReturn(c);
        internalMetricController.createPageMetric(page);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getId().equals(id) &&
            internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(page) &&
            internalMetric.getController().equals("PageAccesses") &&
            internalMetric.getControllerName().equals("Page accesses") &&
            internalMetric.getCategory().equals(c) &&
            internalMetric.isGroupable()));
    }

    @Test
    void createFactorMetric() {
        Factor factor = new Factor("factorTest", "factorName");
        String name = factor.getName() + " factor accesses";
        String id = factor.getId() + "FactorAccesses";
        Mockito.when(internalMetricRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(categoryController.getByName(Mockito.anyString())).thenReturn(c);
        internalMetricController.createFactorMetric(factor);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getId().equals(id) &&
            internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(factor.getId()) &&
            internalMetric.getParamName().equals(factor.getName()) &&
            internalMetric.getController().equals("FactorAccesses") &&
            internalMetric.getControllerName().equals("Factor accesses") &&
            internalMetric.getCategory().equals(c) &&
            internalMetric.isGroupable()));
    }

    @Test
    void createIndicatorMetric() {
        Indicator indicator = new Indicator("indicatorTest", "indicatorName");
        String name = indicator.getName() + " indicator accesses";
        String id = indicator.getId() + "IndicatorAccesses";
        Mockito.when(internalMetricRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(categoryController.getByName(Mockito.anyString())).thenReturn(c);
        internalMetricController.createIndicatorMetric(indicator);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getId().equals(id) &&
            internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(indicator.getId()) &&
            internalMetric.getParamName().equals(indicator.getName()) &&
            internalMetric.getController().equals("IndicatorAccesses") &&
            internalMetric.getControllerName().equals("Indicator accesses") &&
            internalMetric.getCategory().equals(c) &&
            internalMetric.isGroupable()));
    }

    @Test
    void createMetricMetric() {
        Metric metric = new Metric("metricTest", "metricName");
        String name = metric.getName() + " metric accesses";
        String id = metric.getId() + "MetricAccesses";
        Mockito.when(userlessInternalMetricRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(categoryController.getByName(Mockito.anyString())).thenReturn(c);
        internalMetricController.createMetricMetric(metric, null);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getId().equals(id) &&
            internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(metric.getId()) &&
            internalMetric.getParamName().equals(metric.getName()) &&
            internalMetric.getController().equals("MetricAccesses") &&
            internalMetric.getControllerName().equals("Metric accesses") &&
            internalMetric.getCategory().equals(c) &&
            internalMetric.isGroupable()));
    }

    @Test
    void createIndicatorViewMetric() {
        String indicator = "indicatorTest";
        String name = indicator + " indicator view accesses";
        String id = indicator + "IViewAccesses";
        Mockito.when(internalMetricRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(categoryController.getByName(Mockito.anyString())).thenReturn(c);
        internalMetricController.createIndicatorViewMetric(indicator);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getId().equals(id) &&
            internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(indicator) &&
            internalMetric.getController().equals("IViewAccesses") &&
            internalMetric.getControllerName().equals("Indicator view accesses") &&
            internalMetric.getCategory().equals(c) &&
            internalMetric.isGroupable()));
    }

    @Test
    void createFactorViewMetric() {
        String factor = "factorTest";
        String name = factor + " factor view accesses";
        String id = factor + "FViewAccesses";
        Mockito.when(internalMetricRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(categoryController.getByName(Mockito.anyString())).thenReturn(c);
        internalMetricController.createFactorViewMetric(factor);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getId().equals(id) &&
            internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(factor) &&
            internalMetric.getController().equals("FViewAccesses") &&
            internalMetric.getControllerName().equals("Factor view accesses") &&
            internalMetric.getCategory().equals(c) &&
            internalMetric.isGroupable()));
    }

    @Test
    void createMetricViewMetric() {
        String metric = "metricTest";
        String name = metric + " metric view accesses";
        String id = metric + "MViewAccesses";
        Mockito.when(internalMetricRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(categoryController.getByName(Mockito.anyString())).thenReturn(c);
        internalMetricController.createMetricViewMetric(metric);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getId().equals(id) &&
            internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(metric) &&
            internalMetric.getController().equals("MViewAccesses") &&
            internalMetric.getControllerName().equals("Metric view accesses") &&
            internalMetric.getCategory().equals(c) &&
            internalMetric.isGroupable()));
    }

    @Test
    void createQModelViewMetric() {
        String qModel = "qModelTest";
        String name = qModel + " quality model view accesses";
        String id = qModel + "QModViewAccesses";
        Mockito.when(internalMetricRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(categoryController.getByName(Mockito.anyString())).thenReturn(c);
        internalMetricController.createQModelViewMetric(qModel);
        Mockito.verify(internalMetricRepository, Mockito.times(1))
            .save(Mockito.argThat(internalMetric -> internalMetric.getId().equals(id) &&
            internalMetric.getName().equals(name) &&
            internalMetric.getParam().equals(qModel) &&
            internalMetric.getController().equals("QModViewAccesses") &&
            internalMetric.getControllerName().equals("Quality Model view accesses") &&
            internalMetric.getCategory().equals(c) &&
            internalMetric.isGroupable()));
    }
}