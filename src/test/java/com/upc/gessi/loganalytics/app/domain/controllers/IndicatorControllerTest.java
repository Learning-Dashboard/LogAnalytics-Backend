package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Indicator;
import com.upc.gessi.loganalytics.app.domain.repositories.IndicatorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class IndicatorControllerTest {

    @Mock
    private IndicatorRepository indicatorRepository;
    @InjectMocks
    private IndicatorController indicatorController;

    @Test
    void storeAllIndicators() throws NoSuchMethodException {
        Method getCurrentLDFactorsMethod = IndicatorController.class.getDeclaredMethod("getCurrentLDIndicators");
        getCurrentLDFactorsMethod.setAccessible(true);
        Indicator i1 = new Indicator("i1Test");
        Indicator i2 = new Indicator("i2Test");
        Set<Indicator> indicatorSet = new HashSet<>();
        indicatorSet.add(i1); indicatorSet.add(i2);
        indicatorController.storeAllIndicators();
        Mockito.verify(indicatorRepository, Mockito.times(1)).saveAll(Mockito.any());
    }
}