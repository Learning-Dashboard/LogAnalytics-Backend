package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.QModelAccessController;
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
class QModViewAccessesControllerTest {

    @Mock
    QModelAccessController qModelAccessController;

    @InjectMocks
    QModViewAccessesController qModViewAccessesController;

    @Test
    void evaluate() {
        Subject subject = new Subject("subject");
        Team team = new Team("team", "sem", subject);
        qModViewAccessesController.setParams("Graph");
        List<QModelAccess> qModelAccessList = new ArrayList<>();
        QModelAccess qModelAccess = new QModelAccess();
        qModelAccessList.add(qModelAccess);
        Mockito.when(qModelAccessController.getAllByTeamAndViewFormat(team, "Graph")).thenReturn(qModelAccessList);

        double result = qModViewAccessesController.evaluate(team);
        assertEquals(1, result);
        Mockito.verify(qModelAccessController, Mockito.times(1)).getAllByTeamAndViewFormat(team, "Graph");
    }
}