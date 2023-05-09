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

    @Autowired
    EvaluationRepository evaluationRepository;

    @GetMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void evaluateInternalMetrics() {
        evaluationController.evaluateMetrics();
    }

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.CREATED)
    public List<EvaluationDTO> getCurrentEvaluations() { //Pasar a controller
        Evaluation latestEvaluation = evaluationRepository.findFirstByOrderByDateDesc();
        if (latestEvaluation != null) {
            String latestDate = latestEvaluation.getDate();
            List<Evaluation> unfilteredEvaluations = evaluationRepository.findAll();
            return filterEvaluations(unfilteredEvaluations, latestDate);
        }
        return new ArrayList<>();
    }

    @GetMapping("/historical")
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluationDTO> getHistoricalEvaluations(
        @RequestParam(name = "dateBefore") String dateBefore,
        @RequestParam (name = "dateAfter") String dateAfter) { //Pasar a controller
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            Date dBefore = formatter.parse(dateBefore);
            Date dAfter = formatter.parse(dateAfter);
            if (dateBefore.compareTo(dateAfter) > 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "dateBefore is not previous to dateAfter");
            List<Evaluation> unfilteredEvaluations = evaluationRepository.
                findByDateBetweenOrderByDateDesc(dateBefore, dateAfter);
            if (!unfilteredEvaluations.isEmpty()) {
                String latestDate = unfilteredEvaluations.get(0).getDate();
                return filterEvaluations(unfilteredEvaluations, latestDate);
            }
            return new ArrayList<>();

        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
        }
    }

    private List<EvaluationDTO> filterEvaluations(List<Evaluation> unfilteredEvaluations, String latestDate) {
        List<EvaluationDTO> result = new ArrayList<>();
        for (Evaluation e : unfilteredEvaluations) {
            if (!e.getInternalMetric().isGroupable()) {
                if (Objects.equals(e.getDate(), latestDate))
                    result.add(new EvaluationDTO(e));
            }
            else {
                boolean found = false;
                for (int i = 0; i < result.size() && !found; ++i) {
                    if (Objects.equals(result.get(i).getInternalMetric().getController(), e.getInternalMetric().getController())) {
                        Double oldValue = result.get(i).getEntities().get(e.getInternalMetric().getParam());
                        if (oldValue == null) oldValue = 0.0;
                        double newValue = oldValue + e.getValue();
                        Map<String,Double> oldEntities = result.get(i).getEntities();
                        oldEntities.put(e.getInternalMetric().getParam(), newValue);
                        result.get(i).setEntities(oldEntities);
                        found = true;
                    }
                }
                if (!found) {
                    Map<String,Double> entities = new HashMap<>();
                    entities.put(e.getInternalMetric().getParam(), e.getValue());
                    EvaluationDTO eDTO = new EvaluationDTO(e);
                    eDTO.setValue(0.0);
                    eDTO.setEntities(entities);
                    result.add(eDTO);
                }
            }
        }
        return result;
    }
}
