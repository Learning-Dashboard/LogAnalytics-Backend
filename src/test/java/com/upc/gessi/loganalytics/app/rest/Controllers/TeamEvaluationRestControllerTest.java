package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.TeamEvaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamEvaluationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TeamEvaluationRestControllerTest {

    @Mock
    TeamEvaluationRepository teamEvaluationRepository;

    @InjectMocks
    TeamEvaluationRestController teamEvaluationRestController;

    @Test
    void getCurrentEvaluations() {
        InternalMetric im = new InternalMetric("internalMetric", "Internal metric");
        TeamEvaluation e1 = new TeamEvaluation("2001-07-22", im, "PES", 5.0);
        Mockito.when(teamEvaluationRepository.findFirstByTeamOrderByDateDesc("PES")).thenReturn(e1);
        List<TeamEvaluation> evaluationList = teamEvaluationRestController.getCurrentEvaluations("PES");
        Mockito.verify(teamEvaluationRepository, Mockito.times(1)).
                findByDateAndTeam("2001-07-22", "PES");
    }

    @Test
    void getHistoricalEvaluations() {
        String dateBefore = "2001-07-22";
        String dateAfter = "2001-10-20";
        List<TeamEvaluation> evaluationList = teamEvaluationRestController.getHistoricalEvaluations("PES", dateBefore, dateAfter);
        Mockito.verify(teamEvaluationRepository, Mockito.times(1)).
                findByDateBetweenAndTeamOrderByInternalMetricAsc(dateBefore, dateAfter, "PES");

        try {
            teamEvaluationRestController.getHistoricalEvaluations("PES", dateAfter, dateBefore);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"dateBefore is not previous to dateAfter\"");
        }
        try {
            String wrongDateBefore = "22-07-2001";
            teamEvaluationRestController.getHistoricalEvaluations("PES", wrongDateBefore, dateAfter);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"Date formats are incorrect\"");
        }
    }
}