package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.TeamEvaluation;
import com.upc.gessi.loganalytics.app.domain.models.pkey.TeamEvaluationPrimaryKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamEvaluationRepository extends CrudRepository<TeamEvaluation, TeamEvaluationPrimaryKey> {

}
