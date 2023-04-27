package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class MetricAccessRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    MetricAccessRepository metricAccessRepository;

    private Session session1;
    private Session session2;

    @BeforeEach
    void setUp() {
        Subject subject = new Subject("PES");
        Team team1 = new Team("team1", "sem", subject);
        Team team2 = new Team("team2", "sem", subject);
        entityManager.persistAndFlush(subject);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        session1 = new Session("session1", team1, 0);
        session2 = new Session("session2", team2, 0);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);
    }

    @Test
    void findByTeamAndMetricsId() {
        List<Metric> metrics = new ArrayList<>();
        metrics.add(new Metric("m1"));
        List<Metric> metrics2 = new ArrayList<>();
        metrics2.add(new Metric("m2"));
        MetricAccess metricAccess1 = new MetricAccess(0, "team1", "testMessage", "testPage", session1, true, "testView", metrics);
        MetricAccess metricAccess2 = new MetricAccess(5, "team1", "testMessage", "testPage", session1, true, "testView", metrics2);
        MetricAccess metricAccess3 = new MetricAccess(0, "team2", "testMessage", "testPage", session2, true, "testView", metrics);
        MetricAccess metricAccess4 = new MetricAccess(5, "team2", "testMessage", "testPage", session2, true, "testView", metrics2);
        entityManager.persistAndFlush(new Metric("m1"));
        entityManager.persistAndFlush(new Metric("m2"));
        entityManager.persistAndFlush(metricAccess1);
        entityManager.persistAndFlush(metricAccess2);
        entityManager.persistAndFlush(metricAccess3);
        entityManager.persistAndFlush(metricAccess4);
        assertEquals(1, metricAccessRepository.findByTeamAndMetricsId("team1", "m2").size());
        assertEquals(metricAccess2, metricAccessRepository.findByTeamAndMetricsId("team1", "m2").get(0));
    }

    @Test
    void findByTeamAndViewFormat() {
        List<Metric> metrics = new ArrayList<>();
        metrics.add(new Metric("m1"));
        MetricAccess metricAccess1 = new MetricAccess(0, "team1", "testMessage", "testPage", session1, true, "testView", metrics);
        MetricAccess metricAccess2 = new MetricAccess(5, "team1", "testMessage", "testPage", session1, true, "testView2", metrics);
        MetricAccess metricAccess3 = new MetricAccess(0, "team2", "testMessage", "testPage", session2, true, "testView", metrics);
        MetricAccess metricAccess4 = new MetricAccess(5, "team2", "testMessage", "testPage", session2, true, "testView2", metrics);
        entityManager.persistAndFlush(new Metric("m1"));
        entityManager.persistAndFlush(metricAccess1);
        entityManager.persistAndFlush(metricAccess2);
        entityManager.persistAndFlush(metricAccess3);
        entityManager.persistAndFlush(metricAccess4);
        assertEquals(1, metricAccessRepository.findByTeamAndViewFormat("team1", "testView2").size());
        assertEquals(metricAccess2, metricAccessRepository.findByTeamAndViewFormat("team1", "testView2").get(0));
    }

    @Test
    void findByHistoricAndTeam() {
        List<Metric> metrics = new ArrayList<>();
        metrics.add(new Metric("m1"));
        MetricAccess metricAccess1 = new MetricAccess(0, "team1", "testMessage", "testPage", session1, true, "testView", metrics);
        MetricAccess metricAccess2 = new MetricAccess(5, "team1", "testMessage", "testPage", session1, false, "testView", metrics);
        MetricAccess metricAccess3 = new MetricAccess(0, "team2", "testMessage", "testPage", session2, true, "testView", metrics);
        MetricAccess metricAccess4 = new MetricAccess(5, "team2", "testMessage", "testPage", session2, false, "testView", metrics);
        entityManager.persistAndFlush(new Metric("m1"));
        entityManager.persistAndFlush(metricAccess1);
        entityManager.persistAndFlush(metricAccess2);
        entityManager.persistAndFlush(metricAccess3);
        entityManager.persistAndFlush(metricAccess4);
        assertEquals(1, metricAccessRepository.findByHistoricAndTeam(false, "team1").size());
        assertEquals(metricAccess2, metricAccessRepository.findByHistoricAndTeam(false, "team1").get(0));
    }
}