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
class FactorAccessRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    FactorAccessRepository factorAccessRepository;

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
    void findByTeamAndFactorsId() {
        List<Factor> factors = new ArrayList<>();
        factors.add(new Factor("f1"));
        List<Factor> factors2 = new ArrayList<>();
        factors2.add(new Factor("f2"));
        FactorAccess factorAccess1 = new FactorAccess(0, "team1", "testMessage", "testPage", session1, true, "testView", factors);
        FactorAccess factorAccess2 = new FactorAccess(5, "team1", "testMessage", "testPage", session1, true, "testView", factors2);
        FactorAccess factorAccess3 = new FactorAccess(0, "team2", "testMessage", "testPage", session2, true, "testView", factors);
        FactorAccess factorAccess4 = new FactorAccess(5, "team2", "testMessage", "testPage", session2, true, "testView", factors2);
        entityManager.persistAndFlush(new Factor("f1"));
        entityManager.persistAndFlush(new Factor("f2"));
        entityManager.persistAndFlush(factorAccess1);
        entityManager.persistAndFlush(factorAccess2);
        entityManager.persistAndFlush(factorAccess3);
        entityManager.persistAndFlush(factorAccess4);
        assertEquals(1, factorAccessRepository.findByTeamAndFactorsId("team1", "f2").size());
        assertEquals(factorAccess2, factorAccessRepository.findByTeamAndFactorsId("team1", "f2").get(0));
    }

    @Test
    void findByTeamAndViewFormat() {
        List<Factor> factors = new ArrayList<>();
        factors.add(new Factor("f1"));
        FactorAccess factorAccess1 = new FactorAccess(0, "team1", "testMessage", "testPage", session1, true, "testView", factors);
        FactorAccess factorAccess2 = new FactorAccess(5, "team1", "testMessage", "testPage", session1, true, "testView2", factors);
        FactorAccess factorAccess3 = new FactorAccess(0, "team2", "testMessage", "testPage", session2, true, "testView", factors);
        FactorAccess factorAccess4 = new FactorAccess(5, "team2", "testMessage", "testPage", session2, true, "testView2", factors);
        entityManager.persistAndFlush(new Factor("f1"));
        entityManager.persistAndFlush(factorAccess1);
        entityManager.persistAndFlush(factorAccess2);
        entityManager.persistAndFlush(factorAccess3);
        entityManager.persistAndFlush(factorAccess4);
        assertEquals(1, factorAccessRepository.findByTeamAndViewFormat("team1", "testView2").size());
        assertEquals(factorAccess2, factorAccessRepository.findByTeamAndViewFormat("team1", "testView2").get(0));
    }

    @Test
    void findByHistoricAndTeam() {
        List<Factor> factors = new ArrayList<>();
        factors.add(new Factor("f1"));
        FactorAccess factorAccess1 = new FactorAccess(0, "team1", "testMessage", "testPage", session1, true, "testView", factors);
        FactorAccess factorAccess2 = new FactorAccess(5, "team1", "testMessage", "testPage", session1, false, "testView", factors);
        FactorAccess factorAccess3 = new FactorAccess(0, "team2", "testMessage", "testPage", session2, true, "testView", factors);
        FactorAccess factorAccess4 = new FactorAccess(5, "team2", "testMessage", "testPage", session2, false, "testView", factors);
        entityManager.persistAndFlush(new Factor("f1"));
        entityManager.persistAndFlush(factorAccess1);
        entityManager.persistAndFlush(factorAccess2);
        entityManager.persistAndFlush(factorAccess3);
        entityManager.persistAndFlush(factorAccess4);
        assertEquals(1, factorAccessRepository.findByHistoricAndTeam(false, "team1").size());
        assertEquals(factorAccess2, factorAccessRepository.findByHistoricAndTeam(false, "team1").get(0));
    }
}