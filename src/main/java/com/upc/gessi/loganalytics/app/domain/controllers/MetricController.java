package com.upc.gessi.loganalytics.app.domain.controllers;

import com.google.gson.*;
import com.upc.gessi.loganalytics.app.client.APIClient;
import com.upc.gessi.loganalytics.app.domain.models.Metric;
import com.upc.gessi.loganalytics.app.domain.repositories.MetricRepository;
import jakarta.annotation.PostConstruct;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;

@Controller
public class MetricController {

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private TeamController teamController;

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    @PostConstruct
    public void storeAllMetrics() {
        /*
        List<Team> visibleTeams = teamController.getStoredTeams();
        for (Team visibleTeam : visibleTeams) {
            String project = visibleTeam.getId();
            List<Metric> metrics = getMetricsFromProject(project);
            metricRepository.saveAll(metrics);
        }
        */
        Set<Metric> metrics = getCurrentLDMetrics();
        Iterable<Metric> metricIterable = metricRepository.findAll();
        List<Metric> metricList = new ArrayList<>();
        metricIterable.forEach(metricList::add);
        for (Metric m : metrics) {
            if (!metricList.contains(m))
                metricRepository.save(m);
        }
    }

    private Set<Metric> getCurrentLDMetrics() {
        Set<Metric> metrics = new HashSet<>();
        try {
            APIClient apiClient = new APIClient();
            String url = "http://gessi-dashboard.essi.upc.edu:8888/api/projects";
            HashMap<String, String> queryParams = new HashMap<>();
            HashSet<String> pathSegments = new HashSet<>();
            Response response = apiClient.get(url, queryParams, pathSegments);
            if (response.body() != null) {
                String json = response.body().string();
                JsonArray jsonProjects = JsonParser.parseString(json).getAsJsonArray();
                for (int i = 0; i < jsonProjects.size(); ++i) {
                    JsonObject item = jsonProjects.get(i).getAsJsonObject();
                    String externalId = item.get("externalId").getAsString();
                    List<Metric> metricFromProject = getMetricsFromProject(externalId);
                    metrics.addAll(metricFromProject);
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard response");
        }
        return metrics;
    }

    private List<Metric> getMetricsFromProject(String project) {
        List<Metric> metrics = new ArrayList<>();
        try {
            APIClient apiClient = new APIClient();
            String url = "http://gessi-dashboard.essi.upc.edu:8888/api/metrics";
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("prj", project);
            HashSet<String> pathSegments = new HashSet<>();
            Response response = apiClient.get(url, queryParams, pathSegments);
            if (response.body() != null) {
                String json = response.body().string();
                JsonArray jsonMetrics = JsonParser.parseString(json).getAsJsonArray();
                for (int i = 0; i < jsonMetrics.size(); ++i) {
                    JsonObject item = jsonMetrics.get(i).getAsJsonObject();
                    String externalId = item.get("externalId").getAsString();
                    //externalId = removeUsername(item, externalId);
                    Metric newMetric = new Metric(externalId);
                    metrics.add(newMetric);
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard response");
        }
        return metrics;
    }

    public String removeUsername(JsonObject item, String externalId) {
        JsonElement student = item.get("Student");
        if (student != null && student != JsonNull.INSTANCE) {
            String usernameG = student.getAsJsonObject().get("githubUsername").getAsString();
            String usernameT = student.getAsJsonObject().get("taigaUsername").getAsString();
            if (externalId.contains(usernameG))
                externalId = externalId.replace("_" + usernameG, "");
            else if (externalId.contains(usernameT))
                externalId = externalId.replace("_" + usernameT, "");
        }
        return externalId;
    }
}
