package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.models.TeamEvaluation;
import com.upc.gessi.loganalytics.app.domain.models.pkey.TeamEvaluationPrimaryKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamEvaluationRepository extends CrudRepository<TeamEvaluation, TeamEvaluationPrimaryKey> {
    List<TeamEvaluation> findByDateAndTeam(String date, String team);
    TeamEvaluation findFirstByTeamOrderByDateDesc(String team);
    List<TeamEvaluation> findByDateBetweenAndTeamOrderByInternalMetricAsc(
        String dateBefore, String dateAfter, String team);
}
