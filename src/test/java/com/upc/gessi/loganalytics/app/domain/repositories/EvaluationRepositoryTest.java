package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.UserlessInternalMetric;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class EvaluationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Test
    void findByDate() {
        InternalMetric im = new InternalMetric("testInternalMetric", "Test internal metric");
        Evaluation e1 = new Evaluation("2001-07-22", im, 5.0);
        Evaluation e2 = new Evaluation("2001-10-20", im, 2.5);
        entityManager.persistAndFlush(im);
        entityManager.persistAndFlush(e1); entityManager.persistAndFlush(e2);
        assertEquals(e1, evaluationRepository.findByDate("2001-07-22").get(0));
        assertEquals(1, evaluationRepository.findByDate("2001-07-22").size());
    }

    @Test
    void findFirstByOrderByDateDesc() {
        InternalMetric im = new InternalMetric("testInternalMetric", "Test internal metric");
        Evaluation e1 = new Evaluation("2001-07-22", im, 5.0);
        Evaluation e2 = new Evaluation("2001-10-20", im, 2.5);
        Evaluation e3 = new Evaluation("2000-10-20", im, 2.5);
        entityManager.persistAndFlush(im); entityManager.persistAndFlush(e1);
        entityManager.persistAndFlush(e2); entityManager.persistAndFlush(e3);
        assertEquals(e2, evaluationRepository.findFirstByOrderByDateDesc());
    }

    @Test
    void findByDateBetween() {
        InternalMetric im1 = new InternalMetric("testInternalMetric", "Test internal metric");
        InternalMetric im2 = new InternalMetric("testInternalMetric2", "Test internal metric 2");
        Evaluation e1 = new Evaluation("2001-07-22", im1, 5.0);
        Evaluation e2 = new Evaluation("2001-08-22", im2, 2.5);
        Evaluation e3 = new Evaluation("2001-09-22", im1, 2.5);
        Evaluation e4 = new Evaluation("2001-10-22", im2, 2.5);
        Evaluation e5 = new Evaluation("2001-11-22", im1, 2.5);
        Evaluation e6 = new Evaluation("2001-12-22", im2, 2.5);
        entityManager.persistAndFlush(im1); entityManager.persistAndFlush(im2);
        entityManager.persistAndFlush(e1); entityManager.persistAndFlush(e2);
        entityManager.persistAndFlush(e3); entityManager.persistAndFlush(e4);
        entityManager.persistAndFlush(e5); entityManager.persistAndFlush(e6);
        assertEquals(4, evaluationRepository.
                findByDateBetween("2001-07-22", "2001-10-22").size());
        assertEquals(e1, evaluationRepository.
            findByDateBetween("2001-07-22", "2001-10-22").get(0));
        assertEquals(e2, evaluationRepository.
            findByDateBetween("2001-07-22", "2001-10-22").get(1));
        assertEquals(e3, evaluationRepository.
            findByDateBetween("2001-07-22", "2001-10-22").get(2));
        assertEquals(e4, evaluationRepository.
            findByDateBetween("2001-07-22", "2001-10-22").get(3));
    }

    @Test
    void findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParam() {
        InternalMetric im1 = new InternalMetric("testInternalMetric", "Test internal metric",
            "testParam", "testParamName", "testController", "testControllerName", true, null);
        InternalMetric im2 = new InternalMetric("testInternalMetric2", "Test internal metric 2",
            "testParam2", "testParamName2", "testController2", "testControllerName2", true, null);
        Evaluation e1 = new Evaluation("2001-07-22", im1, 5.0);
        Evaluation e2 = new Evaluation("2001-08-22", im2, 2.5);
        Evaluation e3 = new Evaluation("2001-09-22", im1, 2.5);
        Evaluation e4 = new Evaluation("2001-10-22", im2, 2.5);
        Evaluation e5 = new Evaluation("2001-11-22", im1, 2.5);
        Evaluation e6 = new Evaluation("2001-12-22", im2, 2.5);
        entityManager.persistAndFlush(im1); entityManager.persistAndFlush(im2);
        entityManager.persistAndFlush(e1); entityManager.persistAndFlush(e2);
        entityManager.persistAndFlush(e3); entityManager.persistAndFlush(e4);
        entityManager.persistAndFlush(e5); entityManager.persistAndFlush(e6);
        assertEquals(2, evaluationRepository.
            findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParam
            ("2001-07-22", "2001-10-22", "testControllerName", "testParam")
            .size());
        assertEquals(e1, evaluationRepository.
            findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParam
            ("2001-07-22", "2001-10-22", "testControllerName", "testParam")
            .get(0));
        assertEquals(e3, evaluationRepository.
            findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParam
            ("2001-07-22", "2001-10-22", "testControllerName", "testParam")
            .get(1));
    }

    @Test
    void findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParamName() {
        InternalMetric im1 = new InternalMetric("testInternalMetric", "Test internal metric",
                "testParam", "testParamName", "testController", "testControllerName", true, null);
        InternalMetric im2 = new InternalMetric("testInternalMetric2", "Test internal metric 2",
                "testParam2", "testParamName2", "testController2", "testControllerName2", true, null);
        Evaluation e1 = new Evaluation("2001-07-22", im1, 5.0);
        Evaluation e2 = new Evaluation("2001-08-22", im2, 2.5);
        Evaluation e3 = new Evaluation("2001-09-22", im1, 2.5);
        Evaluation e4 = new Evaluation("2001-10-22", im2, 2.5);
        Evaluation e5 = new Evaluation("2001-11-22", im1, 2.5);
        Evaluation e6 = new Evaluation("2001-12-22", im2, 2.5);
        entityManager.persistAndFlush(im1); entityManager.persistAndFlush(im2);
        entityManager.persistAndFlush(e1); entityManager.persistAndFlush(e2);
        entityManager.persistAndFlush(e3); entityManager.persistAndFlush(e4);
        entityManager.persistAndFlush(e5); entityManager.persistAndFlush(e6);
        assertEquals(2, evaluationRepository.
                findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParamName
                    ("2001-07-22", "2001-10-22", "testControllerName", "testParamName")
                    .size());
        assertEquals(e1, evaluationRepository.
                findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParamName
                    ("2001-07-22", "2001-10-22", "testControllerName", "testParamName")
                    .get(0));
        assertEquals(e3, evaluationRepository.
                findByDateBetweenAndInternalMetricControllerNameAndInternalMetricParamName
                    ("2001-07-22", "2001-10-22", "testControllerName", "testParamName")
                    .get(1));
    }

    @Test
    void findByNoUserName() {
        UserlessInternalMetric im1 = new UserlessInternalMetric("testInternalMetric", "Test internal metric",
                "testParam", "testParamName", "testController",
                "testControllerName", true, null, "testId", "testName");
        UserlessInternalMetric im2 = new UserlessInternalMetric("testInternalMetric2", "Test internal metric 2",
                "testParam2", "testParamName2", "testController2",
                "testControllerName2", true, null, "testId2", "testName2");
        Evaluation e1 = new Evaluation("2001-07-22", im1, 5.0);
        Evaluation e2 = new Evaluation("2001-08-22", im2, 2.5);
        Evaluation e3 = new Evaluation("2001-09-22", im1, 2.5);
        Evaluation e4 = new Evaluation("2001-10-22", im2, 2.5);
        Evaluation e5 = new Evaluation("2001-11-22", im1, 2.5);
        Evaluation e6 = new Evaluation("2001-12-22", im2, 2.5);
        entityManager.persistAndFlush(im1); entityManager.persistAndFlush(im2);
        entityManager.persistAndFlush(e1); entityManager.persistAndFlush(e2);
        entityManager.persistAndFlush(e3); entityManager.persistAndFlush(e4);
        entityManager.persistAndFlush(e5); entityManager.persistAndFlush(e6);
        assertEquals(2, evaluationRepository.
                findByNoUserName
                    ("2001-07-22", "2001-10-22", "testControllerName", "testName")
                    .size());
        assertEquals(e1, evaluationRepository.
                findByNoUserName
                    ("2001-07-22", "2001-10-22", "testControllerName", "testName")
                    .get(0));
        assertEquals(e3, evaluationRepository.
                findByNoUserName
                    ("2001-07-22", "2001-10-22", "testControllerName", "testName")
                    .get(1));
    }
}