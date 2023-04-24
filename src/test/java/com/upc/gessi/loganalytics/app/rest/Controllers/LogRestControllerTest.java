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
        when(logRepository.findAllByOrderByTimeDesc()).thenReturn(logs);
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
            logRestController.findAllLogs(null, null,
                null, null, "pes11a", "PES", null);
        } catch (ResponseStatusException e) {
            assertEquals(e.getMessage(),
                "400 BAD_REQUEST \"Cannot filter by team and subject simultaneously\"");
        }


        logRestController.findAllLogs(null, null,
            "2001-07-22", "2001-10-20", "Team", null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeBetweenAndTeamAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
            Mockito.anyLong(), Mockito.eq("Team"), Mockito.eq("Hello"));

        logRestController.findAllLogs(null, null,
            "2001-07-22", "2001-10-20", "Team", null, null);
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeBetweenAndTeamOrderByTimeDesc(Mockito.anyLong(),
            Mockito.anyLong(), Mockito.eq("Team"));

        logRestController.findAllLogs(null, null,
            "2001-07-22", "2001-10-20", null, "Subject", "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeBetweenAndSubjectAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
            Mockito.anyLong(), Mockito.eq("Subject"), Mockito.eq("Hello"));

        logRestController.findAllLogs(null, null,
            "2001-07-22", "2001-10-20", null, "Subject", null);
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeBetweenAndSubjectOrderByTimeDesc(Mockito.anyLong(),
            Mockito.anyLong(), Mockito.eq("Subject"));

        logRestController.findAllLogs(null, null,
            "2001-07-22", "2001-10-20", null, null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeBetweenAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
            Mockito.anyLong(), Mockito.eq("Hello"));

        logRestController.findAllLogs(null, null,
            "2001-07-22", "2001-10-20", null, null, null);
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeBetweenOrderByTimeDesc(Mockito.anyLong(),
            Mockito.anyLong());


        logRestController.findAllLogs(null, null,
            "2001-07-22", null, "Team", null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeGreaterThanEqualAndTeamAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
            Mockito.eq("Team"), Mockito.eq("Hello"));

        logRestController.findAllLogs(null, null,
            "2001-07-22", null, "Team", null, null);
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeGreaterThanEqualAndTeamOrderByTimeDesc(Mockito.anyLong(),
            Mockito.eq("Team"));

        logRestController.findAllLogs(null, null,
            "2001-07-22", null, null, "Subject", "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeGreaterThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
            Mockito.eq("Subject"), Mockito.eq("Hello"));

        logRestController.findAllLogs(null, null,
            "2001-07-22", null, null, "Subject", null);
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeGreaterThanEqualAndSubjectOrderByTimeDesc(Mockito.anyLong(),
            Mockito.eq("Subject"));

        logRestController.findAllLogs(null, null,
            "2001-07-22", null, null, null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeGreaterThanEqualAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
            Mockito.eq("Hello"));

        logRestController.findAllLogs(null, null,
            "2001-07-22", null, null, null, null);
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeGreaterThanEqualOrderByTimeDesc(Mockito.anyLong());


        logRestController.findAllLogs(null, null,
            null, "2001-07-22", "Team", null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeLessThanEqualAndTeamAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
            Mockito.eq("Team"), Mockito.eq("Hello"));

        logRestController.findAllLogs(null, null,
            null, "2001-07-22", "Team", null, null);
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeLessThanEqualAndTeamOrderByTimeDesc(Mockito.anyLong(),
            Mockito.eq("Team"));

        logRestController.findAllLogs(null, null,
            null, "2001-07-22", null, "Subject", "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeLessThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
            Mockito.eq("Subject"), Mockito.eq("Hello"));

        logRestController.findAllLogs(null, null,
            null, "2001-07-22", null, "Subject", null);
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeLessThanEqualAndSubjectOrderByTimeDesc(Mockito.anyLong(),
            Mockito.eq("Subject"));

        logRestController.findAllLogs(null, null,
            null, "2001-07-22", null, null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeLessThanEqualAndMessageContainingOrderByTimeDesc(Mockito.anyLong(),
            Mockito.eq("Hello"));

        logRestController.findAllLogs(null, null,
            null, "2001-07-22", null, null, null);
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTimeLessThanEqualOrderByTimeDesc(Mockito.anyLong());


        logRestController.findAllLogs(null, null,
            null, null, "Team", null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTeamAndMessageContainingOrderByTimeDesc(
            Mockito.eq("Team"), Mockito.eq("Hello"));

        logRestController.findAllLogs(null, null,
            null, null, "Team", null, null);
        Mockito.verify(logRepository, Mockito.times(1))
            .findByTeamOrderByTimeDesc(Mockito.eq("Team"));

        logRestController.findAllLogs(null, null,
            null, null, null, "Subject", "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findBySubjectAndMessageContainingOrderByTimeDesc(
            Mockito.eq("Subject"), Mockito.eq("Hello"));

        logRestController.findAllLogs(null, null,
            null, null, null, "Subject", null);
        Mockito.verify(logRepository, Mockito.times(1))
            .findBySubjectOrderByTimeDesc(Mockito.eq("Subject"));

        logRestController.findAllLogs(null, null,
            null, null, null, null, "Hello");
        Mockito.verify(logRepository, Mockito.times(1))
            .findByMessageContainingOrderByTimeDesc(Mockito.eq("Hello"));
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