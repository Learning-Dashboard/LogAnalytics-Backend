package com.upc.gessi.loganalytics.app.domain.controllers;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MetricControllerTest {

    @InjectMocks
    MetricController metricController;

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
}