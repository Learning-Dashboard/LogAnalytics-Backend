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
class IndicatorAccessRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    IndicatorAccessRepository indicatorAccessRepository;

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
    void findByTeamAndIndicatorsId() {
        List<Indicator> indicators = new ArrayList<>();
        indicators.add(new Indicator("i1"));
        List<Indicator> indicators2 = new ArrayList<>();
        indicators2.add(new Indicator("i2"));
        IndicatorAccess indicatorAccess1 = new IndicatorAccess(0, "team1", "testMessage", "testPage", session1, true, "testView", indicators);
        IndicatorAccess indicatorAccess2 = new IndicatorAccess(5, "team1", "testMessage", "testPage", session1, true, "testView", indicators2);
        IndicatorAccess indicatorAccess3 = new IndicatorAccess(0, "team2", "testMessage", "testPage", session2, true, "testView", indicators);
        IndicatorAccess indicatorAccess4 = new IndicatorAccess(5, "team2", "testMessage", "testPage", session2, true, "testView", indicators2);
        entityManager.persistAndFlush(indicatorAccess1);
        entityManager.persistAndFlush(indicatorAccess2);
        entityManager.persistAndFlush(indicatorAccess3);
        entityManager.persistAndFlush(indicatorAccess4);
        assertEquals(1, indicatorAccessRepository.findByTeamAndIndicatorsId("team1", "i2").size());
        assertEquals(indicatorAccess2, indicatorAccessRepository.findByTeamAndIndicatorsId("team1", "i2").get(0));
    }

    @Test
    void findByTeamAndViewFormat() {
        List<Indicator> indicators = new ArrayList<>();
        indicators.add(new Indicator("i1"));
        IndicatorAccess indicatorAccess1 = new IndicatorAccess(0, "team1", "testMessage", "testPage", session1, true, "testView", indicators);
        IndicatorAccess indicatorAccess2 = new IndicatorAccess(5, "team1", "testMessage", "testPage", session1, true, "testView2", indicators);
        IndicatorAccess indicatorAccess3 = new IndicatorAccess(0, "team2", "testMessage", "testPage", session2, true, "testView", indicators);
        IndicatorAccess indicatorAccess4 = new IndicatorAccess(5, "team2", "testMessage", "testPage", session2, true, "testView2", indicators);
        entityManager.persistAndFlush(indicatorAccess1);
        entityManager.persistAndFlush(indicatorAccess2);
        entityManager.persistAndFlush(indicatorAccess3);
        entityManager.persistAndFlush(indicatorAccess4);
        assertEquals(1, indicatorAccessRepository.findByTeamAndViewFormat("team1", "testView2").size());
        assertEquals(indicatorAccess2, indicatorAccessRepository.findByTeamAndViewFormat("team1", "testView2").get(0));
    }

    @Test
    void findByHistoricAndTeam() {
        List<Indicator> indicators = new ArrayList<>();
        indicators.add(new Indicator("i1"));
        IndicatorAccess indicatorAccess1 = new IndicatorAccess(0, "team1", "testMessage", "testPage", session1, true, "testView", indicators);
        IndicatorAccess indicatorAccess2 = new IndicatorAccess(5, "team1", "testMessage", "testPage", session1, false, "testView", indicators);
        IndicatorAccess indicatorAccess3 = new IndicatorAccess(0, "team2", "testMessage", "testPage", session2, true, "testView", indicators);
        IndicatorAccess indicatorAccess4 = new IndicatorAccess(5, "team2", "testMessage", "testPage", session2, false, "testView", indicators);
        entityManager.persistAndFlush(indicatorAccess1);
        entityManager.persistAndFlush(indicatorAccess2);
        entityManager.persistAndFlush(indicatorAccess3);
        entityManager.persistAndFlush(indicatorAccess4);
        assertEquals(1, indicatorAccessRepository.findByHistoricAndTeam(false, "team1").size());
        assertEquals(indicatorAccess2, indicatorAccessRepository.findByHistoricAndTeam(false, "team1").get(0));
    }
}