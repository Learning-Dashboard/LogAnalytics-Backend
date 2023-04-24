package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.EvaluationController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EvaluationRestControllerTest {

    @Mock
    EvaluationController evaluationController;

    @InjectMocks
    EvaluationRestController evaluationRestController;

    @Test
    void evaluateInternalMetrics() {
        evaluationRestController.evaluateInternalMetrics();
        Mockito.verify(evaluationController, Mockito.times(1)).evaluateMetrics();
    }
}