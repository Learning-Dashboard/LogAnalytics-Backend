package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.SubjectEvaluationController;
import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.SubjectEvaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectEvaluationRepository;
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
class SubjectEvaluationRestControllerTest {

    @Mock
    SubjectEvaluationController subjectEvaluationController;

    @InjectMocks
    SubjectEvaluationRestController subjectEvaluationRestController;

    @Test
    void getCurrentEvaluations() {
        InternalMetric im = new InternalMetric("internalMetric", "Internal metric");
        SubjectEvaluation e1 = new SubjectEvaluation("2001-07-22", im, "PES", 5.0);
        List<EvaluationDTO> evaluationDTOS = new ArrayList<>();
        evaluationDTOS.add(new EvaluationDTO(e1));
        Mockito.when(subjectEvaluationController.getCurrentEvaluations("PES")).thenReturn(evaluationDTOS);
        List<EvaluationDTO> evaluationList = subjectEvaluationRestController.getCurrentEvaluations("PES");
        Mockito.verify(subjectEvaluationController, Mockito.times(1)).
            getCurrentEvaluations("PES");
    }

    @Test
    void getHistoricalEvaluations() {
        String dateBefore = "2001-07-22";
        String dateAfter = "2001-10-20";
        List<EvaluationDTO> evaluationList = subjectEvaluationRestController.getHistoricalEvaluations("PES", dateBefore, dateAfter);
        Mockito.verify(subjectEvaluationController, Mockito.times(1)).
                getHistoricalEvaluations("PES", dateBefore, dateAfter);

        try {
            subjectEvaluationRestController.getHistoricalEvaluations("PES", dateAfter, dateBefore);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"dateBefore is not previous to dateAfter\"");
        }
        try {
            String wrongDateBefore = "22-07-2001";
            subjectEvaluationRestController.getHistoricalEvaluations("PES", wrongDateBefore, dateAfter);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"Date formats are incorrect\"");
        }
    }

    @Test
    void getHistoricalEvaluationsByParam() {
        String dateBefore = "2001-07-22";
        String dateAfter = "2001-10-20";
        EvaluationDTO evaluation = subjectEvaluationRestController.getHistoricalEvaluationsByParam
            ("PES", dateBefore, dateAfter, "testMetric", "testParam");
        Mockito.verify(subjectEvaluationController, Mockito.times(1)).
            getHistoricalEvaluationsByParam("PES", dateBefore, dateAfter, "testMetric", "testParam");

        try {
            subjectEvaluationRestController.getHistoricalEvaluationsByParam
                ("PES", dateAfter, dateBefore, "testMetric", "testParam");
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"dateBefore is not previous to dateAfter\"");
        }
        try {
            String wrongDateBefore = "22-07-2001";
            subjectEvaluationRestController.getHistoricalEvaluationsByParam
                ("PES", wrongDateBefore, dateAfter, "testMetric", "testParam");
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"Date formats are incorrect\"");
        }
    }
}