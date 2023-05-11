package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.EvaluationController;
import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.EvaluationRepository;
import com.upc.gessi.loganalytics.app.rest.DTOs.EvaluationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.CREATED)
    public List<EvaluationDTO> getCurrentEvaluations() {
        return evaluationController.getCurrentEvaluations();
    }

    @GetMapping("/historical")
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluationDTO> getHistoricalEvaluations(
        @RequestParam(name = "dateBefore") String dateBefore,
        @RequestParam (name = "dateAfter") String dateAfter) {
        return evaluationController.getHistoricalEvaluations(dateBefore, dateAfter);
    }

    @GetMapping("/historical/{displayableMetric}/{param}")
    @ResponseStatus(HttpStatus.OK)
    public EvaluationDTO getHistoricalEvaluationsByParam(
        @RequestParam(name = "dateBefore") String dateBefore,
        @RequestParam (name = "dateAfter") String dateAfter,
        @PathVariable (name = "displayableMetric") String displayableMetric,
        @PathVariable (name = "param") String param) {
        return evaluationController.getHistoricalEvaluationsByParam(dateBefore, dateAfter, displayableMetric, param);
    }
}
