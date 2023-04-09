package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.LogController;
import com.upc.gessi.loganalytics.app.domain.models.Log;
import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.LogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogRestControllerTest {

    @Mock
    LogRepository logRepository;
    @Mock
    LogController logController;

    @InjectMocks
    LogRestController logRestController;

    @Test
    void findAllLogs() {
        List<Log> logs = new ArrayList<>();
        logs.add(new Log(0, "pes11a", "testMessage"));
        logs.add(new Log(5, "pes11a", "testMessage"));
        when(logRepository.findAllByOrderByTimeDesc()).thenReturn(logs);
        List<Log> actualLogs = logRestController.findAllLogs(null, null);
        assertEquals(logs, actualLogs);
    }

    @Test
    void findLogsBetweenDates() {
        List<Log> logs = new ArrayList<>();
        logs.add(new Log(82800000L, "pes11a", "testMessage"));
        logs.add(new Log(259200000L, "pes11a", "testMessage"));
        when(logRepository.findByTimeBetweenOrderByTimeDesc(Mockito.anyLong(), Mockito.anyLong())).thenReturn(logs);
        List<Log> actualLogs = logRestController.findLogsBetweenDates("1970-01-02", "1970-01-04", null, null);
        assertEquals(logs, actualLogs);
    }

    @Test
    void findLogsByTeam() {
        List<Log> logs = new ArrayList<>();
        logs.add(new Log(0, "pes11a", "testMessage"));
        logs.add(new Log(10, "pes11a", "testMessage"));
        when(logRepository.findByTeamOrderByTimeDesc("pes11a")).thenReturn(logs);
        List<Log> actualLogs = logRestController.findLogsByTeam("pes11a", null, null);
        assertEquals(logs, actualLogs);
    }

    @Test
    void findLogsBySubject() {
        List<Log> logs = new ArrayList<>();
        logs.add(new Log(0, "pes11a", "testMessage1"));
        logs.add(new Log(10, "pes11a", "testMessage2"));
        when(logRepository.findBySubjectOrderByTimeDesc("PES")).thenReturn(logs);
        List<Log> actualLogs = logRestController.findLogsBySubject("PES", null, null);
        assertEquals(logs, actualLogs);
    }

    @Test
    void findLogsByKeyword() {
        List<Log> logs = new ArrayList<>();
        logs.add(new Log(0, "pes11a", "testMessage1"));
        logs.add(new Log(10, "pes11a", "testMessage2"));
        when(logRepository.findByMessageContainingOrderByTimeDesc("Message")).thenReturn(logs);
        List<Log> actualLogs = logRestController.findLogsByKeyword("Message", null, null);
        assertEquals(logs, actualLogs);
    }

    @Test
    void importLogs() {
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.log",
            MediaType.TEXT_PLAIN_VALUE,
            "log1\nlog2\nlog3".getBytes()
        );
        logRestController.importLogs(file);
        verify(logController, Mockito.times(1)).getOriginalLogs(file);
        verify(logController, Mockito.times(1)).parseLogs(Mockito.any());
    }
}