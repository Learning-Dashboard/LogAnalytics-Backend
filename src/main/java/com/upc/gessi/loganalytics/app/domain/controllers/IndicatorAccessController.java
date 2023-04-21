package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.IndicatorAccess;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.IndicatorAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndicatorAccessController {

    @Autowired
    IndicatorAccessRepository indicatorAccessRepository;

    public List<IndicatorAccess> getAllByTeamAndIndicator(Team team, String indicator) {
        Iterable<IndicatorAccess> indicatorAccessIterable =
            indicatorAccessRepository.findByTeamAndIndicatorsId(team.getId(), indicator);
        List<IndicatorAccess> indicatorAccesses = new ArrayList<>();
        indicatorAccessIterable.forEach(indicatorAccesses::add);
        return indicatorAccesses;
    }

    public List<IndicatorAccess> getAllByHistoricAndTeam(boolean historic, Team team) {
        Iterable<IndicatorAccess> indicatorAccessIterable =
                indicatorAccessRepository.findByHistoricAndTeam(historic, team.getId());
        List<IndicatorAccess> indicatorAccesses = new ArrayList<>();
        indicatorAccessIterable.forEach(indicatorAccesses::add);
        return indicatorAccesses;
    }
}
