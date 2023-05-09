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
}