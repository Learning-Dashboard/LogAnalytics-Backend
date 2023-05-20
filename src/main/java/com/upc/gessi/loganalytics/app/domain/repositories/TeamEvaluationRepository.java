package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.TeamEvaluation;
import com.upc.gessi.loganalytics.app.domain.models.pkey.TeamEvaluationPrimaryKey;
import org.springframework.data.jpa.repository.Query;
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
    List<TeamEvaluation> findByTeamAndDateBetweenAndInternalMetricControllerNameAndInternalMetricParamName
        (String team, String dateBefore, String dateAfter, String controllerName, String paramName);
    @Query("SELECT e FROM TeamEvaluation e " +
            "JOIN InternalMetric im ON e.internalMetric = im.id " +
            "JOIN UserlessInternalMetric uim ON im.id = uim.id " +
            "WHERE e.team = ?1 " +
            "AND e.date BETWEEN ?2 AND ?3 " +
            "AND im.controllerName = ?4 " +
            "AND uim.userlessName = ?5")
    List<TeamEvaluation> findByNoUserName
        (String team, String dateBefore, String dateAfter, String controllerName, String paramName);
    List<TeamEvaluation> findByTeam(String team);
}
