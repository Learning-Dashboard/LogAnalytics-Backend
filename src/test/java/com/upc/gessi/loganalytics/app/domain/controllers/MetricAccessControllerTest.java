package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.MetricAccessRepository;
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
class MetricAccessControllerTest {

    @Mock
    MetricAccessRepository metricAccessRepository;

    @InjectMocks
    MetricAccessController metricAccessController;

    @Test
    void getAllByTeamAndMetric() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        Metric m1 = new Metric("m1");
        List<Metric> metrics = new ArrayList<>();
        metrics.add(m1);
        List<MetricAccess> expectedLogs = List.of(
                new MetricAccess(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, " +
                    "GET /Metrics/CurrentGaugeChart", "Metrics", s, true, "GaugeChart", metrics),
                new MetricAccess(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, " +
                    "GET /Metrics/CurrentGaugeChart", "Metrics", s, true, "GaugeChart", metrics)
        );
        Mockito.when(metricAccessRepository.findByTeamAndMetricsId(Mockito.any(), Mockito.any())).thenReturn(expectedLogs);
        List<MetricAccess> actualLogs = metricAccessController.getAllByTeamAndMetric(t, "m1");
        assertEquals(expectedLogs, actualLogs);
    }

    @Test
    void getAllByTeamAndViewFormat() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        Metric m1 = new Metric("m1");
        List<Metric> metrics = new ArrayList<>();
        metrics.add(m1);
        List<MetricAccess> expectedLogs = List.of(
                new MetricAccess(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, " +
                        "GET /Metrics/CurrentGaugeChart", "Metrics", s, true,"GaugeChart", metrics),
                new MetricAccess(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, " +
                        "GET /Metrics/CurrentGaugeChart", "Metrics", s, true,"GaugeChart", metrics)
        );
        Mockito.when(metricAccessRepository.findByTeamAndViewFormat(Mockito.any(), Mockito.any())).thenReturn(expectedLogs);
        List<MetricAccess> actualLogs = metricAccessController.getAllByTeamAndViewFormat(t, "GaugeChart");
        assertEquals(expectedLogs, actualLogs);
    }

    @Test
    void getAllByHistoricAndTeam() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        Metric m1 = new Metric("m1");
        List<Metric> metrics = new ArrayList<>();
        metrics.add(m1);
        List<MetricAccess> expectedLogs = List.of(
                new MetricAccess(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, " +
                        "GET /Metrics/CurrentGaugeChart", "Metrics", s, true,"GaugeChart", metrics),
                new MetricAccess(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, " +
                        "GET /Metrics/CurrentGaugeChart", "Metrics", s, true,"GaugeChart", metrics)
        );
        Mockito.when(metricAccessRepository.findByHistoricAndTeam(Mockito.anyBoolean(), Mockito.any())).thenReturn(expectedLogs);
        List<MetricAccess> actualLogs = metricAccessController.getAllByHistoricAndTeam(true, t);
        assertEquals(expectedLogs, actualLogs);
    }
}