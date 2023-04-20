package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.EvaluationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationRestController {

    @Autowired
    EvaluationController evaluationController;

    @GetMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void evaluateInternalMetrics() {
        evaluationController.evaluateMetrics();
    }
}
