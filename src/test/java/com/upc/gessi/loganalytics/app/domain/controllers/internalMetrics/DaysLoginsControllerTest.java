package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.SessionController;
import com.upc.gessi.loganalytics.app.domain.models.Session;
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
class DaysLoginsControllerTest {

    @Mock
    SessionController sessionController;

    @InjectMocks
    DaysLoginsController daysLoginsController;

    @Test
    void evaluate() {
        daysLoginsController.setParams("7");
        Subject s = new Subject("s");
        Team team1 = new Team("t1", "22-23-Q1", s);
        Team team2 = new Team("t2", "22-23-Q1", s);
        List<Session> sessions = new ArrayList<>();
        Session session1 = new Session("s", team1, 0, 10, 10, 10);
        Session session2 = new Session("s", team1, 15, 25, 10, 5);
        sessions.add(session1); sessions.add(session2);
        Mockito.when(sessionController.getAllByTimestampBetweenAndTeam(Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
                .thenReturn(sessions);
        assertEquals(2, daysLoginsController.evaluate(team1));
        assertEquals(2, 2);
    }
}