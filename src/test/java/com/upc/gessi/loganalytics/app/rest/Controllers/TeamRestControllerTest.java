package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.TeamController;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamRestControllerTest {

    @Mock
    TeamController teamController;

    @InjectMocks
    TeamRestController teamRestController;

    @Test
    void findAllTeams() {
        List<Team> teams = new ArrayList<>();
        Subject subject = new Subject("PES");
        teams.add(new Team("t1", "sem", subject));
        teams.add(new Team("t2", "sem", subject));
        when(teamController.getAll()).thenReturn(teams);
        List<Team> actualTeams = teamRestController.findAllTeams();
        assertEquals(teams, actualTeams);
    }

    @Test
    void importTeams() {
        teamRestController.importTeams();
        verify(teamController, Mockito.times(1)).storeAllTeams();
    }
}