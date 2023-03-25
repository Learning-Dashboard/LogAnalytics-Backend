package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Factor;
import com.upc.gessi.loganalytics.app.domain.repositories.FactorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class FactorControllerTest {

    @Mock
    private FactorRepository factorRepository;
    @InjectMocks
    private FactorController factorController;

    @Test
    void storeAllFactors() throws NoSuchMethodException {
        Method getCurrentLDFactorsMethod = FactorController.class.getDeclaredMethod("getCurrentLDFactors");
        getCurrentLDFactorsMethod.setAccessible(true);
        Factor f1 = new Factor("f1Test");
        Factor f2 = new Factor("f2Test");
        Set<Factor> factorSet = new HashSet<>();
        factorSet.add(f1); factorSet.add(f2);
        factorController.storeAllFactors();
        Mockito.verify(factorRepository, Mockito.times(1)).saveAll(Mockito.any());
    }
}