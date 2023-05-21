package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.SubjectController;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectRestController {

    @Autowired
    SubjectController subjectController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Subject> findAllSubjects() {
        return subjectController.getAll();
    }
}
