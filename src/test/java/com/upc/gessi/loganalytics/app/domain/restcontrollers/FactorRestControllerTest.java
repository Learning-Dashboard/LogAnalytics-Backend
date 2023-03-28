package com.upc.gessi.loganalytics.app.domain.restcontrollers;

import com.upc.gessi.loganalytics.app.domain.controllers.FactorController;
import com.upc.gessi.loganalytics.app.domain.models.Factor;
import com.upc.gessi.loganalytics.app.domain.repositories.FactorRepository;
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
class FactorRestControllerTest {

    @Mock
    FactorRepository factorRepository;
    @Mock
    FactorController factorController;
    @InjectMocks
    FactorRestController factorRestController;

    @Test
    void findAllFactors() {
        List<Factor> factors = new LinkedList<>();
        factors.add(new Factor("f1"));
        factors.add(new Factor("f2"));
        when(factorRepository.findAll()).thenReturn(factors);
        List<Factor> actualFactors = factorRestController.findAllFactors();
        assertEquals(factors, actualFactors);
    }

    @Test
    void importMetrics() {
        factorRestController.importMetrics();
        verify(factorController, Mockito.times(1)).storeAllFactors();
    }
}