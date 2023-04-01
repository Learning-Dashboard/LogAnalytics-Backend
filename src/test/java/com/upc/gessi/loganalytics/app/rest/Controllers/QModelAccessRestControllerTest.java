package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.models.QModelAccess;
import com.upc.gessi.loganalytics.app.domain.repositories.QModelAccessRepository;
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
class QModelAccessRestControllerTest {

    @Mock
    QModelAccessRepository qModelAccessRepository;

    @InjectMocks
    QModelAccessRestController qModelAccessRestController;

    @Test
    void findAllQModelAccesses() {
        List<QModelAccess> qModelAccesses = new LinkedList<>();
        qModelAccesses.add(new QModelAccess());
        qModelAccesses.add(new QModelAccess());
        when(qModelAccessRepository.findAll()).thenReturn(qModelAccesses);
        List<QModelAccess> actualQModels = qModelAccessRestController.findAllQModelAccesses();
        assertEquals(qModelAccesses, actualQModels);
    }
}