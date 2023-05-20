package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.SubjectEvaluation;
import com.upc.gessi.loganalytics.app.domain.models.pkey.SubjectEvaluationPrimaryKey;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectEvaluationRepository extends CrudRepository<SubjectEvaluation, SubjectEvaluationPrimaryKey> {
    List<SubjectEvaluation> findByDateAndSubject(String date, String subject);
    SubjectEvaluation findFirstBySubjectOrderByDateDesc(String subject);
    List<SubjectEvaluation> findBySubjectAndDateBetween(String subject, String dateBefore, String dateAfter);
    List<SubjectEvaluation> findBySubjectAndDateBetweenAndInternalMetricControllerNameAndInternalMetricParam
        (String subject, String dateBefore, String dateAfter, String controllerName, String param);
    List<SubjectEvaluation> findBySubjectAndDateBetweenAndInternalMetricControllerNameAndInternalMetricParamName
        (String subject, String dateBefore, String dateAfter, String controllerName, String paramName);
    @Query("SELECT e FROM SubjectEvaluation e " +
        "JOIN InternalMetric im ON e.internalMetric = im.id " +
        "JOIN UserlessInternalMetric uim ON im.id = uim.id " +
        "WHERE e.subject = ?1 " +
        "AND e.date BETWEEN ?2 AND ?3 " +
        "AND im.controllerName = ?4 " +
        "AND uim.userlessName = ?5")
    List<SubjectEvaluation> findByNoUserName
        (String subject, String dateBefore, String dateAfter, String controllerName, String paramName);
    List<SubjectEvaluation> findBySubject(String subject);
}
