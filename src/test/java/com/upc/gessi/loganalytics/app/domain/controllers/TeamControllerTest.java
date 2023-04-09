package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Metric;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.models.pkey.TeamPrimaryKey;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @Mock
    TeamRepository teamRepository;
    @Mock
    SubjectController subjectController;

    @InjectMocks
    TeamController teamController;

    @BeforeEach
    void setUp() {
        teamController.setSemester("22-23-Q1");
    }

    @Test
    void storeAllTeams() throws NoSuchMethodException {
        Method getCurrentLDTeamsMethod = TeamController.class.getDeclaredMethod("getCurrentLDTeams");
        getCurrentLDTeamsMethod.setAccessible(true);
        teamController.storeAllTeams();
        Mockito.verify(teamRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getSemester() {
        String semester = teamController.getSemester();
        assertEquals(semester, "22-23-Q1");
    }

    @Test
    void getTeam() {
        Subject s = new Subject("s");
        Team team = new Team("teamId", "22-23-Q1", s);
        Mockito.when(teamRepository.findById(Mockito.any(TeamPrimaryKey.class)))
            .thenReturn(Optional.of(team));
        Team result = teamController.getTeam("teamId", "22-23-Q1");
        Mockito.verify(teamRepository).findById(new TeamPrimaryKey("teamId", "22-23-Q1"));
        assertEquals(team, result);
    }

    @Test
    void getStoredTeams() {
        List<Team> expectedTeams = new ArrayList<>();
        Subject s = new Subject("s");
        expectedTeams.add(new Team("t1", s));
        expectedTeams.add(new Team("t2", s));
        when(teamRepository.findAll()).thenReturn(expectedTeams);
        List<Team> actualTeams = teamController.getStoredTeams();
        assertEquals(expectedTeams, actualTeams);
        Mockito.verify(teamRepository, Mockito.times(1)).findAll();
    }
}