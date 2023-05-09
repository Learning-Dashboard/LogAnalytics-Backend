package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.models.TeamEvaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamEvaluationRepository;
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
    TeamEvaluationRepository teamEvaluationRepository;

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public List<TeamEvaluation> getCurrentEvaluations(
        @RequestParam(name = "team") String team) {
        TeamEvaluation latestEvaluation = teamEvaluationRepository.findFirstByTeamOrderByDateDesc(team);
        if (latestEvaluation != null) {
            String latestDate = latestEvaluation.getDate();
            return teamEvaluationRepository.findByDateAndTeam(latestDate, team);
        }
        return new ArrayList<>();
    }

    @GetMapping("/historical")
    @ResponseStatus(HttpStatus.OK)
    public List<TeamEvaluation> getHistoricalEvaluations(
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
            return teamEvaluationRepository.
                    findByDateBetweenAndTeamOrderByInternalMetricAsc(dateBefore, dateAfter, team);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
        }
    }
}
