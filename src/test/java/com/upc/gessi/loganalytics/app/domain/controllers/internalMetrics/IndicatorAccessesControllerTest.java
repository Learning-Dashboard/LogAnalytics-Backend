package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.IndicatorAccessController;
import com.upc.gessi.loganalytics.app.domain.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IndicatorAccessesControllerTest {

    @Mock
    IndicatorAccessController indicatorAccessController;

    @InjectMocks
    IndicatorAccessesController indicatorAccessesController;

    @Test
    void evaluate() {
        Subject subject = new Subject("subject");
        Team team = new Team("team", "sem", subject);
        Indicator indicator = new Indicator("i1");
        indicatorAccessesController.setParams("i1");
        List<IndicatorAccess> indicatorAccessList = new ArrayList<>();
        IndicatorAccess indicatorAccess = new IndicatorAccess();
        indicatorAccess.setIndicators(List.of(indicator));
        indicatorAccessList.add(indicatorAccess);
        Mockito.when(indicatorAccessController.getAllByTeamAndIndicator(team, indicator.getId())).thenReturn(indicatorAccessList);

        double result = indicatorAccessesController.evaluate(team);
        assertEquals(1, result);
        Mockito.verify(indicatorAccessController, Mockito.times(1)).getAllByTeamAndIndicator(team, indicator.getId());
    }
}