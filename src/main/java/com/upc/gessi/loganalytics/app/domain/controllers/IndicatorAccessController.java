package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.IndicatorAccess;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.IndicatorAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndicatorAccessController {

    @Autowired
    IndicatorAccessRepository indicatorAccessRepository;

    public List<IndicatorAccess> getAll() {
        Iterable<IndicatorAccess> indicatorAccessIterable = indicatorAccessRepository.findAll();
        List<IndicatorAccess> indicatorAccessList = new ArrayList<>();
        indicatorAccessIterable.forEach(indicatorAccessList::add);
        return indicatorAccessList;
    }

    public List<IndicatorAccess> getAllByTeamAndIndicator(Team team, String indicator) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampYesterday = yesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

        Iterable<IndicatorAccess> indicatorAccessIterable =
            indicatorAccessRepository.findByTeamAndIndicatorsIdAndTimeBetween(team.getId(), indicator, timestampYesterday, timestampToday);
        List<IndicatorAccess> indicatorAccesses = new ArrayList<>();
        indicatorAccessIterable.forEach(indicatorAccesses::add);
        return indicatorAccesses;
    }

    public List<IndicatorAccess> getAllByTeamAndViewFormat(Team team, String viewFormat) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampYesterday = yesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

        Iterable<IndicatorAccess> indicatorAccessIterable =
                indicatorAccessRepository.findByTeamAndViewFormatAndTimeBetween(team.getId(), viewFormat, timestampYesterday, timestampToday);
        List<IndicatorAccess> indicatorAccesses = new ArrayList<>();
        indicatorAccessIterable.forEach(indicatorAccesses::add);
        return indicatorAccesses;
    }

    public List<IndicatorAccess> getAllByHistoricAndTeam(boolean historic, Team team) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampYesterday = yesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

        Iterable<IndicatorAccess> indicatorAccessIterable =
                indicatorAccessRepository.findByHistoricAndTeamAndTimeBetween(historic, team.getId(), timestampYesterday, timestampToday);
        List<IndicatorAccess> indicatorAccesses = new ArrayList<>();
        indicatorAccessIterable.forEach(indicatorAccesses::add);
        return indicatorAccesses;
    }
}
