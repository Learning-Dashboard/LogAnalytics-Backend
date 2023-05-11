package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.TeamEvaluationController;
import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.TeamEvaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamEvaluationRepository;
import com.upc.gessi.loganalytics.app.rest.DTOs.EvaluationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TeamEvaluationRestControllerTest {

    @Mock
    TeamEvaluationController teamEvaluationController;

    @InjectMocks
    TeamEvaluationRestController teamEvaluationRestController;

    @Test
    void getCurrentEvaluations() {
        InternalMetric im = new InternalMetric("internalMetric", "Internal metric");
        TeamEvaluation e1 = new TeamEvaluation("2001-07-22", im, "PES", 5.0);
        List<EvaluationDTO> evaluationDTOS = new ArrayList<>();
        evaluationDTOS.add(new EvaluationDTO(e1));
        Mockito.when(teamEvaluationController.getCurrentEvaluations("PES")).thenReturn(evaluationDTOS);
        List<EvaluationDTO> evaluationList = teamEvaluationRestController.getCurrentEvaluations("PES");
        Mockito.verify(teamEvaluationController, Mockito.times(1)).
                getCurrentEvaluations("PES");
    }

    @Test
    void getHistoricalEvaluations() {
        String dateBefore = "2001-07-22";
        String dateAfter = "2001-10-20";
        List<EvaluationDTO> evaluationList = teamEvaluationRestController.getHistoricalEvaluations("PES", dateBefore, dateAfter);
        Mockito.verify(teamEvaluationController, Mockito.times(1)).
                getHistoricalEvaluations("PES", dateBefore, dateAfter);

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