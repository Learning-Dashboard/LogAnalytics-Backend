package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.MetricAccess;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.MetricAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MetricAccessController {

    @Autowired
    MetricAccessRepository metricAccessRepository;

    public List<MetricAccess> getAllByTeamAndMetric(Team team, String metric) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampYesterday = yesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

        Iterable<MetricAccess> metricAccessIterable =
            metricAccessRepository.findByTeamAndMetricsIdAndTimeBetween(team.getId(), metric, timestampYesterday, timestampToday);
        List<MetricAccess> metricAccesses = new ArrayList<>();
        metricAccessIterable.forEach(metricAccesses::add);
        return metricAccesses;
    }

    public List<MetricAccess> getAllByTeamAndViewFormat(Team team, String viewFormat) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampYesterday = yesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

        Iterable<MetricAccess> metricAccessIterable =
            metricAccessRepository.findByTeamAndViewFormatAndTimeBetween(team.getId(), viewFormat, timestampYesterday, timestampToday);
        List<MetricAccess> metricAccesses = new ArrayList<>();
        metricAccessIterable.forEach(metricAccesses::add);
        return metricAccesses;
    }

    public List<MetricAccess> getAllByHistoricAndTeam(boolean historic, Team team) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampYesterday = yesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

        Iterable<MetricAccess> metricAccessIterable =
                metricAccessRepository.findByHistoricAndTeamAndTimeBetween(historic, team.getId(), timestampYesterday, timestampToday);
        List<MetricAccess> metricAccesses = new ArrayList<>();
        metricAccessIterable.forEach(metricAccesses::add);
        return metricAccesses;
    }
}
