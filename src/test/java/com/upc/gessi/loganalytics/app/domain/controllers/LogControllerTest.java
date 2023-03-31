package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogControllerTest {

    @Mock
    SessionController sessionController;

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
        List<String> originalLogs = Arrays.asList(
            "2022-03-30 10:30:15.000, pes11a enters app",
            "2022-03-30 10:30:50.000, pes11a exits app");
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        List<Log> expectedLogs = List.of(
                new Log(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, pes11a enters app"),
                new Log(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, pes11a exits app")
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
    void manageSessions() throws ParseException {
        List<Log> parsedLogs = new ArrayList<>();
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:15.000");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2022-03-30 10:30:50.000");
        parsedLogs.add(new Log(d1.getTime(), "pes11a", "2022-03-30 10:30:15.000, pes11a enters app"));
        parsedLogs.add(new Log(d2.getTime(), "pes11a", "2022-03-30 10:30:50.000, pes11a exits app"));
        Subject s = new Subject("s");
        Team t = new Team("pes11a", "s1", s);
        when(sessionController.getSessionToStoreLog(any(Log.class)))
                .thenAnswer((Answer<Session>) invocation -> {
                    Session session = mock(Session.class);
                    when(session.getTeam()).thenReturn(t);
                    when(session.getStartTimestamp()).thenReturn(0L);
                    return session;
                });
        logController.manageSessions(parsedLogs);
        verify(sessionController, times(parsedLogs.size())).getSessionToStoreLog(any(Log.class));
        for (Log log : parsedLogs) {
            assertEquals(log.getSession().getTeam().getId(), log.getTeam());
            assertEquals(log.getSession().getStartTimestamp(), 0L);
        }
    }
}