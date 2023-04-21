package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.MetricAccess;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.MetricAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MetricAccessController {

    @Autowired
    MetricAccessRepository metricAccessRepository;

    public List<MetricAccess> getAllByTeamAndMetric(Team team, String metric) {
        Iterable<MetricAccess> metricAccessIterable =
            metricAccessRepository.findByTeamAndMetricsId(team.getId(), metric);
        List<MetricAccess> metricAccesses = new ArrayList<>();
        metricAccessIterable.forEach(metricAccesses::add);
        return metricAccesses;
    }

    public List<MetricAccess> getAllByHistoricAndTeam(boolean historic, Team team) {
        Iterable<MetricAccess> metricAccessIterable =
                metricAccessRepository.findByHistoricAndTeam(historic, team.getId());
        List<MetricAccess> metricAccesses = new ArrayList<>();
        metricAccessIterable.forEach(metricAccesses::add);
        return metricAccesses;
    }
}
