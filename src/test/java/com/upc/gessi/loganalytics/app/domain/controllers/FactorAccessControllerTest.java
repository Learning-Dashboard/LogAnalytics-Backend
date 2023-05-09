package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.FactorAccessRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FactorAccessControllerTest {

    @Mock
    FactorAccessRepository factorAccessRepository;

    @InjectMocks
    FactorAccessController factorAccessController;

    @Test
    void getAllByTeamAndFactor() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        Factor f1 = new Factor("f1");
        List<Factor> factors = new ArrayList<>();
        factors.add(f1);
        List<FactorAccess> expectedLogs = List.of(
                new FactorAccess(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, " +
                        "GET /QualityFactors/CurrentChart", "StrategicIndicators", s, true, "Chart", factors),
                new FactorAccess(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, " +
                        "GET /QualityFactors/CurrentChart", "StrategicIndicators", s, true, "Chart", factors)
        );
        Mockito.when(factorAccessRepository.findByTeamAndFactorsIdAndTimeBetween
            (Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(expectedLogs);
        List<FactorAccess> actualLogs = factorAccessController.getAllByTeamAndFactor(t, "f1");
        assertEquals(expectedLogs, actualLogs);
    }

    @Test
    void getAllByTeamAndViewFormat() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        Factor f1 = new Factor("f1");
        List<Factor> factors = new ArrayList<>();
        factors.add(f1);
        List<FactorAccess> expectedLogs = List.of(
                new FactorAccess(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, " +
                        "GET /QualityFactors/CurrentChart", "StrategicIndicators", s, true, "Chart", factors),
                new FactorAccess(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, " +
                        "GET /QualityFactors/CurrentChart", "StrategicIndicators", s, true, "Chart", factors)
        );
        Mockito.when(factorAccessRepository.findByTeamAndViewFormatAndTimeBetween(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(expectedLogs);
        List<FactorAccess> actualLogs = factorAccessController.getAllByTeamAndViewFormat(t, "Chart");
        assertEquals(expectedLogs, actualLogs);
    }

    @Test
    void getAllByHistoricAndTeam() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        Factor f1 = new Factor("f1");
        List<Factor> factors = new ArrayList<>();
        factors.add(f1);
        List<FactorAccess> expectedLogs = List.of(
                new FactorAccess(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, " +
                        "GET /QualityFactors/CurrentChart", "StrategicIndicators", s, true, "Chart", factors),
                new FactorAccess(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, " +
                        "GET /QualityFactors/CurrentChart", "StrategicIndicators", s, true, "Chart", factors)
        );
        Mockito.when(factorAccessRepository.findByHistoricAndTeamAndTimeBetween(Mockito.anyBoolean(), Mockito.any(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(expectedLogs);
        List<FactorAccess> actualLogs = factorAccessController.getAllByHistoricAndTeam(true, t);
        assertEquals(expectedLogs, actualLogs);
    }
}