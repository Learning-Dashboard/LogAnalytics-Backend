package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class QModelAccessRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    QModelAccessRepository qModelAccessRepository;

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
    void findByTeamAndViewFormat() {
        QModelAccess qModelAccess1 = new QModelAccess(0, "team1", "testMessage", "testPage", session1, "testView");
        QModelAccess qModelAccess2 = new QModelAccess(5, "team1", "testMessage", "testPage", session1, "testView2");
        QModelAccess qModelAccess3 = new QModelAccess(0, "team2", "testMessage", "testPage", session2, "testView");
        QModelAccess qModelAccess4 = new QModelAccess(5, "team2", "testMessage", "testPage", session2, "testView2");
        entityManager.persistAndFlush(qModelAccess1);
        entityManager.persistAndFlush(qModelAccess2);
        entityManager.persistAndFlush(qModelAccess3);
        entityManager.persistAndFlush(qModelAccess4);
        assertEquals(1, qModelAccessRepository.findByTeamAndViewFormat("team1", "testView2").size());
        assertEquals(qModelAccess2, qModelAccessRepository.findByTeamAndViewFormat("team1", "testView2").get(0));
    }
}