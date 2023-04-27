package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.SubjectEvaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectEvaluationRepository;
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
class SubjectEvaluationRestControllerTest {

    @Mock
    SubjectEvaluationRepository subjectEvaluationRepository;

    @InjectMocks
    SubjectEvaluationRestController subjectEvaluationRestController;

    @Test
    void getCurrentEvaluations() {
        InternalMetric im = new InternalMetric("internalMetric", "Internal metric");
        SubjectEvaluation e1 = new SubjectEvaluation("2001-07-22", im, "PES", 5.0);
        Mockito.when(subjectEvaluationRepository.findFirstBySubjectOrderByDateDesc("PES")).thenReturn(e1);
        List<SubjectEvaluation> evaluationList = subjectEvaluationRestController.getCurrentEvaluations("PES");
        Mockito.verify(subjectEvaluationRepository, Mockito.times(1)).
            findByDateAndSubject("2001-07-22", "PES");
    }

    @Test
    void getHistoricalEvaluations() {
        String dateBefore = "2001-07-22";
        String dateAfter = "2001-10-20";
        List<SubjectEvaluation> evaluationList = subjectEvaluationRestController.getHistoricalEvaluations("PES", dateBefore, dateAfter);
        Mockito.verify(subjectEvaluationRepository, Mockito.times(1)).
                findByDateBetweenAndSubjectOrderByInternalMetricAsc(dateBefore, dateAfter, "PES");

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
}