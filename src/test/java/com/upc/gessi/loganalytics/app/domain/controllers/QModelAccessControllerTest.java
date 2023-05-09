package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.QModelAccessRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QModelAccessControllerTest {

    @Mock
    QModelAccessRepository qModelAccessRepository;

    @InjectMocks
    QModelAccessController qModelAccessController;

    @Test
    void getAllByTeamAndViewFormat() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        List<QModelAccess> expectedLogs = List.of(
                new QModelAccess(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, GET /QualityModelGraph", "QualityModelGraph", s, "Graph"),
                new QModelAccess(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, GET /QualityModelGraph", "QualityModelGraph", s, "Graph")
        );
        Mockito.when(qModelAccessRepository.findByTeamAndViewFormatAndTimeBetween(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(expectedLogs);
        List<QModelAccess> actualLogs = qModelAccessController.getAllByTeamAndViewFormat(t, "Graph");
        assertEquals(expectedLogs, actualLogs);
    }
}