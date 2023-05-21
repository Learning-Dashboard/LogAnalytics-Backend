package com.upc.gessi.loganalytics.app.domain.controllers;

import com.google.gson.JsonObject;
import com.upc.gessi.loganalytics.app.domain.models.Metric;
import com.upc.gessi.loganalytics.app.domain.repositories.MetricRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MetricControllerTest {

    @Mock
    MetricRepository metricRepository;

    @InjectMocks
    MetricController metricController;

    @Test
    void getAll() {
        List<Metric> metricList = new ArrayList<>();
        metricList.add(new Metric("m1", "m1"));
        metricList.add(new Metric("m2", "m2"));
        Mockito.when(metricRepository.findAll()).thenReturn(metricList);
        List<Metric> actualMetrics = metricController.getAll();
        assertEquals(metricList, actualMetrics);
    }

    @Test
    void removeUsername() {
        String externalId = "closedtasks_laura_cazorla";
        JsonObject metric = new JsonObject();
        JsonObject student = new JsonObject();
        student.addProperty("taigaUsername", "laura_cazorla");
        student.addProperty("githubUsername", "laura_cazorla");
        student.addProperty("prtUsername", "laura_cazorla");
        metric.add("student", student);
        metric.addProperty("externalId", externalId);
        assertEquals("closedtasks", metricController.removeUsername(metric, externalId));
    }

    @Test
    void removeUsernameFromName() {
        String externalId = "laura_cazorla closed tasks";
        JsonObject metric = new JsonObject();
        JsonObject student = new JsonObject();
        student.addProperty("taigaUsername", "laura_cazorla");
        student.addProperty("githubUsername", "laura_cazorla");
        student.addProperty("prtUsername", "laura_cazorla");
        metric.add("student", student);
        metric.addProperty("name", externalId);
        assertEquals("Closed tasks", metricController.removeUsernameFromName(metric, externalId));
    }

    @Test
    void checkExistence() {
        Metric m1 = new Metric("m1", "m1");
        Metric m2 = new Metric("m2", "m2");
        Mockito.when(metricRepository.findById("m1")).thenReturn(Optional.of(m1));
        Mockito.when(metricRepository.findById("m2")).thenReturn(Optional.empty());
        assertTrue(metricController.checkExistence(m1));
        assertFalse(metricController.checkExistence(m2));
    }

    @Test
    void getMetric() {
        Metric m1 = new Metric("m1", "m1");
        Mockito.when(metricRepository.findById("m1")).thenReturn(Optional.of(m1));
        assertEquals(m1, metricController.getMetric("m1"));
    }
}