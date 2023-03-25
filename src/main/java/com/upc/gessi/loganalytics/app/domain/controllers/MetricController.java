package com.upc.gessi.loganalytics.app.domain.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.upc.gessi.loganalytics.app.domain.models.Metric;
import com.upc.gessi.loganalytics.app.domain.repositories.MetricRepository;
import jakarta.annotation.PostConstruct;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    @PostConstruct
    public void storeAllMetrics() {
        Set<Metric> metrics = getCurrentLDMetrics();
        metricRepository.saveAll(metrics);
    }

    private Set<Metric> getCurrentLDMetrics() {
        Set<Metric> metrics = new HashSet<>();
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://gessi-dashboard.essi.upc.edu:8888/api/projects")).newBuilder();
            Request request = new Request.Builder()
                    .url(urlBuilder.build().toString())
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
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
            logger.error("Error in the Learning Dashboard server");
        }
        return metrics;
    }

    private List<Metric> getMetricsFromProject(String project) {
        List<Metric> metrics = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("http://gessi-dashboard.essi.upc.edu:8888/api/metrics")).newBuilder();
            urlBuilder.addQueryParameter("prj", project);
            Request request = new Request.Builder()
                    .url(urlBuilder.build().toString())
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
                String json = response.body().string();
                JsonArray jsonMetrics = JsonParser.parseString(json).getAsJsonArray();
                for (int i = 0; i < jsonMetrics.size(); ++i) {
                    JsonObject item = jsonMetrics.get(i).getAsJsonObject();
                    String externalId = item.get("externalId").getAsString();
                    Metric newMetric = new Metric(externalId);
                    metrics.add(newMetric);
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard server");
        }
        return metrics;
    }
}
