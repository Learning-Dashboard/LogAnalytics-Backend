package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.IndicatorAccessRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class IndicatorAccessControllerTest {

    @Mock
    IndicatorAccessRepository indicatorAccessRepository;

    @InjectMocks
    IndicatorAccessController indicatorAccessController;

    @Test
    void getAll() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        List<Indicator> indicators = new ArrayList<>();
        List<IndicatorAccess> expectedLogs = List.of(
                new IndicatorAccess(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, " +
                        "GET /StrategicIndicators/CurrentChart", "StrategicIndicators", s, true, "Chart", indicators),
                new IndicatorAccess(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, " +
                        "GET /StrategicIndicators/CurrentChart", "StrategicIndicators", s, true, "Chart", indicators)
        );
        Mockito.when(indicatorAccessRepository.findAll()).thenReturn(expectedLogs);
        List<IndicatorAccess> actualLogs = indicatorAccessController.getAll();
        assertEquals(expectedLogs, actualLogs);
    }

    @Test
    void getAllByTeamAndIndicator() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        Indicator i1 = new Indicator("i1");
        List<Indicator> indicators = new ArrayList<>();
        indicators.add(i1);
        List<IndicatorAccess> expectedLogs = List.of(
                new IndicatorAccess(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, " +
                        "GET /StrategicIndicators/CurrentChart", "StrategicIndicators", s, true, "Chart", indicators),
                new IndicatorAccess(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, " +
                        "GET /StrategicIndicators/CurrentChart", "StrategicIndicators", s, true, "Chart", indicators)
        );
        Mockito.when(indicatorAccessRepository.findByTeamAndIndicatorsIdAndTimeBetween(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(expectedLogs);
        List<IndicatorAccess> actualLogs = indicatorAccessController.getAllByTeamAndIndicator(t, "i1");
        assertEquals(expectedLogs, actualLogs);
    }

    @Test
    void getAllByTeamAndViewFormat() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        Indicator i1 = new Indicator("i1");
        List<Indicator> indicators = new ArrayList<>();
        indicators.add(i1);
        List<IndicatorAccess> expectedLogs = List.of(
                new IndicatorAccess(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, " +
                        "GET /StrategicIndicators/CurrentChart", "StrategicIndicators", s, true, "Chart", indicators),
                new IndicatorAccess(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, " +
                        "GET /StrategicIndicators/CurrentChart", "StrategicIndicators", s, true, "Chart", indicators)
        );
        Mockito.when(indicatorAccessRepository.findByTeamAndViewFormatAndTimeBetween(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(expectedLogs);
        List<IndicatorAccess> actualLogs = indicatorAccessController.getAllByTeamAndViewFormat(t, "Chart");
        assertEquals(expectedLogs, actualLogs);
    }

    @Test
    void getAllByHistoricAndTeam() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        Indicator i1 = new Indicator("i1");
        List<Indicator> indicators = new ArrayList<>();
        indicators.add(i1);
        List<IndicatorAccess> expectedLogs = List.of(
                new IndicatorAccess(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, " +
                        "GET /StrategicIndicators/CurrentChart", "StrategicIndicators", s, true, "Chart", indicators),
                new IndicatorAccess(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, " +
                        "GET /StrategicIndicators/CurrentChart", "StrategicIndicators", s, true, "Chart", indicators)
        );
        Mockito.when(indicatorAccessRepository.findByHistoricAndTeamAndTimeBetween(Mockito.anyBoolean(), Mockito.any(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(expectedLogs);
        List<IndicatorAccess> actualLogs = indicatorAccessController.getAllByHistoricAndTeam(true, t);
        assertEquals(expectedLogs, actualLogs);
    }
}