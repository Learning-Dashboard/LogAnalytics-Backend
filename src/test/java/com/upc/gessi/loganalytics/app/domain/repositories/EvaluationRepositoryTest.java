package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
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
        InternalMetric im = new InternalMetric("testInternalMetric");
        Evaluation e1 = new Evaluation("2001-07-22", im, 5.0);
        Evaluation e2 = new Evaluation("2001-10-20", im, 2.5);
        entityManager.persistAndFlush(im);
        entityManager.persistAndFlush(e1);
        entityManager.persistAndFlush(e2);
        assertEquals(e1, evaluationRepository.findByDate("2001-07-22").get(0));
        assertEquals(1, evaluationRepository.findByDate("2001-07-22").size());
    }
}