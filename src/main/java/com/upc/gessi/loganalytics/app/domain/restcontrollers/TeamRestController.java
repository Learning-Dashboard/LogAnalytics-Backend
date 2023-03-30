package com.upc.gessi.loganalytics.app.domain.restcontrollers;

import com.upc.gessi.loganalytics.app.domain.controllers.TeamController;
import com.upc.gessi.loganalytics.app.domain.models.Metric;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamRestController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamController teamController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Team> findAllTeams() {
        Iterable<Team> teamIterable = teamRepository.findAll();
        List<Team> teamList = new ArrayList<>();
        teamIterable.forEach(teamList::add);
        return teamList;
    }

    @GetMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void importTeams() {
        teamController.storeAllTeams();
    }
}
