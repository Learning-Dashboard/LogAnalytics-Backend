package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.FactorAccessController;
import com.upc.gessi.loganalytics.app.domain.models.FactorAccess;
import com.upc.gessi.loganalytics.app.domain.repositories.FactorAccessRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FactorAccessRestControllerTest {

    @Mock
    FactorAccessController factorAccessController;

    @InjectMocks
    FactorAccessRestController factorAccessRestController;

    @Test
    void findAllFactorAccesses() {
        List<FactorAccess> factorAccesses = new LinkedList<>();
        factorAccesses.add(new FactorAccess());
        factorAccesses.add(new FactorAccess());
        when(factorAccessController.getAll()).thenReturn(factorAccesses);
        List<FactorAccess> actualFactors = factorAccessRestController.findAllFactorAccesses();
        assertEquals(factorAccesses, actualFactors);
    }
}