package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.EvaluationRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectEvaluationRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.TeamEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class EvaluationController {

    @Autowired
    EvaluationRepository evaluationRepository;

    @Autowired
    SubjectEvaluationRepository subjectEvaluationRepository;

    @Autowired
    TeamEvaluationRepository teamEvaluationRepository;

    @Autowired
    InternalMetricController internalMetricController;

    @Autowired
    TeamController teamController;

    @Autowired
    SubjectController subjectController;

    public void evaluateMetrics() {
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(today);

        Iterable<Evaluation> currentEvaluations = evaluationRepository.findByDate(date);
        List<Evaluation> evaluationList = new ArrayList<>();
        currentEvaluations.forEach(evaluationList::add);
        if (evaluationList.size() > 0)
            evaluationRepository.deleteAll(currentEvaluations);

        List<InternalMetric> internalMetrics = internalMetricController.getAll();
        List<Team> teams = teamController.getStoredTeams();
        List<Subject> subjects = subjectController.getAll();

        for (InternalMetric im : internalMetrics) {
            HashMap<Subject, Double> subjectHashMap = new HashMap<>();
            for (Subject s : subjects) subjectHashMap.put(s, 0.0);
            double globalValue = 0.0;
            for (Team t : teams) {
                double value = im.evaluate(t);
                globalValue += value;
                Subject s = t.getSubject();
                double valueSubject = subjectHashMap.get(s);
                subjectHashMap.put(s, valueSubject + value);
                TeamEvaluation teamEvaluation = new TeamEvaluation(date, im, t.getId(), value);
                teamEvaluationRepository.save(teamEvaluation);
            }
            for (Subject s : subjects) {
                double value = subjectHashMap.get(s);
                SubjectEvaluation subjectEvaluation = new SubjectEvaluation(date, im, s.getAcronym(), value);
                subjectEvaluationRepository.save(subjectEvaluation);
            }
            Evaluation evaluation = new Evaluation(date, im, globalValue);
            evaluationRepository.save(evaluation);
        }
    }
}
