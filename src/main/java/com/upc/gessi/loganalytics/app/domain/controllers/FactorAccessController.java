package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.FactorAccess;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.FactorAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FactorAccessController {

    @Autowired
    FactorAccessRepository factorAccessRepository;

    public List<FactorAccess> getAllByTeamAndFactor(Team team, String factor) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampYesterday = yesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

        Iterable<FactorAccess> factorAccessIterable =
            factorAccessRepository.findByTeamAndFactorsIdAndTimeBetween(team.getId(), factor, timestampYesterday, timestampToday);
        List<FactorAccess> factorAccesses = new ArrayList<>();
        factorAccessIterable.forEach(factorAccesses::add);
        return factorAccesses;
    }
    
    public List<FactorAccess> getAllByTeamAndViewFormat(Team team, String viewFormat) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampYesterday = yesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

        Iterable<FactorAccess> factorAccessIterable =
            factorAccessRepository.findByTeamAndViewFormatAndTimeBetween(team.getId(), viewFormat, timestampYesterday, timestampToday);
        List<FactorAccess> factorAccesses = new ArrayList<>();
        factorAccessIterable.forEach(factorAccesses::add);
        return factorAccesses;
    }

    public List<FactorAccess> getAllByHistoricAndTeam(boolean historic, Team team) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampYesterday = yesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

        Iterable<FactorAccess> factorAccessIterable =
                factorAccessRepository.findByHistoricAndTeamAndTimeBetween(historic, team.getId(), timestampYesterday, timestampToday);
        List<FactorAccess> factorAccesses = new ArrayList<>();
        factorAccessIterable.forEach(factorAccesses::add);
        return factorAccesses;
    }
}
