package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.TeamEvaluation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class TeamEvaluationRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TeamEvaluationRepository teamEvaluationRepository;

    @Test
    void findByDateAndTeam() {
        InternalMetric im = new InternalMetric("testInternalMetric", "Test internal metric");
        TeamEvaluation e1 = new TeamEvaluation("2001-07-22", im, "PES", 5.0);
        TeamEvaluation e2 = new TeamEvaluation("2001-10-20", im, "PES", 5.0);
        TeamEvaluation e3 = new TeamEvaluation("2001-07-22", im, "ASW", 5.0);
        TeamEvaluation e4 = new TeamEvaluation("2001-10-20", im, "ASW", 5.0);
        entityManager.persistAndFlush(im);
        entityManager.persistAndFlush(e1); entityManager.persistAndFlush(e2);
        entityManager.persistAndFlush(e3); entityManager.persistAndFlush(e4);
        assertEquals(e1, teamEvaluationRepository.
                findByDateAndTeam("2001-07-22", "PES").get(0));
        assertEquals(1, teamEvaluationRepository.
                findByDateAndTeam("2001-07-22", "PES").size());
    }

    @Test
    void findFirstByTeamOrderByDateDesc() {
        InternalMetric im = new InternalMetric("testInternalMetric", "Test internal metric");
        TeamEvaluation e1 = new TeamEvaluation("2001-07-22", im, "PES", 5.0);
        TeamEvaluation e2 = new TeamEvaluation("2001-10-20", im, "PES", 5.0);
        TeamEvaluation e3 = new TeamEvaluation("2001-07-22", im, "ASW", 5.0);
        TeamEvaluation e4 = new TeamEvaluation("2001-10-20", im, "ASW", 5.0);
        entityManager.persistAndFlush(im);
        entityManager.persistAndFlush(e1); entityManager.persistAndFlush(e2);
        entityManager.persistAndFlush(e3); entityManager.persistAndFlush(e4);
        assertEquals(e2, teamEvaluationRepository.
                findFirstByTeamOrderByDateDesc("PES"));
    }

    @Test
    void findByDateBetweenAndTeamOrderByInternalMetricAsc() {
        InternalMetric im = new InternalMetric("testInternalMetric", "Test internal metric");
        TeamEvaluation e1 = new TeamEvaluation("2001-07-22", im, "PES", 5.0);
        TeamEvaluation e2 = new TeamEvaluation("2001-10-20", im, "PES", 5.0);
        TeamEvaluation e3 = new TeamEvaluation("2001-07-22", im, "ASW", 5.0);
        TeamEvaluation e4 = new TeamEvaluation("2001-10-20", im, "ASW", 5.0);
        TeamEvaluation e5 = new TeamEvaluation("2001-11-20", im, "ASW", 5.0);
        TeamEvaluation e6 = new TeamEvaluation("2001-12-20", im, "ASW", 5.0);
        entityManager.persistAndFlush(im);
        entityManager.persistAndFlush(e1); entityManager.persistAndFlush(e2);
        entityManager.persistAndFlush(e3); entityManager.persistAndFlush(e4);
        entityManager.persistAndFlush(e5); entityManager.persistAndFlush(e6);
        assertEquals(e3, teamEvaluationRepository.
            findByDateBetweenAndTeamOrderByInternalMetricAsc("2001-07-20",
            "2001-11-20", "ASW").get(0));
        assertEquals(e4, teamEvaluationRepository.
            findByDateBetweenAndTeamOrderByInternalMetricAsc("2001-07-20",
            "2001-11-20", "ASW").get(1));
        assertEquals(e5, teamEvaluationRepository.
            findByDateBetweenAndTeamOrderByInternalMetricAsc("2001-07-20",
            "2001-11-20", "ASW").get(2));
        assertEquals(3, teamEvaluationRepository.
            findByDateBetweenAndTeamOrderByInternalMetricAsc("2001-07-20",
            "2001-11-20", "ASW").size());
    }
}