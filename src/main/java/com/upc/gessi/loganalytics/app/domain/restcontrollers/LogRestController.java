package com.upc.gessi.loganalytics.app.domain.restcontrollers;

import com.upc.gessi.loganalytics.app.domain.controllers.LogController;
import com.upc.gessi.loganalytics.app.domain.models.Log;
import com.upc.gessi.loganalytics.app.domain.repositories.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/logs")
public class LogRestController {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private LogController logController;

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    @GetMapping
    public List<Log> findAllLogs() {
        Iterable<Log> logIterable = logRepository.findAll();
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        return logList;
    }

    @GetMapping("/byDates")
    @ResponseStatus(HttpStatus.OK)
    public List<Log> findLogsBetweenDates(
        @RequestParam (name = "dateBefore", required = false) String dateBefore,
        @RequestParam (name = "dateAfter", required = false) String dateAfter) {

        if (dateBefore != null) {
            if (dateAfter != null) {
                try {
                    Date dBefore = new SimpleDateFormat("yyyy-MM-dd").parse(dateBefore);
                    Date dAfter = new SimpleDateFormat("yyyy-MM-dd").parse(dateAfter);
                    long epochBefore = dBefore.getTime();
                    long epochAfter = dAfter.getTime();
                    Iterable<Log> logIterable = logRepository.findByTimeBetween(epochBefore, epochAfter);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                } catch (ParseException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            }
            else {
                try {
                    Date dBefore = new SimpleDateFormat("yyyy-MM-dd").parse(dateBefore);
                    long epoch = dBefore.getTime();
                    Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqual(epoch);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                } catch (ParseException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
            }
        }
        else if (dateAfter != null) {
            try {
                Date dAfter = new SimpleDateFormat("yyyy-MM-dd").parse(dateAfter);
                long epoch = dAfter.getTime();
                Iterable<Log> logIterable = logRepository.findByTimeLessThanEqual(epoch);
                List<Log> logList = new ArrayList<>();
                logIterable.forEach(logList::add);
                return logList;
            } catch (ParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/byTeam")
    @ResponseStatus(HttpStatus.OK)
    public List<Log> findLogsByTeam(
            @RequestParam (name = "team", required = true) String team) {
        Iterable<Log> logIterable = logRepository.findByTeam(team);
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        return logList;
    }

    /*
    @GetMapping("/bySubject")
    @ResponseStatus(HttpStatus.OK)
    public List<Log> findLogsBySubject(
            @RequestParam (name = "subject", required = true) String subject) {
        Iterable<Log> logIterable = logRepository.findByTeam(team);
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        return logList;
    }
     */

    @GetMapping("/byKeyword")
    @ResponseStatus(HttpStatus.OK)
    public List<Log> findLogsByKeyword(
            @RequestParam (name = "keyword", required = true) String keyword) {
        Iterable<Log> logIterable = logRepository.findByMessageContaining(keyword);
        List<Log> logList = new ArrayList<>();
        logIterable.forEach(logList::add);
        return logList;
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void importLogs(
            @RequestParam (name = "file", required = true) MultipartFile file) {
        //Get Sessions
        //Store Sessions
        List<String> originalLogs = logController.getOriginalLogs(file);
        List<Log> parsedLogs = logController.parseLogs(originalLogs);

        if (!parsedLogs.isEmpty()) {
            Log logFile = parsedLogs.get(0);
            Log logDB = logRepository.findFirstByOrderByTimeDesc();
            if (logDB != null) {
                if (logFile.getTime() > logDB.getTime()) {
                    logRepository.saveAll(parsedLogs);
                    logController.manageSessions(parsedLogs);
                }
            }
            else logRepository.saveAll(parsedLogs);
        }
    }
}
