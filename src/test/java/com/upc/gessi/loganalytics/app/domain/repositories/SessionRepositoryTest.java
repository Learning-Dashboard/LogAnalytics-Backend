package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.AppUser;
import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
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
class SessionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    void findByTeam() {
        Subject subject = new Subject("PES");
        Team team1 = new Team("pes11a", "sem", subject);
        Team team2 = new Team("pes11b", "sem", subject);
        Session session = new Session(team1, 0);
        Session session2 = new Session(team2, 0);

        entityManager.persistAndFlush(subject);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(session);
        entityManager.persistAndFlush(session2);

        Iterable<Session> sessionsFound = sessionRepository.findByTeam(team1);
        List<Session> sessionList = new ArrayList<>();
        sessionsFound.forEach(sessionList::add);
        assertEquals(sessionList.get(0), session);
    }

    @Test
    void findByTeamAndStartTimestampLessThanEqual() {
        Subject subject = new Subject("PES");
        Team team1 = new Team("pes11a", "sem", subject);
        Team team2 = new Team("pes11b", "sem", subject);
        Session session1 = new Session(team1, 0);
        Session session2 = new Session(team2, 0);
        Session session3 = new Session(team1, 10);
        Session session4 = new Session(team2, 10);

        entityManager.persistAndFlush(subject);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);
        entityManager.persistAndFlush(session3);
        entityManager.persistAndFlush(session4);

        Iterable<Session> sessionsFound = sessionRepository.findByTeamAndStartTimestampLessThanEqual(team1, 5);
        List<Session> sessionList = new ArrayList<>();
        sessionsFound.forEach(sessionList::add);
        assertEquals(sessionList.get(0), session1);
    }
}