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
class LogRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LogRepository logRepository;

    @Test
    void findByTimeGreaterThanEqual() {
        Log log1 = new Log(0, "pes11a", "testMessage");
        Log log2 = new Log(10, "pes11a", "testMessage");
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqual(5);
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log2);
    }

    @Test
    void findByTimeLessThanEqual() {
        Log log1 = new Log(0, "pes11a", "testMessage");
        Log log2 = new Log(10, "pes11a", "testMessage");
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqual(5);
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log1);
    }

    @Test
    void findByTimeBetween() {
        Log log1 = new Log(0, "pes11a", "testMessage");
        Log log2 = new Log(10, "pes11a", "testMessage");
        Log log3 = new Log(7, "pes11a", "testMessage");
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTimeBetween(5, 10);
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        List<Log> actualLogList = new ArrayList<>();
        actualLogList.add(log2); actualLogList.add(log3);

        assertEquals(logList, actualLogList);
    }

    @Test
    void findByTeam() {
        Log log1 = new Log(0, "pes11a", "testMessage");
        Log log2 = new Log(0, "pes11b", "testMessage");
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Iterable<Log> logIterable = logRepository.findByTeam("pes11a");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log1);
    }

    @Test
    void findFirstByOrderByTimeDesc() {
        Log log1 = new Log(0, "pes11a", "testMessage");
        Log log2 = new Log(10, "pes11b", "testMessage");
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Log log = logRepository.findFirstByOrderByTimeDesc();
        assertEquals(log, log2);
    }

    @Test
    void findByMessageContaining() {
        Log log1 = new Log(0, "pes11a", "testMessage1");
        Log log2 = new Log(10, "pes11a", "testMessage2");
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Iterable<Log> logIterable = logRepository.findByMessageContaining("testMessage2");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log2);
    }

    @Test
    void findBySubject() {
        Subject subject1 = new Subject("PES");
        Subject subject2 = new Subject("ASW");
        Team team1 = new Team("pes11a", "sem", subject1);
        Team team2 = new Team("asw11a", "sem", subject2);
        Session session1 = new Session(team1, 0);
        Session session2 = new Session(team2, 0);
        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(0, "asw11a", "testMessage", session2);

        entityManager.persistAndFlush(subject1);
        entityManager.persistAndFlush(subject2);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Iterable<Log> logIterable = logRepository.findBySubject("PES");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log1);
    }
}