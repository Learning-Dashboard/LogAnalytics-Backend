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
    void findByPageAndTeam() {
        Subject subj = new Subject("PES");
        Team team = new Team("pes11a", "sem", subj);
        Session session = new Session("s", team, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team);
        entityManager.persistAndFlush(session);

        Log log1 = new Log(0, "pes11a", "testMessage", "testPage", session);
        Log log2 = new Log(6, "pes11a", "testMessage", "testPage", session);
        Log log3 = new Log(10, "pes11a", "testMessage", "testPage2", session);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByPageAndTeamAndTimeBetween("testPage", "pes11a", 0L, 5L);
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log1);
        assertEquals(logList.size(), 1);
    }

    @Test
    void findAll() {
        Subject subj = new Subject("PES");
        Team team = new Team("pes11a", "sem", subj);
        Session session = new Session("s", team, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team);
        entityManager.persistAndFlush(session);

        Log log1 = new Log(0, "pes11a", "testMessage", session);
        Log log2 = new Log(10, "pes11a", "testMessage", session);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Iterable<Log> logIterable = logRepository.findAllByOrderByTimeDesc();
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log2);
    }

    @Test
    void findByTimeGreaterThanEqual() {
        Subject subj = new Subject("PES");
        Team team = new Team("pes11a", "sem", subj);
        Session session = new Session("s", team, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team);
        entityManager.persistAndFlush(session);

        Log log1 = new Log(0, "pes11a", "testMessage", session);
        Log log2 = new Log(10, "pes11a", "testMessage", session);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualOrderByTimeDesc(5);
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log2);
    }

    @Test
    public void findByTimeGreaterThanEqualAndMessageContaining() {
        Subject subj = new Subject("PES");
        Team team = new Team("pes11a", "sem", subj);
        Session session = new Session("s", team, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team);
        entityManager.persistAndFlush(session);

        Log log1 = new Log(0, "pes11a", "testMessage", session);
        Log log2 = new Log(10, "pes11a", "testMessage", session);
        Log log3 = new Log(15, "pes11a", "testMessage2", session);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualAndMessageContainingOrderByTimeDesc(5, "testMessage2");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log3);
    }

    @Test
    public void findByTimeGreaterThanEqualAndSubject() {
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

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11a", "testMessage", session1);
        Log log3 = new Log(10, "asw11a", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualAndSubjectOrderByTimeDesc(5, "PES");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log2);
    }

    @Test
    public void findByTimeGreaterThanEqualAndSubjectAndMessageContaining() {
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

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11a", "testMessage", session1);
        Log log3 = new Log(15, "pes11a", "testMessage2", session1);
        Log log4 = new Log(10, "asw11a", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);
        entityManager.persistAndFlush(log4);

        Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(5, "PES", "testMessage2");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log3);
    }

    @Test
    public void findByTimeGreaterThanEqualAndTeam() {
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

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11a", "testMessage", session1);
        Log log3 = new Log(10, "asw11a", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualAndTeamOrderByTimeDesc(5, "pes11a");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log2);
    }

    @Test
    public void findByTimeGreaterThanEqualAndTeamAndMessageContaining() {
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

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11a", "testMessage", session1);
        Log log3 = new Log(15, "pes11a", "testMessage2", session1);
        Log log4 = new Log(10, "asw11a", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);
        entityManager.persistAndFlush(log4);

        Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualAndTeamAndMessageContainingOrderByTimeDesc(5, "pes11a", "testMessage2");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log3);
    }

    @Test
    void findByTimeLessThanEqual() {
        Subject subj = new Subject("PES");
        Team team = new Team("pes11a", "sem", subj);
        Session session = new Session("s", team, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team);
        entityManager.persistAndFlush(session);

        Log log1 = new Log(0, "pes11a", "testMessage", session);
        Log log2 = new Log(10, "pes11a", "testMessage", session);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualOrderByTimeDesc(5);
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log1);
    }

    @Test
    public void findByTimeLessThanEqualAndMessageContaining() {
        Subject subj = new Subject("PES");
        Team team = new Team("pes11a", "sem", subj);
        Session session = new Session("s", team, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team);
        entityManager.persistAndFlush(session);

        Log log1 = new Log(0, "pes11a", "testMessage2", session);
        Log log2 = new Log(2, "pes11a", "testMessage", session);
        Log log3 = new Log(10, "pes11a", "testMessage", session);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualAndMessageContainingOrderByTimeDesc(5, "testMessage2");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log1);
    }

    @Test
    public void findByTimeLessThanEqualAndSubject() {
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

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11a", "testMessage", session1);
        Log log3 = new Log(2, "asw11a", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualAndSubjectOrderByTimeDesc(5, "PES");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log1);
    }

    @Test
    public void findByTimeLessThanEqualAndSubjectAndMessageContaining() {
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

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11a", "testMessage", session1);
        Log log3 = new Log(2, "pes11a", "testMessage2", session1);
        Log log4 = new Log(10, "asw11a", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);
        entityManager.persistAndFlush(log4);

        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(5, "PES", "testMessage2");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log3);
    }

    @Test
    public void findByTimeLessThanEqualAndTeam() {
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

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11a", "testMessage", session1);
        Log log3 = new Log(10, "asw11a", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualAndTeamOrderByTimeDesc(5, "pes11a");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log1);
    }

    @Test
    public void findByTimeLessThanEqualAndTeamAndMessageContaining() {
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

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(2, "pes11a", "testMessage2", session1);
        Log log3 = new Log(15, "pes11a", "testMessage2", session1);
        Log log4 = new Log(10, "asw11a", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);
        entityManager.persistAndFlush(log4);

        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualAndTeamAndMessageContainingOrderByTimeDesc(5, "pes11a", "testMessage2");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log2);
    }

    @Test
    void findByTimeBetween() {
        Subject subj = new Subject("PES");
        Team team = new Team("pes11a", "sem", subj);
        Session session = new Session("s", team, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team);
        entityManager.persistAndFlush(session);

        Log log1 = new Log(0, "pes11a", "testMessage", session);
        Log log2 = new Log(10, "pes11a", "testMessage", session);
        Log log3 = new Log(7, "pes11a", "testMessage", session);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTimeBetweenOrderByTimeDesc(5, 10);
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        List<Log> actualLogList = new ArrayList<>();
        actualLogList.add(log2); actualLogList.add(log3);

        assertEquals(logList, actualLogList);
    }

    @Test
    public void findByTimeBetweenAndMessageContaining() {
        Subject subj = new Subject("PES");
        Team team = new Team("pes11a", "sem", subj);
        Session session = new Session("s", team, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team);
        entityManager.persistAndFlush(session);

        Log log1 = new Log(0, "pes11a", "testMessage", session);
        Log log2 = new Log(10, "pes11a", "testMessage", session);
        Log log3 = new Log(7, "pes11a", "testMessage2", session);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTimeBetweenAndMessageContainingOrderByTimeDesc(5, 10, "testMessage2");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        List<Log> actualLogList = new ArrayList<>();
        actualLogList.add(log3);

        assertEquals(logList, actualLogList);
    }

    @Test
    public void findByTimeBetweenAndSubject() {
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

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11a", "testMessage", session1);
        Log log3 = new Log(7, "pes11a", "testMessage", session1);
        Log log4 = new Log(7, "asw11a", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);
        entityManager.persistAndFlush(log4);

        Iterable<Log> logIterable = logRepository.findByTimeBetweenAndSubjectOrderByTimeDesc(5, 10, "PES");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        List<Log> actualLogList = new ArrayList<>();
        actualLogList.add(log2); actualLogList.add(log3);

        assertEquals(logList, actualLogList);
    }

    @Test
    public void findByTimeBetweenAndSubjectAndMessageContaining() {
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

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11a", "testMessage", session1);
        Log log3 = new Log(7, "pes11a", "testMessage2", session1);
        Log log4 = new Log(7, "asw11a", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);
        entityManager.persistAndFlush(log4);

        Iterable<Log> logIterable = logRepository.findByTimeBetweenAndSubjectAndMessageContainingOrderByTimeDesc(5, 10, "PES", "testMessage2");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        List<Log> actualLogList = new ArrayList<>();
        actualLogList.add(log3);

        assertEquals(logList, actualLogList);
    }

    @Test
    public void findByTimeBetweenAndTeam() {
        Subject subj = new Subject("PES");
        Team team1 = new Team("pes11a", "sem", subj);
        Team team2 = new Team("pes11b", "sem", subj);
        Session session1 = new Session("s1", team1, 0);
        Session session2 = new Session("s2", team2, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11a", "testMessage2", session1);
        Log log3 = new Log(7, "pes11b", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTimeBetweenAndTeamOrderByTimeDesc(5, 10, "pes11a");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        List<Log> actualLogList = new ArrayList<>();
        actualLogList.add(log2);

        assertEquals(logList, actualLogList);
    }

    @Test
    public void findByTimeBetweenAndTeamAndMessageContaining() {
        Subject subj = new Subject("PES");
        Team team1 = new Team("pes11a", "sem", subj);
        Team team2 = new Team("pes11b", "sem", subj);
        Session session1 = new Session("s1", team1, 0);
        Session session2 = new Session("s2", team2, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);

        Log log1 = new Log(7, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11a", "testMessage2", session1);
        Log log3 = new Log(7, "pes11b", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTimeBetweenAndTeamAndMessageContainingOrderByTimeDesc(5, 10, "pes11a", "testMessage2");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        List<Log> actualLogList = new ArrayList<>();
        actualLogList.add(log2);

        assertEquals(logList, actualLogList);
    }

    @Test
    void findByTeam() {
        Subject subj = new Subject("PES");
        Team team1 = new Team("pes11a", "sem", subj);
        Team team2 = new Team("pes11b", "sem", subj);
        Session session1 = new Session("s1", team1, 0);
        Session session2 = new Session("s2", team2, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(0, "pes11b", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Iterable<Log> logIterable = logRepository.findByTeamOrderByTimeDesc("pes11a");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log1);
    }

    @Test
    public void findByTeamAndMessageContaining() {
        Subject subj = new Subject("PES");
        Team team1 = new Team("pes11a", "sem", subj);
        Team team2 = new Team("pes11b", "sem", subj);
        Session session1 = new Session("s1", team1, 0);
        Session session2 = new Session("s2", team2, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(0, "pes11b", "testMessage", session2);
        Log log3 = new Log(10, "pes11b", "testMessage2", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findByTeamAndMessageContainingOrderByTimeDesc("pes11b", "testMessage2");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log3);
    }

    @Test
    void findFirstByOrderByTimeDesc() {
        Subject subj = new Subject("PES");
        Team team1 = new Team("pes11a", "sem", subj);
        Team team2 = new Team("pes11b", "sem", subj);
        Session session1 = new Session("s1", team1, 0);
        Session session2 = new Session("s2", team2, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);

        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(10, "pes11b", "testMessage", session2);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Log log = logRepository.findFirstByOrderByTimeDesc();
        assertEquals(log, log2);
    }

    @Test
    void findByMessageContaining() {
        Subject subj = new Subject("PES");
        Team team = new Team("pes11a", "sem", subj);
        Session session = new Session("s", team, 0);
        entityManager.persistAndFlush(subj);
        entityManager.persistAndFlush(team);
        entityManager.persistAndFlush(session);

        Log log1 = new Log(0, "pes11a", "testMessage1", session);
        Log log2 = new Log(10, "pes11a", "testMessage2", session);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);

        Iterable<Log> logIterable = logRepository.findByMessageContainingOrderByTimeDesc("testMessage2");
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
        Session session1 = new Session("s1", team1, 0);
        Session session2 = new Session("s2", team2, 0);
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

        Iterable<Log> logIterable = logRepository.findBySubjectOrderByTimeDesc("PES");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log1);
    }

    @Test
    public void findBySubjectAndMessageContaining() {
        Subject subject1 = new Subject("PES");
        Subject subject2 = new Subject("ASW");
        Team team1 = new Team("pes11a", "sem", subject1);
        Team team2 = new Team("asw11a", "sem", subject2);
        Team team3 = new Team("asw11b", "sem", subject2);
        Session session1 = new Session("s1", team1, 0);
        Session session2 = new Session("s2", team2, 0);
        Session session3 = new Session("s3", team3, 0);
        Log log1 = new Log(0, "pes11a", "testMessage", session1);
        Log log2 = new Log(0, "asw11a", "testMessage", session2);
        Log log3 = new Log(0, "asw11b", "testMessage2", session3);

        entityManager.persistAndFlush(subject1);
        entityManager.persistAndFlush(subject2);
        entityManager.persistAndFlush(team1);
        entityManager.persistAndFlush(team2);
        entityManager.persistAndFlush(team3);
        entityManager.persistAndFlush(session1);
        entityManager.persistAndFlush(session2);
        entityManager.persistAndFlush(session3);
        entityManager.persistAndFlush(log1);
        entityManager.persistAndFlush(log2);
        entityManager.persistAndFlush(log3);

        Iterable<Log> logIterable = logRepository.findBySubjectAndMessageContainingOrderByTimeDesc("ASW", "testMessage");
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        assertEquals(logList.get(0), log2);
    }
}