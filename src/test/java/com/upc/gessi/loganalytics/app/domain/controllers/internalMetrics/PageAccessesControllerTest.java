package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.LogController;
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
class PageAccessesControllerTest {

    @Mock
    LogController logController;

    @InjectMocks
    PageAccessesController pageAccessesController;

    @Test
    void evaluate() {
        Subject subject = new Subject("subject");
        Team team = new Team("team", "sem", subject);
        Log log = new Log();
        log.setPage("page");
        pageAccessesController.setParams("page");
        List<Log> logList = new ArrayList<>();
        logList.add(log);
        Mockito.when(logController.getAllByPageAndTeam("page", team.getId())).thenReturn(logList);

        double result = pageAccessesController.evaluate(team);
        assertEquals(1, result);
        Mockito.verify(logController, Mockito.times(1)).getAllByPageAndTeam("page", team.getId());
    }
}