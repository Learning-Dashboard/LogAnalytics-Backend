package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.EvaluationController;
import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.repositories.EvaluationRepository;
import com.upc.gessi.loganalytics.app.rest.DTOs.EvaluationDTO;
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
class EvaluationRestControllerTest {

    @Mock
    EvaluationController evaluationController;
    @Mock
    EvaluationRepository evaluationRepository;

    @InjectMocks
    EvaluationRestController evaluationRestController;

    @Test
    void getCurrentEvaluations() {
        InternalMetric im = new InternalMetric("internalMetric", "Internal metric");
        Evaluation evaluation = new Evaluation("2001-07-22", im, 5.0);
        Mockito.when(evaluationRepository.findFirstByOrderByDateDesc()).thenReturn(evaluation);
        List<EvaluationDTO> evaluationList = evaluationRestController.getCurrentEvaluations();
        Mockito.verify(evaluationRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getHistoricalEvaluations() {
        String dateBefore = "2001-07-22";
        String dateAfter = "2001-10-20";
        List<EvaluationDTO> evaluationList = evaluationRestController.getHistoricalEvaluations(dateBefore, dateAfter);
        Mockito.verify(evaluationRepository, Mockito.times(1)).
            findByDateBetweenOrderByDateDesc(dateBefore, dateAfter);

        try {
            evaluationRestController.getHistoricalEvaluations(dateAfter, dateBefore);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"dateBefore is not previous to dateAfter\"");
        }
        try {
            String wrongDateBefore = "22-07-2001";
            evaluationRestController.getHistoricalEvaluations(wrongDateBefore, dateAfter);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"Date formats are incorrect\"");
        }
    }
}