package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.QModelAccess;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.QModelAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QModelAccessController {

    @Autowired
    QModelAccessRepository qModelAccessRepository;

    public List<QModelAccess> getAllByTeamAndViewFormat(Team team, String viewFormat) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime yesterday = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampYesterday = yesterday.toInstant(ZoneOffset.UTC).toEpochMilli();

        Iterable<QModelAccess> qModelAccessIterable =
            qModelAccessRepository.findByTeamAndViewFormatAndTimeBetween(team.getId(), viewFormat, timestampYesterday, timestampToday);
        List<QModelAccess> qModelAccesses = new ArrayList<>();
        qModelAccessIterable.forEach(qModelAccesses::add);
        return qModelAccesses;
    }

}
