package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.InternalMetricController;
import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class InternalMetricRestControllerTest {

    @Mock
    private InternalMetricController internalMetricController;

    @InjectMocks
    private InternalMetricRestController internalMetricRestController;

    @Test
    void findAllInternalMetrics() {
        List<InternalMetric> internalMetricList = internalMetricRestController.findAllInternalMetrics();
        Mockito.verify(internalMetricController, Mockito.times(1)).getAll();
    }
}