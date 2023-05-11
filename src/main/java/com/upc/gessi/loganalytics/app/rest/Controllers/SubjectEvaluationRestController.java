package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.SubjectController;
import com.upc.gessi.loganalytics.app.domain.controllers.SubjectEvaluationController;
import com.upc.gessi.loganalytics.app.domain.models.SubjectEvaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectEvaluationRepository;
import com.upc.gessi.loganalytics.app.rest.DTOs.EvaluationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/subjectEvaluations")
public class SubjectEvaluationRestController {

    @Autowired
    SubjectEvaluationController subjectEvaluationController;

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluationDTO> getCurrentEvaluations(
        @RequestParam(name = "subject") String subject) {
        return subjectEvaluationController.getCurrentEvaluations(subject);
    }

    @GetMapping("/historical")
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluationDTO> getHistoricalEvaluations(
        @RequestParam(name = "subject") String subject,
        @RequestParam(name = "dateBefore") String dateBefore,
        @RequestParam (name = "dateAfter") String dateAfter) {
        return subjectEvaluationController.getHistoricalEvaluations(subject, dateBefore, dateAfter);
    }

    @GetMapping("/historical/{displayableMetric}/{param}")
    @ResponseStatus(HttpStatus.OK)
    public EvaluationDTO getHistoricalEvaluationsByParam(
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "dateBefore") String dateBefore,
            @RequestParam (name = "dateAfter") String dateAfter,
            @PathVariable (name = "displayableMetric") String displayableMetric,
            @PathVariable (name = "param") String param) {
        return subjectEvaluationController.getHistoricalEvaluationsByParam(subject, dateBefore, dateAfter, displayableMetric, param);
    }
}
