package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.LogController;
import com.upc.gessi.loganalytics.app.domain.models.Log;
import com.upc.gessi.loganalytics.app.domain.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        if (page != null && page < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Page value must be greater than 0");
        }
        if (size != null && size < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Size value must be greater than 0");
        }
        return logController.getAll(page, size, dateBefore, dateAfter, team, subject, keyword);
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void importLogs(
        @RequestParam (name = "file") MultipartFile file) {
        if (file.getSize() > 20 * 1024 * 1024)
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "The file is bigger than 20MB");
        List<String> originalLogs = logController.getOriginalLogs(file);
        List<Log> parsedLogs = logController.parseLogs(originalLogs);
        logController.storeLogs(parsedLogs);
    }
}
