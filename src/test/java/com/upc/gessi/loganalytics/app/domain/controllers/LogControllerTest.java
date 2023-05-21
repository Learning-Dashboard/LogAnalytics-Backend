package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.LogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class LogControllerTest {

    @Mock
    SessionController sessionController;
    @Mock
    LogRepository logRepository;

    @InjectMocks
    LogController logController;

    @Test
    void getOriginalLogs() {
        String fileContent = "Line 1\nLine 2\nLine 3";
        MockMultipartFile mockFile =
                new MockMultipartFile("file", "test.txt",
                        "text/plain", fileContent.getBytes(StandardCharsets.UTF_8));
        List<String> expectedList = Arrays.asList("Line 1", "Line 2", "Line 3");
        LogController logController = new LogController();
        List<String> result = logController.getOriginalLogs(mockFile);
        assertEquals(expectedList, result);
    }

    @Test
    void parseLogs() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        when(sessionController.createSession(anyString(), anyLong(), anyString())).thenReturn(s);
        when(sessionController.updateSession(anyString(), anyLong())).thenReturn(s);
        List<String> originalLogs = Arrays.asList(
            "2022-03-30 10:30:15.000, pes11a enters app (abc)",
            "2022-03-30 10:30:50.000, pes11a exits app (abc)");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        List<Log> expectedLogs = List.of(
                new Log(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, pes11a enters app", s),
                new Log(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, pes11a exits app", s)
        );
        List<Log> actualLogs = logController.parseLogs(originalLogs);
        assertEquals(expectedLogs.size(), actualLogs.size());
        for (int i = 0; i < expectedLogs.size(); i++) {
            assertEquals(expectedLogs.get(i).getTime(), actualLogs.get(i).getTime());
            assertEquals(expectedLogs.get(i).getTeam(), actualLogs.get(i).getTeam());
            assertEquals(expectedLogs.get(i).toString(), actualLogs.get(i).toString());
        }
    }

    @Test
    void getAllByPageAndTeam() throws ParseException {
        Subject subj = new Subject("PES");
        Team t = new Team("pes11a", "s", subj);
        Session s = new Session("s", t, 1);
        List<String> originalLogs = Arrays.asList(
                "2022-03-30 10:30:15.000, pes11a enters app (abc)",
                "2022-03-30 10:30:50.000, pes11a exits app (abc)");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        List<Log> expectedLogs = List.of(
                new Log(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, pes11a enters app (abc)", s),
                new Log(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, pes11a exits app (abc)", s)
        );
        Mockito.when(logRepository.findByPageAndTeamAndTimeBetween(Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(expectedLogs);
        List<Log> actualLogs = logController.getAllByPageAndTeam("page", "pes11a");
        assertEquals(expectedLogs, actualLogs);
    }

    @Test
    void getAll() {
        List<Log> logs = new ArrayList<>();
        logs.add(new Log(0, "pes11a", "testMessage"));
        logs.add(new Log(5, "pes11a", "testMessage"));
        when(logRepository.findAllByOrderByTimeDesc()).thenReturn(logs);
        List<Log> actualLogs = logController.getAll(null, null,
                null, null, null, null, null);
        assertEquals(logs, actualLogs);

        logController.getAll(null, null,
                "2001-07-22", "2001-10-20", "Team", null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeBetweenAndTeamAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.anyLong(), Mockito.eq("Team"), Mockito.eq("Hello"));

        logController.getAll(null, null,
                "2001-07-22", "2001-10-20", "Team", null, null);
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeBetweenAndTeamOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.anyLong(), Mockito.eq("Team"));

        logController.getAll(null, null,
                "2001-07-22", "2001-10-20", null, "Subject", "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeBetweenAndSubjectAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.anyLong(), Mockito.eq("Subject"), Mockito.eq("Hello"));

        logController.getAll(null, null,
                "2001-07-22", "2001-10-20", null, "Subject", null);
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeBetweenAndSubjectOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.anyLong(), Mockito.eq("Subject"));

        logController.getAll(null, null,
                "2001-07-22", "2001-10-20", null, null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeBetweenAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.anyLong(), Mockito.eq("Hello"));

        logController.getAll(null, null,
                "2001-07-22", "2001-10-20", null, null, null);
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeBetweenOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.anyLong());


        logController.getAll(null, null,
                "2001-07-22", null, "Team", null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeGreaterThanEqualAndTeamAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.eq("Team"), Mockito.eq("Hello"));

        logController.getAll(null, null,
                "2001-07-22", null, "Team", null, null);
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeGreaterThanEqualAndTeamOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.eq("Team"));

        logController.getAll(null, null,
                "2001-07-22", null, null, "Subject", "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeGreaterThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.eq("Subject"), Mockito.eq("Hello"));

        logController.getAll(null, null,
                "2001-07-22", null, null, "Subject", null);
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeGreaterThanEqualAndSubjectOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.eq("Subject"));

        logController.getAll(null, null,
                "2001-07-22", null, null, null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeGreaterThanEqualAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.eq("Hello"));

        logController.getAll(null, null,
                "2001-07-22", null, null, null, null);
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeGreaterThanEqualOrderByTimeDesc(Mockito.anyLong());


        logController.getAll(null, null,
                null, "2001-07-22", "Team", null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeLessThanEqualAndTeamAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.eq("Team"), Mockito.eq("Hello"));

        logController.getAll(null, null,
                null, "2001-07-22", "Team", null, null);
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeLessThanEqualAndTeamOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.eq("Team"));

        logController.getAll(null, null,
                null, "2001-07-22", null, "Subject", "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeLessThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.eq("Subject"), Mockito.eq("Hello"));

        logController.getAll(null, null,
                null, "2001-07-22", null, "Subject", null);
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeLessThanEqualAndSubjectOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.eq("Subject"));

        logController.getAll(null, null,
                null, "2001-07-22", null, null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeLessThanEqualAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
                        Mockito.eq("Hello"));

        logController.getAll(null, null,
                null, "2001-07-22", null, null, null);
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTimeLessThanEqualOrderByTimeDesc(Mockito.anyLong());


        logController.getAll(null, null,
                null, null, "Team", null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTeamAndMessageContainingOrderByTimeDesc(
                        Mockito.eq("Team"), Mockito.eq("Hello"));

        logController.getAll(null, null,
                null, null, "Team", null, null);
        Mockito.verify(logRepository, Mockito.times(1))
                .findByTeamOrderByTimeDesc(Mockito.eq("Team"));

        logController.getAll(null, null,
                null, null, null, "Subject", "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findBySubjectAndMessageContainingOrderByTimeDesc(
                        Mockito.eq("Subject"), Mockito.eq("Hello"));

        logController.getAll(null, null,
                null, null, null, "Subject", null);
        Mockito.verify(logRepository, Mockito.times(1))
                .findBySubjectOrderByTimeDesc(Mockito.eq("Subject"));

        logController.getAll(null, null,
                null, null, null, null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
                .findByMessageContainingOrderByTimeDesc(Mockito.eq("Hello"));
    }

    @Test
    void storeLogs() {
        List<Log> parsedLogs = new ArrayList<>();
        parsedLogs.add(new Log(0, "pes11a", "message"));
        Mockito.when(logRepository.findFirstByOrderByTimeDesc()).thenReturn(null);
        logController.storeLogs(parsedLogs);
        Mockito.verify(logRepository, Mockito.times(1)).saveAll(parsedLogs);
    }
}