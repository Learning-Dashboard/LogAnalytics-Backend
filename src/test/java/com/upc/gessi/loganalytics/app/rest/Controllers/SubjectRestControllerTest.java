package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.SubjectController;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectRestControllerTest {

    @Mock
    SubjectController subjectController;

    @InjectMocks
    SubjectRestController subjectRestController;

    @Test
    void findAllSubjects() {
        List<Subject> subjects = new LinkedList<>();
        subjects.add(new Subject("PES"));
        subjects.add(new Subject("ASW"));
        when(subjectController.getAll()).thenReturn(subjects);
        List<Subject> actualSubjects = subjectRestController.findAllSubjects();
        assertEquals(subjects, actualSubjects);
    }
}