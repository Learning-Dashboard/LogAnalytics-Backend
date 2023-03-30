package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashSet;

@Controller
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    public void storeSubjects(HashSet<String> acronyms) {
        HashSet<Subject> subjectSet = new HashSet<>();
        for (String s : acronyms) {
            Subject sub = new Subject(s);
            subjectSet.add(sub);
        }
        subjectRepository.saveAll(subjectSet);
    }
}
