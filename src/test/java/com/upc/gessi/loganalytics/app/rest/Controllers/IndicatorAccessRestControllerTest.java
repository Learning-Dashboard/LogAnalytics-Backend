package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.IndicatorAccessController;
import com.upc.gessi.loganalytics.app.domain.models.IndicatorAccess;
import com.upc.gessi.loganalytics.app.domain.repositories.IndicatorAccessRepository;
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
class IndicatorAccessRestControllerTest {

    @Mock
    IndicatorAccessController indicatorAccessController;

    @InjectMocks
    IndicatorAccessRestController indicatorAccessRestController;


    @Test
    void findAllIndicatorAccesses() {
        List<IndicatorAccess> indicatorAccesses = new LinkedList<>();
        indicatorAccesses.add(new IndicatorAccess());
        indicatorAccesses.add(new IndicatorAccess());
        when(indicatorAccessController.getAll()).thenReturn(indicatorAccesses);
        List<IndicatorAccess> actualIndicators = indicatorAccessRestController.findAllIndicatorAccesses();
        assertEquals(indicatorAccesses, actualIndicators);
    }
}