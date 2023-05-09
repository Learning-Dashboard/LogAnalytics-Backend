package com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics;

import com.upc.gessi.loganalytics.app.domain.controllers.SessionController;
import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

@Controller
public class DaysLoginsController implements Strategy {

    @Autowired
    SessionController sessionController;

    private Integer days;

    @Override
    public double evaluate(Team team) {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        long timestampToday = today.toInstant(ZoneOffset.UTC).toEpochMilli() - 1;
        long timestampPrev = timestampToday - (days * 86400000L) + 1;

        System.out.println(timestampPrev);
        System.out.println(timestampToday);

        List<Session> sessions = sessionController.getAllByTimestampBetweenAndTeam(timestampPrev, timestampToday, team);
        return sessions.size();
    }

    @Override
    public double evaluate(Subject subject) { return -1.0; }

    @Override
    public double evaluate() { return -1.0; }

    @Override
    public void setParams(String entity) {
        this.days = Integer.parseInt(entity);
    }
}
