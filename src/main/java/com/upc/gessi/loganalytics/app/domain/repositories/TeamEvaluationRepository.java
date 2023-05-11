package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.TeamEvaluation;
import com.upc.gessi.loganalytics.app.domain.models.pkey.TeamEvaluationPrimaryKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamEvaluationRepository extends CrudRepository<TeamEvaluation, TeamEvaluationPrimaryKey> {
    List<TeamEvaluation> findByDateAndTeam(String date, String team);
    TeamEvaluation findFirstByTeamOrderByDateDesc(String team);
    List<TeamEvaluation> findByTeamAndDateBetween(String team, String dateBefore, String dateAfter);
    List<TeamEvaluation> findByTeamAndDateBetweenAndInternalMetricControllerNameAndInternalMetricParam
        (String team, String dateBefore, String dateAfter, String controllerName, String param);
    List<TeamEvaluation> findByTeam(String team);
}
