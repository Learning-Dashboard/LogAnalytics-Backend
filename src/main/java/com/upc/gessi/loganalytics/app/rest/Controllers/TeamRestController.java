package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.TeamController;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamRestController {

    @Autowired
    TeamController teamController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Team> findAllTeams() {
        return teamController.getAll();
    }

    @GetMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void importTeams() {
        teamController.storeAllTeams();
    }
}
