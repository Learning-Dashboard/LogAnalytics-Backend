package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.repositories.UserlessInternalMetricRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserlessInternalMetricControllerTest {

    @Mock
    UserlessInternalMetricRepository userlessInternalMetricRepository;

    @InjectMocks
    UserlessInternalMetricController userlessInternalMetricController;

    @Test
    void checkNoUserNameExistence() {
        Mockito.when(userlessInternalMetricRepository.existsByUserlessName("s1")).thenReturn(true);
        Mockito.when(userlessInternalMetricRepository.existsByUserlessName("s2")).thenReturn(false);
        assertTrue(userlessInternalMetricController.checkNoUserNameExistence("s1"));
        assertFalse(userlessInternalMetricController.checkNoUserNameExistence("s2"));
    }
}