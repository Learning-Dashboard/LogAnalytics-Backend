package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.SubjectEvaluation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class SubjectEvaluationRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    SubjectEvaluationRepository subjectEvaluationRepository;

    @Test
    void findByDateAndSubject() {
        InternalMetric im = new InternalMetric("testInternalMetric", "Test internal metric");
        SubjectEvaluation e1 = new SubjectEvaluation("2001-07-22", im, "PES", 5.0);
        SubjectEvaluation e2 = new SubjectEvaluation("2001-10-20", im, "PES", 5.0);
        SubjectEvaluation e3 = new SubjectEvaluation("2001-07-22", im, "ASW", 5.0);
        SubjectEvaluation e4 = new SubjectEvaluation("2001-10-20", im, "ASW", 5.0);
        entityManager.persistAndFlush(im);
        entityManager.persistAndFlush(e1); entityManager.persistAndFlush(e2);
        entityManager.persistAndFlush(e3); entityManager.persistAndFlush(e4);
        assertEquals(e1, subjectEvaluationRepository.
            findByDateAndSubject("2001-07-22", "PES").get(0));
        assertEquals(1, subjectEvaluationRepository.
            findByDateAndSubject("2001-07-22", "PES").size());
    }

    @Test
    void findFirstBySubjectOrderByDateDesc() {
        InternalMetric im = new InternalMetric("testInternalMetric", "Test internal metric");
        SubjectEvaluation e1 = new SubjectEvaluation("2001-07-22", im, "PES", 5.0);
        SubjectEvaluation e2 = new SubjectEvaluation("2001-10-20", im, "PES", 5.0);
        SubjectEvaluation e3 = new SubjectEvaluation("2001-07-22", im, "ASW", 5.0);
        SubjectEvaluation e4 = new SubjectEvaluation("2001-10-20", im, "ASW", 5.0);
        entityManager.persistAndFlush(im);
        entityManager.persistAndFlush(e1); entityManager.persistAndFlush(e2);
        entityManager.persistAndFlush(e3); entityManager.persistAndFlush(e4);
        assertEquals(e2, subjectEvaluationRepository.
                findFirstBySubjectOrderByDateDesc("PES"));
    }

    @Test
    void findByDateBetweenAndSubjectOrderByInternalMetricAsc() {
        InternalMetric im = new InternalMetric("testInternalMetric", "Test internal metric");
        SubjectEvaluation e1 = new SubjectEvaluation("2001-07-22", im, "PES", 5.0);
        SubjectEvaluation e2 = new SubjectEvaluation("2001-10-20", im, "PES", 5.0);
        SubjectEvaluation e3 = new SubjectEvaluation("2001-07-22", im, "ASW", 5.0);
        SubjectEvaluation e4 = new SubjectEvaluation("2001-10-20", im, "ASW", 5.0);
        SubjectEvaluation e5 = new SubjectEvaluation("2001-11-20", im, "ASW", 5.0);
        SubjectEvaluation e6 = new SubjectEvaluation("2001-12-20", im, "ASW", 5.0);
        entityManager.persistAndFlush(im);
        entityManager.persistAndFlush(e1); entityManager.persistAndFlush(e2);
        entityManager.persistAndFlush(e3); entityManager.persistAndFlush(e4);
        entityManager.persistAndFlush(e5); entityManager.persistAndFlush(e6);
        assertEquals(e3, subjectEvaluationRepository.
            findBySubjectAndDateBetween("ASW", "2001-07-20",
            "2001-11-20").get(0));
        assertEquals(e4, subjectEvaluationRepository.
            findBySubjectAndDateBetween("ASW", "2001-07-20",
            "2001-11-20").get(1));
        assertEquals(e5, subjectEvaluationRepository.
            findBySubjectAndDateBetween("ASW", "2001-07-20",
            "2001-11-20").get(2));
        assertEquals(3, subjectEvaluationRepository.
            findBySubjectAndDateBetween("ASW", "2001-07-20",
            "2001-11-20").size());
    }
}