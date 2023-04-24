package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.FactorAccessController;
import com.upc.gessi.loganalytics.app.domain.models.Factor;
import com.upc.gessi.loganalytics.app.domain.models.FactorAccess;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
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
class FViewAccessesControllerTest {

    @Mock
    FactorAccessController factorAccessController;

    @InjectMocks
    FViewAccessesController fViewAccessesController;

    @Test
    void evaluate() {
        Subject subject = new Subject("subject");
        Team team = new Team("team", "sem", subject);
        Factor factor = new Factor("f1");
        fViewAccessesController.setParams("Chart");
        List<FactorAccess> factorAccessList = new ArrayList<>();
        FactorAccess factorAccess = new FactorAccess();
        factorAccess.setFactors(List.of(factor));
        factorAccessList.add(factorAccess);
        Mockito.when(factorAccessController.getAllByTeamAndViewFormat(team, "Chart")).thenReturn(factorAccessList);

        double result = fViewAccessesController.evaluate(team);
        assertEquals(1, result);
        Mockito.verify(factorAccessController, Mockito.times(1)).getAllByTeamAndViewFormat(team, "Chart");
    }
}