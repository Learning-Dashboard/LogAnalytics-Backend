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
        return teamEvaluationController.getHistoricalEvaluations(team, dateBefore, dateAfter);
    }

    @GetMapping("/historical/{displayableMetric}/{param}")
    @ResponseStatus(HttpStatus.OK)
    public EvaluationDTO getHistoricalEvaluationsByParam(
            @RequestParam(name = "team") String team,
            @RequestParam(name = "dateBefore") String dateBefore,
            @RequestParam (name = "dateAfter") String dateAfter,
            @PathVariable (name = "displayableMetric") String displayableMetric,
            @PathVariable (name = "param") String param) {
        return teamEvaluationController.getHistoricalEvaluationsByParam(team, dateBefore, dateAfter, displayableMetric, param);
    }
}
