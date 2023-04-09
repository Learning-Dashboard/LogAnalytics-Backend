package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.LogController;
import com.upc.gessi.loganalytics.app.domain.models.Log;
import com.upc.gessi.loganalytics.app.domain.repositories.LogRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
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

    @GetMapping
    public List<Log> findAllLogs(
        @RequestParam (name = "page", required = false) Integer page,
        @RequestParam (name = "size", required = false) Integer size) {

        if (page == null)
            return logRepository.findAllByOrderByTimeDesc();
        else {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Log> logIterable = logRepository.findAllByOrderByTimeDesc(pageable);
            return logIterable.getContent();
        }
    }

    @GetMapping("/byDates")
    @ResponseStatus(HttpStatus.OK)
    public List<Log> findLogsBetweenDates(
        @RequestParam (name = "dateBefore", required = false) String dateBefore,
        @RequestParam (name = "dateAfter", required = false) String dateAfter,
        @RequestParam (name = "page", required = false) Integer page,
        @RequestParam (name = "size", required = false) Integer size) {

        if (dateBefore != null) {
            if (dateAfter != null) {
                try {
                    Date dBefore = new SimpleDateFormat("yyyy-MM-dd").parse(dateBefore);
                    Date dAfter = new SimpleDateFormat("yyyy-MM-dd").parse(dateAfter);
                    long epochBefore = dBefore.getTime();
                    long epochAfter = dAfter.getTime() + 86399999;
                    if (epochBefore > epochAfter)
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "dateBefore is not previous to dateAfter");
                    if (page == null) {
                        Iterable<Log> logIterable = logRepository.findByTimeBetweenOrderByTimeDesc(epochBefore, epochAfter);
                        List<Log> logList = new ArrayList<>();
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else {
                        Pageable pageable = PageRequest.of(page - 1, size);
                        Page<Log> logPage = logRepository.findByTimeBetweenOrderByTimeDesc(epochBefore, epochAfter, pageable);
                        return logPage.getContent();
                    }
                } catch (ParseException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                }
            }
            else {
                try {
                    Date dBefore = new SimpleDateFormat("yyyy-MM-dd").parse(dateBefore);
                    long epoch = dBefore.getTime();
                    if (page == null) {
                        Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualOrderByTimeDesc(epoch);
                        List<Log> logList = new ArrayList<>();
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else {
                        Pageable pageable = PageRequest.of(page - 1, size);
                        Page<Log> logPage = logRepository.findByTimeGreaterThanEqualOrderByTimeDesc(epoch, pageable);
                        return logPage.getContent();
                    }
                } catch (ParseException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                }
            }
        }
        else if (dateAfter != null) {
            try {
                Date dAfter = new SimpleDateFormat("yyyy-MM-dd").parse(dateAfter);
                long epoch = dAfter.getTime();
                if (page == null) {
                    Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualOrderByTimeDesc(epoch);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
                else {
                    Pageable pageable = PageRequest.of(page - 1, size);
                    Page<Log> logPage = logRepository.findByTimeLessThanEqualOrderByTimeDesc(epoch, pageable);
                    return logPage.getContent();
                }
            } catch (ParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one date needs to be provided");
        }
    }

    @GetMapping("/byTeam")
    @ResponseStatus(HttpStatus.OK)
    public List<Log> findLogsByTeam(
        @RequestParam (name = "team") String team,
        @RequestParam (name = "page", required = false) Integer page,
        @RequestParam (name = "size", required = false) Integer size) {

        if (page == null) {
            Iterable<Log> logIterable = logRepository.findByTeamOrderByTimeDesc(team);
            List<Log> logList = new ArrayList<>();
            logIterable.forEach(logList::add);
            return logList;
        }
        else {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Log> logPage = logRepository.findByTeamOrderByTimeDesc(team, pageable);
            return logPage.getContent();
        }
    }

    @GetMapping("/bySubject")
    @ResponseStatus(HttpStatus.OK)
    public List<Log> findLogsBySubject(
        @RequestParam (name = "subject") String subject,
        @RequestParam (name = "page", required = false) Integer page,
        @RequestParam (name = "size", required = false) Integer size) {

        if (page == null) {
            Iterable<Log> logIterable = logRepository.findBySubjectOrderByTimeDesc(subject);
            List<Log> logList = new ArrayList<>();
            logIterable.forEach(logList::add);
            return logList;
        }
        else {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Log> logPage = logRepository.findBySubjectOrderByTimeDesc(subject, pageable);
            return logPage.getContent();
        }
    }

    @GetMapping("/byKeyword")
    @ResponseStatus(HttpStatus.OK)
    public List<Log> findLogsByKeyword(
        @RequestParam (name = "keyword") String keyword,
        @RequestParam (name = "page", required = false) Integer page,
        @RequestParam (name = "size", required = false) Integer size) {

        if (page == null) {
            Iterable<Log> logIterable = logRepository.findByMessageContainingOrderByTimeDesc(keyword);
            List<Log> logList = new ArrayList<>();
            logIterable.forEach(logList::add);
            return logList;
        }
        else {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Log> logPage = logRepository.findByMessageContainingOrderByTimeDesc(keyword, pageable);
            return logPage.getContent();
        }
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void importLogs(
        @RequestParam (name = "file") MultipartFile file) {

        List<String> originalLogs = logController.getOriginalLogs(file);
        List<Log> parsedLogs = logController.parseLogs(originalLogs);
        if (!parsedLogs.isEmpty()) {
            Log logFile = parsedLogs.get(0);
            Log logDB = logRepository.findFirstByOrderByTimeDesc();
            if (logDB != null) {
                if (logFile.getTime() > logDB.getTime()) {
                    //logRepository.saveAll(parsedLogs);
                    for (Log l : parsedLogs) {
                        try {
                            logRepository.save(l);
                        }
                        catch (JpaObjectRetrievalFailureException ignored) {
                        }
                    }
                }
            }
            else {
                //logRepository.saveAll(parsedLogs);
                for (Log l : parsedLogs) {
                    try {
                        logRepository.save(l);
                    }
                    catch (JpaObjectRetrievalFailureException ignored) { }
                }
            }
        }
    }
}
