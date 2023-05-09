package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.pkey.EvaluationPrimaryKey;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends CrudRepository<Evaluation, EvaluationPrimaryKey> {
    List<Evaluation> findByDate(String date);
    Evaluation findFirstByOrderByDateDesc();
    List<Evaluation> findByDateBetweenOrderByDateDesc(String dateBefore, String dateAfter);
    @NotNull
    List<Evaluation> findAll();
}
