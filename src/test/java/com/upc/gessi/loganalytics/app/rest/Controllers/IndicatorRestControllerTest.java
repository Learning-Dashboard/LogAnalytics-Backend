package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.IndicatorController;
import com.upc.gessi.loganalytics.app.domain.models.Indicator;
import com.upc.gessi.loganalytics.app.domain.repositories.IndicatorRepository;
import com.upc.gessi.loganalytics.app.rest.Controllers.IndicatorRestController;
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
class IndicatorRestControllerTest {

    @Mock
    IndicatorController indicatorController;
    @InjectMocks
    IndicatorRestController indicatorRestController;

    @Test
    void findAllIndicators() {
        List<Indicator> indicators = new LinkedList<>();
        indicators.add(new Indicator("i1"));
        indicators.add(new Indicator("i2"));
        when(indicatorController.getAll()).thenReturn(indicators);
        List<Indicator> actualIndicators = indicatorRestController.findAllIndicators();
        assertEquals(indicators, actualIndicators);
    }

    @Test
    void importIndicators() {
        indicatorRestController.importIndicators();
        verify(indicatorController, Mockito.times(1)).storeAllIndicators();
    }
}