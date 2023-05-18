package com.upc.gessi.loganalytics.app.domain.controllers;

import com.google.gson.*;
import com.upc.gessi.loganalytics.app.client.APIClient;
import com.upc.gessi.loganalytics.app.domain.models.Indicator;
import com.upc.gessi.loganalytics.app.domain.models.Metric;
import com.upc.gessi.loganalytics.app.domain.repositories.MetricRepository;
import jakarta.annotation.PostConstruct;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Controller
public class MetricController {

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private TeamController teamController;

    @Autowired
    private InternalMetricController internalMetricController;

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    @PostConstruct
    public void storeAllMetrics() {
        Map<Metric,Set<String>> metrics = getCurrentLDMetrics();
        for (Map.Entry<Metric,Set<String>> entry : metrics.entrySet()) {
            Metric m = entry.getKey();
            for (String team : entry.getValue())
                internalMetricController.createMetricMetric(m.getId(), team);
            Optional<Metric> metricOptional = metricRepository.findById(m.getId());
            if (metricOptional.isEmpty()) metricRepository.save(m);
            else {
                Metric met = metricOptional.get();
                met.setName(m.getName());
                met.setNoUserId(m.getNoUserId());
                met.setNoUserName(m.getNoUserName());
                if (!Objects.equals(met.getName(), m.getName())
                    || !Objects.equals(met.getNoUserId(), m.getNoUserId())
                    || !Objects.equals(met.getNoUserName(), m.getNoUserName()))
                    metricRepository.save(met);
            }
        }
    }

    private Map<Metric,Set<String>> getCurrentLDMetrics() {
        Map<Metric,Set<String>> metrics = new HashMap<>();
        try {
            APIClient apiClient = new APIClient();
            String url = "http://gessi-dashboard.essi.upc.edu:8888/api/projects";
            HashMap<String, String> queryParams = new HashMap<>();
            HashSet<String> pathSegments = new HashSet<>();
            Response response = apiClient.get(url, queryParams, pathSegments);
            if (response != null && response.body() != null) {
                String json = response.body().string();
                JsonArray jsonProjects = JsonParser.parseString(json).getAsJsonArray();
                for (int i = 0; i < jsonProjects.size(); ++i) {
                    JsonObject item = jsonProjects.get(i).getAsJsonObject();
                    String externalId = item.get("externalId").getAsString();
                    Map<Metric,String> metricFromProject = getMetricsFromProject(externalId);
                    //metrics.putAll(metricFromProject);
                    for (Map.Entry<Metric,String> entry : metricFromProject.entrySet()) {
                        Metric m = entry.getKey();
                        String project = entry.getValue();
                        if (metrics.containsKey(entry.getKey())) {
                            Set<String> teams = metrics.get(m);
                            teams.add(project);
                            metrics.replace(m, teams);
                        }
                        else {
                            Set<String> teams = new HashSet<>();
                            teams.add(project);
                            metrics.put(m, teams);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard response");
        }
        return metrics;
    }

    private Map<Metric,String> getMetricsFromProject(String project) {
        Map<Metric,String> metrics = new HashMap<>();
        try {
            APIClient apiClient = new APIClient();
            String url = "http://gessi-dashboard.essi.upc.edu:8888/api/metrics";
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put("prj", project);
            HashSet<String> pathSegments = new HashSet<>();
            Response response = apiClient.get(url, queryParams, pathSegments);
            if (response != null && response.body() != null) {
                String json = response.body().string();
                JsonArray jsonMetrics = JsonParser.parseString(json).getAsJsonArray();
                for (int i = 0; i < jsonMetrics.size(); ++i) {
                    JsonObject item = jsonMetrics.get(i).getAsJsonObject();
                    String externalId = item.get("externalId").getAsString();
                    String name = item.get("name").getAsString();
                    String noUserId = removeUsername(item, externalId);
                    String noUserName = removeUsernameFromName(item, name);
                    String team = getMetricTeam(item);
                    Metric newMetric;
                    if (noUserId.equals(externalId)) newMetric = new Metric(externalId, name);
                    else newMetric = new Metric(externalId, name, noUserId, noUserName);
                    metrics.put(newMetric, team);
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard response");
        }
        return metrics;
    }

    public String removeUsername(JsonObject item, String externalId) {
        JsonElement student = item.get("student");
        String ret = null;
        if (student != null && student != JsonNull.INSTANCE) {

            JsonElement uG = student.getAsJsonObject().get("githubUsername");
            if (!uG.isJsonNull()) {
                String usernameG = uG.getAsString().replace('.', '_').replace('-', '_');
                if (externalId.endsWith("_" + usernameG))
                    ret = externalId.replace("_" + usernameG, "");
            }

            JsonElement uT = student.getAsJsonObject().get("taigaUsername");
            if (!uT.isJsonNull()) {
                String usernameT = uT.getAsString().replace('.', '_').replace('-', '_');
                if (externalId.endsWith("_" + usernameT))
                    ret = externalId.replace("_" + usernameT, "");
            }

            JsonElement uPRT = student.getAsJsonObject().get("prtUsername");
            if (!uPRT.isJsonNull()) {
                String usernamePRT = uPRT.getAsString().replace(' ', '_')
                    .replace('.', '_').replace('-', '_');
                usernamePRT = usernamePRT.toLowerCase();
                if (externalId.endsWith("_" + usernamePRT))
                    ret = externalId.replace("_" + usernamePRT, "");
            }
        }
        if (ret != null && ret.endsWith("_"))
            ret = ret.substring(0, ret.length() - 1);
        if (ret != null) return ret;
        return externalId;
    }

    public String removeUsernameFromName(JsonObject item, String originalName) {
        JsonElement student = item.get("student");
        String ret = null;
        if (student != null && student != JsonNull.INSTANCE) {

            JsonElement uG = student.getAsJsonObject().get("githubUsername");
            if (!uG.isJsonNull()) {
                String usernameG = uG.getAsString();
                if (originalName.contains(usernameG + " ")) {
                    ret = originalName.replace(usernameG + " ", "");
                    ret = StringUtils.capitalize(ret);
                }
            }

            JsonElement uT = student.getAsJsonObject().get("taigaUsername");
            if (!uT.isJsonNull()) {
                String usernameT = uT.getAsString();
                if (originalName.contains(usernameT + " ")) {
                    ret = originalName.replace(usernameT + " ", "");
                    ret = StringUtils.capitalize(ret);
                }
            }

            JsonElement uPRT = student.getAsJsonObject().get("prtUsername");
            if (!uPRT.isJsonNull()) {
                String usernamePRT = uPRT.getAsString();
                if (originalName.contains(usernamePRT + " ")) {
                    ret = originalName.replace(usernamePRT + " ", "");
                    ret = StringUtils.capitalize(ret);
                }
            }
        }
        if (ret != null && ret.startsWith("_"))
            ret = StringUtils.capitalize(ret.substring(1));
        if (ret != null) return ret;
        return originalName;
    }

    private String getMetricTeam(JsonObject item) {
        JsonElement student = item.get("student");
        if (student != null && student != JsonNull.INSTANCE)
            return item.get("project").getAsJsonObject().get("externalId").getAsString();
        return null;
    }

    public boolean checkExistence(Metric m) {
        Optional<Metric> metricOptional = metricRepository.findById(m.getId());
        return metricOptional.isPresent();
    }
}
