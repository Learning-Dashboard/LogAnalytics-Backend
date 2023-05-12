package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.TeamEvaluationController;
import com.upc.gessi.loganalytics.app.domain.models.TeamEvaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamEvaluationRepository;
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
@RequestMapping("/api/teamEvaluations")
public class TeamEvaluationRestController {

    @Autowired
    TeamEvaluationController teamEvaluationController;

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluationDTO> getCurrentEvaluations(
        @RequestParam(name = "team") String team) {
        return teamEvaluationController.getCurrentEvaluations(team);
    }

    @GetMapping("/historical")
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluationDTO> getHistoricalEvaluations(
        @RequestParam(name = "team") String team,
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
            else return teamEvaluationController.getHistoricalEvaluations(team, dateBefore, dateAfter);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
        }
    }

    @GetMapping("/historical/{displayableMetric}/{param}")
    @ResponseStatus(HttpStatus.OK)
    public EvaluationDTO getHistoricalEvaluationsByParam(
            @RequestParam(name = "team") String team,
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
            else return teamEvaluationController.getHistoricalEvaluationsByParam(team, dateBefore, dateAfter, metric, param);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
        }
    }
}
