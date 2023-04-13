package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.pkey.EvaluationPrimaryKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EvaluationRepository extends CrudRepository<Evaluation, EvaluationPrimaryKey> {
    List<Evaluation> findByDate(String date);
}
