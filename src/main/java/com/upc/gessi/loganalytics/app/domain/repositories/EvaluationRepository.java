package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.pkey.EvaluationPrimaryKey;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends CrudRepository<Evaluation, EvaluationPrimaryKey> {
    List<Evaluation> findByDate(String date);
    Evaluation findFirstByOrderByDateDesc();
    List<Evaluation> findByDateBetween(String dateBefore, String dateAfter);
    List<Evaluation> findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParam
        (String dateBefore, String dateAfter, String controllerName, String param);
    List<Evaluation> findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParamName
        (String dateBefore, String dateAfter, String controllerName, String paramName);
    @Query("SELECT e FROM Evaluation e " +
        "JOIN InternalMetric im ON e.internalMetric = im.id " +
        "JOIN UserlessInternalMetric uim ON im.id = uim.id " +
        "WHERE e.date BETWEEN ?1 AND ?2 " +
        "AND im.controllerName = ?3 " +
        "AND uim.userlessName = ?4")
    List<Evaluation> findByNoUserName
        (String dateBefore, String dateAfter, String controllerName, String paramName);
    @NotNull
    List<Evaluation> findAll();
}
