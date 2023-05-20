package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class InternalMetricRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private InternalMetricRepository internalMetricRepository;

    @Test
    void existsByParamName() {
        InternalMetric im1 = new InternalMetric("im1", "im1Name");
        entityManager.persistAndFlush(im1);
        assertFalse(internalMetricRepository.existsByParamName("paramName"));
        InternalMetric im2 = new InternalMetric("im2", "im2Name", "p2", null, "c2", "c2", true, null);
        entityManager.persistAndFlush(im2);
        assertFalse(internalMetricRepository.existsByParamName("paramName"));
        InternalMetric im3 = new InternalMetric("im3", "im3Name", "p3", "p3", "c3", "c3", true, null);
        entityManager.persistAndFlush(im3);
        assertFalse(internalMetricRepository.existsByParamName("paramName"));
        InternalMetric im4 = new InternalMetric("im4", "im4Name", "p4", "paramName", "c4", "c4", true, null);
        entityManager.persistAndFlush(im4);
        assertTrue(internalMetricRepository.existsByParamName("paramName"));
    }
}