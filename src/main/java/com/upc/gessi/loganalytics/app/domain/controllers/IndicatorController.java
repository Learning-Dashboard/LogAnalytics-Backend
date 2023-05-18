package com.upc.gessi.loganalytics.app.domain.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.upc.gessi.loganalytics.app.client.APIClient;
import com.upc.gessi.loganalytics.app.domain.models.Indicator;
import com.upc.gessi.loganalytics.app.domain.repositories.IndicatorRepository;
import jakarta.annotation.PostConstruct;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;

@Controller
public class IndicatorController {

    @Autowired
    private IndicatorRepository indicatorRepository;

    @Autowired
    private TeamController teamController;

    @Autowired
    private InternalMetricController internalMetricController;

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    @PostConstruct
    public void storeAllIndicators() {
        Set<Indicator> indicators = getCurrentLDIndicators();
        for (Indicator i : indicators) {
            Optional<Indicator> indicatorOptional = indicatorRepository.findById(i.getId());
            if (indicatorOptional.isEmpty()) {
                indicatorRepository.save(i);
                internalMetricController.createIndicatorMetric(i.getId());
            }
        }
    }

    private Set<Indicator> getCurrentLDIndicators() {
        Set<Indicator> indicators = new HashSet<>();
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
                    List<Indicator> indicatorFromProject = getIndicatorsFromProject(externalId);
                    indicators.addAll(indicatorFromProject);
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard response");
        }
        return indicators;
    }

    private List<Indicator> getIndicatorsFromProject(String project) {
        List<Indicator> indicators = new ArrayList<>();
        try {
            APIClient apiClient = new APIClient();
            String url = "http://gessi-dashboard.essi.upc.edu:8888/api/strategicIndicators";
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
                    Indicator newIndicator = new Indicator(externalId, name);
                    indicators.add(newIndicator);
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard response");
        }
        return indicators;
    }

    public boolean checkExistence(Indicator i) {
        Optional<Indicator> indicatorOptional = indicatorRepository.findById(i.getId());
        return indicatorOptional.isPresent();
    }

    public Indicator getIndicator(String id) {
        Optional<Indicator> indicatorOptional = indicatorRepository.findById(id);
        return indicatorOptional.orElse(null);
    }
}
