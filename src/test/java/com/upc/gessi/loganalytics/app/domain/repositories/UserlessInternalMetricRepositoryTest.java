package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.UserlessInternalMetric;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class UserlessInternalMetricRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserlessInternalMetricRepository userlessInternalMetricRepository;

    @Test
    void existsByUserlessName() {
        UserlessInternalMetric im1 = new UserlessInternalMetric("im1", "im1Name", "p1", "p1", "c1", "c1", true, null, null);
        entityManager.persistAndFlush(im1);
        assertFalse(userlessInternalMetricRepository.existsByUserlessName("name2"));
        UserlessInternalMetric im2 = new UserlessInternalMetric("im2", "im2Name", "p2", "p2", "c2", "c2", true, "id", "name");
        entityManager.persistAndFlush(im2);
        assertFalse(userlessInternalMetricRepository.existsByUserlessName("name2"));
        UserlessInternalMetric im3 = new UserlessInternalMetric("im3", "im3Name", "p3", "p3", "c3", "c3", true, "id2", "name2");
        entityManager.persistAndFlush(im3);
        assertTrue(userlessInternalMetricRepository.existsByUserlessName("name2"));
    }
}