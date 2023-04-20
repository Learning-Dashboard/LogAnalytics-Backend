package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Log;
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
        Subject subj1 = new Subject("PES");
        Subject subj2 = new Subject("ASW");
        Team team1 = new Team("pes11a", "sem", subj1);
        Team team2 = new Team("asw11a", "sem", subj2);
        Session session1 = new Session("s1", team1, 0);
        Session session2 = new Session("s2", team2, 0);
        entityManager.persistAndFlush(subj1);
        entityManager.persistAndFlush(subj2);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);

        Iterable<Session> sessionIterable = sessionRepository.findByTeam(team1);
        List<Session> sessionList = new ArrayList<>();
        sessionIterable.forEach(sessionList::add);
        assertEquals(sessionList.get(0), session1);
    }

    @Test
    void findByTeamSubject() {
        Subject subj1 = new Subject("PES");
        Subject subj2 = new Subject("ASW");
        Team team1 = new Team("pes11a", "sem", subj1);
        Team team2 = new Team("asw11a", "sem", subj2);
        Session session1 = new Session("s1", team1, 0);
        Session session2 = new Session("s2", team2, 0);
        entityManager.persistAndFlush(subj1);
        entityManager.persistAndFlush(subj2);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);

        Iterable<Session> sessionIterable = sessionRepository.findByTeamSubject(subj1);
        List<Session> sessionList = new ArrayList<>();
        sessionIterable.forEach(sessionList::add);
        assertEquals(sessionList.get(0), session1);
    }

    @Test
    void findByStartTimestampGreaterThanEqualAndTeam() {
        Subject subj1 = new Subject("PES");
        Subject subj2 = new Subject("ASW");
        Team team1 = new Team("pes11a", "sem", subj1);
        Team team2 = new Team("asw11a", "sem", subj2);
        Session session1 = new Session("s1", team1, 0);
        Session session2 = new Session("s2", team1, 10);
        Session session3 = new Session("s3", team2, 10);
        entityManager.persistAndFlush(subj1);
        entityManager.persistAndFlush(subj2);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);
        entityManager.persistAndFlush(session3);

        Iterable<Session> sessionIterable = sessionRepository.findByStartTimestampGreaterThanEqualAndTeam(5, team1);
        List<Session> sessionList = new ArrayList<>();
        sessionIterable.forEach(sessionList::add);
        assertEquals(sessionList.get(0), session2);
    }
}