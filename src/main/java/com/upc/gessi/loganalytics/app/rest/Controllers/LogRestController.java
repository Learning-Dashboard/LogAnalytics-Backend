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
        @RequestParam (name = "size", required = false) Integer size,
        @RequestParam (name = "dateBefore", required = false) String dateBefore,
        @RequestParam (name = "dateAfter", required = false) String dateAfter,
        @RequestParam (name = "team", required = false) String team,
        @RequestParam (name = "subject", required = false) String subject,
        @RequestParam (name = "keyword", required = false) String keyword) {

        if (team != null && subject != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot filter by team and subject simultaneously");
        }

        if (page != null && size == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Size needed for pagination");
        }

        if (page == null && size != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Page needed for pagination");
        }

        if (page == null) {
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
                        if (team != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeBetweenAndTeamAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, team, keyword);
                            }
                            else {
                                logIterable = logRepository.findByTimeBetweenAndTeamOrderByTimeDesc(epochBefore, epochAfter, team);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (subject != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeBetweenAndSubjectAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, subject, keyword);
                            }
                            else {
                                logIterable = logRepository.findByTimeBetweenAndSubjectOrderByTimeDesc(epochBefore, epochAfter, subject);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (keyword != null) {
                            Iterable<Log> logIterable = logRepository.findByTimeBetweenAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, keyword);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else {
                            Iterable<Log> logIterable = logRepository.findByTimeBetweenOrderByTimeDesc(epochBefore, epochAfter);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                    } catch (ParseException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                    }
                }
                else {
                    try {
                        Date dBefore = new SimpleDateFormat("yyyy-MM-dd").parse(dateBefore);
                        long epoch = dBefore.getTime();
                        if (team != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndTeamAndMessageContainingOrderByTimeDesc(epoch, team, keyword);
                            }
                            else {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndTeamOrderByTimeDesc(epoch, team);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (subject != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(epoch, subject, keyword);
                            }
                            else {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndSubjectOrderByTimeDesc(epoch, subject);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (keyword != null) {
                            Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualAndMessageContainingOrderByTimeDesc(epoch, keyword);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else {
                            Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualOrderByTimeDesc(epoch);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                    } catch (ParseException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                    }
                }
            }
            else if (dateAfter != null) {
                try {
                    Date dAfter = new SimpleDateFormat("yyyy-MM-dd").parse(dateAfter);
                    long epoch = dAfter.getTime() + 86399999;

                    if (team != null) {
                        Iterable<Log> logIterable;
                        List<Log> logList = new ArrayList<>();
                        if (keyword != null) {
                            logIterable = logRepository.findByTimeLessThanEqualAndTeamAndMessageContainingOrderByTimeDesc(epoch, team, keyword);
                        }
                        else {
                            logIterable = logRepository.findByTimeLessThanEqualAndTeamOrderByTimeDesc(epoch, team);
                        }
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else if (subject != null) {
                        Iterable<Log> logIterable;
                        List<Log> logList = new ArrayList<>();
                        if (keyword != null) {
                            logIterable = logRepository.findByTimeLessThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(epoch, subject, keyword);
                        }
                        else {
                            logIterable = logRepository.findByTimeLessThanEqualAndSubjectOrderByTimeDesc(epoch, subject);
                        }
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else if (keyword != null) {
                        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualAndMessageContainingOrderByTimeDesc(epoch, keyword);
                        List<Log> logList = new ArrayList<>();
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else {
                        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualOrderByTimeDesc(epoch);
                        List<Log> logList = new ArrayList<>();
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                } catch (ParseException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                }
            }
            else if (team != null) {
                if (keyword != null) {
                    Iterable<Log> logIterable = logRepository.findByTeamAndMessageContainingOrderByTimeDesc(team, keyword);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
                else {
                    Iterable<Log> logIterable = logRepository.findByTeamOrderByTimeDesc(team);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
            }
            else if (subject != null) {
                if (keyword != null) {
                    Iterable<Log> logIterable = logRepository.findBySubjectAndMessageContainingOrderByTimeDesc(subject, keyword);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
                else  {
                    Iterable<Log> logIterable = logRepository.findBySubjectOrderByTimeDesc(subject);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
            }
            else if (keyword != null) {
                Iterable<Log> logIterable = logRepository.findByMessageContainingOrderByTimeDesc(keyword);
                List<Log> logList = new ArrayList<>();
                logIterable.forEach(logList::add);
                return logList;
            }
            else {
                Iterable<Log> logIterable = logRepository.findAllByOrderByTimeDesc();
                List<Log> logList = new ArrayList<>();
                logIterable.forEach(logList::add);
                return logList;
            }
        }
        else {
            Pageable pageable = PageRequest.of(page - 1, size);
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
                        if (team != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeBetweenAndTeamAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, team, keyword, pageable);
                            }
                            else {
                                logIterable = logRepository.findByTimeBetweenAndTeamOrderByTimeDesc(epochBefore, epochAfter, team, pageable);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (subject != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeBetweenAndSubjectAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, subject, keyword, pageable);
                            }
                            else {
                                logIterable = logRepository.findByTimeBetweenAndSubjectOrderByTimeDesc(epochBefore, epochAfter, subject, pageable);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (keyword != null) {
                            Iterable<Log> logIterable = logRepository.findByTimeBetweenAndMessageContainingOrderByTimeDesc(epochBefore, epochAfter, keyword, pageable);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else {
                            Iterable<Log> logIterable = logRepository.findByTimeBetweenOrderByTimeDesc(epochBefore, epochAfter, pageable);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                    } catch (ParseException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                    }
                }
                else {
                    try {
                        Date dBefore = new SimpleDateFormat("yyyy-MM-dd").parse(dateBefore);
                        long epoch = dBefore.getTime();
                        if (team != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndTeamAndMessageContainingOrderByTimeDesc(epoch, team, keyword, pageable);
                            }
                            else {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndTeamOrderByTimeDesc(epoch, team, pageable);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (subject != null) {
                            Iterable<Log> logIterable;
                            List<Log> logList = new ArrayList<>();
                            if (keyword != null) {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(epoch, subject, keyword, pageable);
                            }
                            else {
                                logIterable = logRepository.findByTimeGreaterThanEqualAndSubjectOrderByTimeDesc(epoch, subject, pageable);
                            }
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else if (keyword != null) {
                            Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualAndMessageContainingOrderByTimeDesc(epoch, keyword, pageable);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
                        }
                        else {
                            Iterable<Log> logIterable = logRepository.findByTimeGreaterThanEqualOrderByTimeDesc(epoch, pageable);
                            List<Log> logList = new ArrayList<>();
                            logIterable.forEach(logList::add);
                            return logList;
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

                    if (team != null) {
                        Iterable<Log> logIterable;
                        List<Log> logList = new ArrayList<>();
                        if (keyword != null) {
                            logIterable = logRepository.findByTimeLessThanEqualAndTeamAndMessageContainingOrderByTimeDesc(epoch, team, keyword, pageable);
                        }
                        else {
                            logIterable = logRepository.findByTimeLessThanEqualAndTeamOrderByTimeDesc(epoch, team, pageable);
                        }
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else if (subject != null) {
                        Iterable<Log> logIterable;
                        List<Log> logList = new ArrayList<>();
                        if (keyword != null) {
                            logIterable = logRepository.findByTimeLessThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(epoch, subject, keyword, pageable);
                        }
                        else {
                            logIterable = logRepository.findByTimeLessThanEqualAndSubjectOrderByTimeDesc(epoch, subject, pageable);
                        }
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else if (keyword != null) {
                        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualAndMessageContainingOrderByTimeDesc(epoch, keyword, pageable);
                        List<Log> logList = new ArrayList<>();
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                    else {
                        Iterable<Log> logIterable = logRepository.findByTimeLessThanEqualOrderByTimeDesc(epoch, pageable);
                        List<Log> logList = new ArrayList<>();
                        logIterable.forEach(logList::add);
                        return logList;
                    }
                } catch (ParseException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date formats are incorrect");
                }
            }
            else if (team != null) {
                if (keyword != null) {
                    Iterable<Log> logIterable = logRepository.findByTeamAndMessageContainingOrderByTimeDesc(team, keyword, pageable);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
                else {
                    Iterable<Log> logIterable = logRepository.findByTeamOrderByTimeDesc(team, pageable);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
            }
            else if (subject != null) {
                if (keyword != null) {
                    Iterable<Log> logIterable = logRepository.findBySubjectAndMessageContainingOrderByTimeDesc(subject, keyword, pageable);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
                else  {
                    Iterable<Log> logIterable = logRepository.findBySubjectOrderByTimeDesc(subject, pageable);
                    List<Log> logList = new ArrayList<>();
                    logIterable.forEach(logList::add);
                    return logList;
                }
            }
            else if (keyword != null) {
                Iterable<Log> logIterable = logRepository.findByMessageContainingOrderByTimeDesc(keyword, pageable);
                List<Log> logList = new ArrayList<>();
                logIterable.forEach(logList::add);
                return logList;
            }
            else {
                Iterable<Log> logIterable = logRepository.findAllByOrderByTimeDesc(pageable);
                List<Log> logList = new ArrayList<>();
                logIterable.forEach(logList::add);
                return logList;
            }
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
                if (logFile.getTime() > logDB.getTime())
                    logRepository.saveAll(parsedLogs);
            }
            else logRepository.saveAll(parsedLogs);
        }
    }
}
