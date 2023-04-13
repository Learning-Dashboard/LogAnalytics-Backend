package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.SubjectEvaluation;
import com.upc.gessi.loganalytics.app.domain.models.pkey.SubjectEvaluationPrimaryKey;
import org.springframework.data.repository.CrudRepository;

public interface SubjectEvaluationRepository extends CrudRepository<SubjectEvaluation, SubjectEvaluationPrimaryKey> {

}
