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
class FactorAccessesControllerTest {

    @Mock
    FactorAccessController factorAccessController;

    @InjectMocks
    FactorAccessesController factorAccessesController;

    @Test
    void evaluate() {
        Subject subject = new Subject("subject");
        Team team = new Team("team", "sem", subject);
        Factor factor = new Factor("f1");
        factorAccessesController.setParams("f1");
        List<FactorAccess> factorAccessList = new ArrayList<>();
        FactorAccess factorAccess = new FactorAccess();
        factorAccess.setFactors(List.of(factor));
        factorAccessList.add(factorAccess);
        Mockito.when(factorAccessController.getAllByTeamAndFactor(team, factor.getId())).thenReturn(factorAccessList);

        double result = factorAccessesController.evaluate(team);
        assertEquals(1, result);
        Mockito.verify(factorAccessController, Mockito.times(1)).getAllByTeamAndFactor(team, factor.getId());
    }
}