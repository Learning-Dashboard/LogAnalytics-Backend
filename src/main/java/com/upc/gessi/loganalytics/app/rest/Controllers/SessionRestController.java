package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.repositories.SessionRepository;
import com.upc.gessi.loganalytics.app.rest.DTOs.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionRestController {

    @Autowired
    SessionRepository sessionRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SessionDTO> findAllSessions() {
        Iterable<Session> sessionIterable = sessionRepository.findAll();
        List<Session> sessionList = new ArrayList<>();
        sessionIterable.forEach(sessionList::add);
        List<SessionDTO> sessionDTOList = new ArrayList<>();
        for (Session s : sessionList) sessionDTOList.add(new SessionDTO(s));
        return sessionDTOList;
    }
}
