package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.models.SubjectEvaluation;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/subjectEvaluations")
public class SubjectEvaluationRestController {

    @Autowired
    SubjectEvaluationRepository subjectEvaluationRepository;

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectEvaluation> getCurrentEvaluations(
        @RequestParam(name = "subject") String subject) {
        SubjectEvaluation latestEvaluation = subjectEvaluationRepository.findFirstBySubjectOrderByDateDesc(subject);
        String latestDate = latestEvaluation.getDate();
        return subjectEvaluationRepository.findByDateAndSubject(latestDate, subject);
    }

    @GetMapping("/historical")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectEvaluation> getHistoricalEvaluations(
        @RequestParam(name = "subject") String subject,
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
            return subjectEvaluationRepository.
                    findByDateBetweenAndSubjectOrderByInternalMetricAsc(dateBefore, dateAfter, subject);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
        }
    }
}
