package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.LogController;
import com.upc.gessi.loganalytics.app.domain.models.Log;
import com.upc.gessi.loganalytics.app.domain.repositories.LogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
        when(logController.getAll(null, null,
            null, null, null, null, null)).thenReturn(logs);
        List<Log> actualLogs = logRestController.findAllLogs(null, null,
            null, null, null, null, null);
        assertEquals(logs, actualLogs);


        try {
            logRestController.findAllLogs(1, null,
                null, null, null, null, null);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"Size needed for pagination\"");
        }
        try {
            logRestController.findAllLogs(null, 1,
                null, null, null, null, null);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"Page needed for pagination\"");
        }
        try {
            logRestController.findAllLogs(-1, 1,
                null, null, null, null, null);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"Page value must be greater than 0\"");
        }
        try {
            logRestController.findAllLogs(1, -1,
                null, null, null, null, null);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(), "400 BAD_REQUEST \"Size value must be greater than 0\"");
        }
        try {
            logRestController.findAllLogs(null, null,
                null, null, "pes11a", "PES", null);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(),
                "400 BAD_REQUEST \"Cannot filter by team and subject simultaneously\"");
        }
        try {
            logRestController.findAllLogs(null, null,
                "2001-07-22", "2001-07-20", null, null, null);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(),
                "400 BAD_REQUEST \"dateBefore is not previous to dateAfter\"");
        }
        try {
            logRestController.findAllLogs(null, null,
                "22-07-2001", null, null, null, null);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(),
                "400 BAD_REQUEST \"Date formats are incorrect\"");
        }
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
        verify(logController, Mockito.times(1)).storeLogs(Mockito.any());
    }
}