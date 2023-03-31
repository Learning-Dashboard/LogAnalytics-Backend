package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectRestController {

    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Subject> findAllSubjects() {
        Iterable<Subject> subjectIterable = subjectRepository.findAll();
        List<Subject> subjectList = new ArrayList<>();
        subjectIterable.forEach(subjectList::add);
        return subjectList;
    }
}
