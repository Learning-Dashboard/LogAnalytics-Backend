package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.repositories.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.HashSet;

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
}