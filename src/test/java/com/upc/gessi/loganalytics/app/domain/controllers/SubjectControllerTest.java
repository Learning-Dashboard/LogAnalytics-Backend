package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.repositories.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SubjectControllerTest {

    @Mock
    private SubjectRepository subjectRepository;
    @InjectMocks
    private SubjectController subjectController;

    @Test
    void storeSubjects() throws NoSuchMethodException {
        Method storeSubjects = SubjectController.class.getDeclaredMethod("storeSubjects", HashSet.class);
        storeSubjects.setAccessible(true);
        HashSet<String> subjectSet = new HashSet<>();
        subjectController.storeSubjects(subjectSet);
        Mockito.verify(subjectRepository, Mockito.times(1)).saveAll(Mockito.any());
    }

    @Test
    void getAll() {
        Subject s = new Subject("s");
        List<Subject> subjects = new ArrayList<>();
        subjects.add(s);
        Mockito.when(subjectRepository.findAll()).thenReturn(subjects);
        List<Subject> actualSubjects = subjectController.getAll();
        assertEquals(subjects, actualSubjects);
    }
}