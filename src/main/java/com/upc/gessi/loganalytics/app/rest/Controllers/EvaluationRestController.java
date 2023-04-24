package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.EvaluationController;
import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationRestController {

    @Autowired
    EvaluationController evaluationController;

    @Autowired
    EvaluationRepository evaluationRepository;

    @GetMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void evaluateInternalMetrics() {
        evaluationController.evaluateMetrics();
    }

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public List<Evaluation> getCurrentEvaluations() {
        Evaluation latestEvaluation = evaluationRepository.findFirstByOrderByDateDesc();
        String latestDate = latestEvaluation.getDate();
        return evaluationRepository.findByDate(latestDate);
    }

    @GetMapping("/historical")
    @ResponseStatus(HttpStatus.OK)
    public List<Evaluation> getHistoricalEvaluations(
        @RequestParam(name = "dateBefore") String dateBefore,
        @RequestParam (name = "dateAfter") String dateAfter) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            Date dBefore = formatter.parse(dateBefore);
            Date dAfter = formatter.parse(dateAfter);
            System.out.println(dBefore);
            if (dateBefore.compareTo(dateAfter) > 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "dateBefore is not previous to dateAfter");
            return evaluationRepository.
                findByDateBetweenOrderByInternalMetricAsc(dateBefore, dateAfter);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
        }
    }
}
