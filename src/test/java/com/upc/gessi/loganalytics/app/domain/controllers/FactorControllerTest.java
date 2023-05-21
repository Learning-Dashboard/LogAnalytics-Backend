package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Factor;
import com.upc.gessi.loganalytics.app.domain.models.Metric;
import com.upc.gessi.loganalytics.app.domain.repositories.FactorRepository;
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
class FactorControllerTest {

    @Mock
    FactorRepository factorRepository;

    @InjectMocks
    FactorController factorController;

    @Test
    void getAll() {
        List<Factor> factorList = new ArrayList<>();
        factorList.add(new Factor("m1", "m1"));
        factorList.add(new Factor("m2", "m2"));
        Mockito.when(factorRepository.findAll()).thenReturn(factorList);
        List<Factor> actualFactors = factorController.getAll();
        assertEquals(factorList, actualFactors);
    }

    @Test
    void checkExistence() {
        Factor m1 = new Factor("m1", "m1");
        Factor m2 = new Factor("m2", "m2");
        Mockito.when(factorRepository.findById("m1")).thenReturn(Optional.of(m1));
        Mockito.when(factorRepository.findById("m2")).thenReturn(Optional.empty());
        assertTrue(factorController.checkExistence(m1));
        assertFalse(factorController.checkExistence(m2));
    }

    @Test
    void getFactor() {
        Factor m1 = new Factor("m1", "m1");
        Mockito.when(factorRepository.findById("m1")).thenReturn(Optional.of(m1));
        assertEquals(m1, factorController.getFactor("m1"));
    }
}