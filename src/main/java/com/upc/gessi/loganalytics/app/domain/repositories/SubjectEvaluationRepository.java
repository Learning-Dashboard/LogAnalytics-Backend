package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.SubjectEvaluation;
import com.upc.gessi.loganalytics.app.domain.models.pkey.SubjectEvaluationPrimaryKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectEvaluationRepository extends CrudRepository<SubjectEvaluation, SubjectEvaluationPrimaryKey> {
    List<SubjectEvaluation> findByDateAndSubject(String date, String subject);
    SubjectEvaluation findFirstBySubjectOrderByDateDesc(String subject);
    List<SubjectEvaluation> findByDateBetweenAndSubjectOrderByInternalMetricAsc(
        String dateBefore, String dateAfter, String subject);
}
