package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.internalMetrics.Strategy;
import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.EvaluationRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectEvaluationRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamEvaluationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class EvaluationControllerTest {

    @Mock
    EvaluationRepository evaluationRepository;
    @Mock
    InternalMetricController internalMetricController;
    @Mock
    TeamController teamController;
    @Mock
    SubjectController subjectController;
    @Mock
    TeamEvaluationRepository teamEvaluationRepository;
    @Mock
    SubjectEvaluationRepository subjectEvaluationRepository;
    @Mock
    Strategy strategy;

    @InjectMocks
    EvaluationController evaluationController;

    @Test
    void evaluateMetrics() {
        InternalMetric internalMetric = new InternalMetric("testInternalMetric", "Test internal metric");
        List<InternalMetric> internalMetrics = List.of(internalMetric);
        Mockito.when(internalMetricController.getAll()).thenReturn(internalMetrics);

        Subject subj1 = new Subject("PES");
        Subject subj2 = new Subject("ASW");
        Team team1 = new Team("pes11a", "sem", subj1);
        Team team2 = new Team("pes11b", "sem", subj1);
        Team team3 = new Team("asw11a", "sem", subj2);
        Team team4 = new Team("asw11b", "sem", subj2);
        List<Team> teams = List.of(team1, team2, team3, team4);
        Mockito.when(teamController.getStoredTeams()).thenReturn(teams);
        List<Subject> subjects = List.of(subj1, subj2);
        Mockito.when(subjectController.getAll()).thenReturn(subjects);

        Mockito.when(strategy.evaluate(Mockito.any(Team.class))).thenReturn(1.0);
        Mockito.when(strategy.evaluate(Mockito.any(Subject.class))).thenReturn(-1.0);
        Mockito.when(strategy.evaluate()).thenReturn(-1.0);
        List<Evaluation> currentEvaluations = List.of(new Evaluation());
        Mockito.when(evaluationRepository.findByDate(Mockito.anyString())).thenReturn(currentEvaluations);
        evaluationController.evaluateMetrics();

        ArgumentCaptor<TeamEvaluation> teamEvaluationCaptor = ArgumentCaptor.forClass(TeamEvaluation.class);
        ArgumentCaptor<SubjectEvaluation> subjectEvaluationCaptor = ArgumentCaptor.forClass(SubjectEvaluation.class);
        ArgumentCaptor<Evaluation> evaluationCaptor = ArgumentCaptor.forClass(Evaluation.class);

        Mockito.verify(evaluationRepository, Mockito.times(1)).deleteAll(currentEvaluations);
        Mockito.verify(teamEvaluationRepository, Mockito.times(4)).save(teamEvaluationCaptor.capture());
        Mockito.verify(subjectEvaluationRepository, Mockito.times(2)).save(subjectEvaluationCaptor.capture());
        Mockito.verify(evaluationRepository, Mockito.times(1)).save(evaluationCaptor.capture());

        TeamEvaluation savedTeamEvaluation = teamEvaluationCaptor.getValue();
        assertEquals(1.0, savedTeamEvaluation.getValue());

        SubjectEvaluation savedSubjectEvaluation = subjectEvaluationCaptor.getValue();
        assertEquals(2.0, savedSubjectEvaluation.getValue());

        Evaluation savedEvaluation = evaluationCaptor.getValue();
        assertEquals(4.0, savedEvaluation.getValue());
    }
}