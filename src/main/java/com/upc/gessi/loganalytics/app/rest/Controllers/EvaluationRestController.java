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
    @ResponseStatus(HttpStatus.CREATED)
    public void evaluateInternalMetrics() {
        evaluationController.evaluateMetrics();
    }

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluationDTO> getCurrentEvaluations() {
        return evaluationController.getCurrentEvaluations();
    }

    @GetMapping("/historical")
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluationDTO> getHistoricalEvaluations(
        @RequestParam(name = "dateBefore") String dateBefore,
        @RequestParam (name = "dateAfter") String dateAfter) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            Date dBefore = formatter.parse(dateBefore);
            Date dAfter = formatter.parse(dateAfter);
            if (dateBefore.compareTo(dateAfter) > 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "dateBefore is not previous to dateAfter");
            else return evaluationController.getHistoricalEvaluations(dateBefore, dateAfter); //TODO: No sé si irá bien
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
        }
    }

    @GetMapping("/historical/{metric}/{param}")
    @ResponseStatus(HttpStatus.OK)
    public EvaluationDTO getHistoricalEvaluationsByParam(
        @RequestParam(name = "dateBefore") String dateBefore,
        @RequestParam (name = "dateAfter") String dateAfter,
        @PathVariable (name = "metric") String metric,
        @PathVariable (name = "param") String param) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            Date dBefore = formatter.parse(dateBefore);
            Date dAfter = formatter.parse(dateAfter);
            if (dateBefore.compareTo(dateAfter) > 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "dateBefore is not previous to dateAfter");
            else return evaluationController.getHistoricalEvaluationsByParam(dateBefore, dateAfter, metric, param);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
        }
    }
}
