package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Factor;
import com.upc.gessi.loganalytics.app.domain.models.Indicator;
import com.upc.gessi.loganalytics.app.domain.repositories.IndicatorRepository;
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
class IndicatorControllerTest {

    @Mock
    IndicatorRepository indicatorRepository;

    @InjectMocks
    IndicatorController indicatorController;

    @Test
    void getAll() {
        List<Indicator> indicatorList = new ArrayList<>();
        indicatorList.add(new Indicator("m1", "m1"));
        indicatorList.add(new Indicator("m2", "m2"));
        Mockito.when(indicatorRepository.findAll()).thenReturn(indicatorList);
        List<Indicator> actualIndicators = indicatorController.getAll();
        assertEquals(indicatorList, actualIndicators);
    }

    @Test
    void checkExistence() {
        Indicator m1 = new Indicator("m1", "m1");
        Indicator m2 = new Indicator("m2", "m2");
        Mockito.when(indicatorRepository.findById("m1")).thenReturn(Optional.of(m1));
        Mockito.when(indicatorRepository.findById("m2")).thenReturn(Optional.empty());
        assertTrue(indicatorController.checkExistence(m1));
        assertFalse(indicatorController.checkExistence(m2));
    }

    @Test
    void getIndicator() {
        Indicator m1 = new Indicator("m1", "m1");
        Mockito.when(indicatorRepository.findById("m1")).thenReturn(Optional.of(m1));
        assertEquals(m1, indicatorController.getIndicator("m1"));
    }
}