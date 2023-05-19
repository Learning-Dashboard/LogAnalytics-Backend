package com.upc.gessi.loganalytics.app.domain.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.upc.gessi.loganalytics.app.client.APIClient;
import com.upc.gessi.loganalytics.app.domain.models.Factor;
import com.upc.gessi.loganalytics.app.domain.models.Indicator;
import com.upc.gessi.loganalytics.app.domain.repositories.FactorRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import okhttp3.Response;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;

@Controller
public class FactorController {

    @Autowired
    private FactorRepository factorRepository;

    @Autowired
    private TeamController teamController;

    @Autowired
    private InternalMetricController internalMetricController;

    private static final Logger logger =
            LoggerFactory.getLogger("ActionLogger");

    @PostConstruct
    public void storeAllFactors() {
        Set<Factor> factors = getCurrentLDFactors();
        for (Factor f : factors) {
            Optional<Factor> factorOptional = factorRepository.findById(f.getId());
            if (factorOptional.isEmpty()) {
                factorRepository.save(f);
                internalMetricController.createFactorMetric(f);
            }
        }
    }

    private Set<Factor> getCurrentLDFactors() {
        Set<Factor> factors = new HashSet<>();
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
                    List<Factor> factorFromProject = getFactorsFromProject(externalId);
                    factors.addAll(factorFromProject);
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard response");
        }
        return factors;
    }

    private List<Factor> getFactorsFromProject(String project) {
        List<Factor> factors = new ArrayList<>();
        try {
            APIClient apiClient = new APIClient();
            String url = "http://gessi-dashboard.essi.upc.edu:8888/api/qualityFactors";
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
                    Factor newFactor = new Factor(externalId, name);
                    factors.add(newFactor);
                }
            }
        } catch (IOException e) {
            logger.error("Error in the Learning Dashboard response");
        }
        return factors;
    }

    public boolean checkExistence(Factor f) {
        Optional<Factor> factorOptional = factorRepository.findById(f.getId());
        return factorOptional.isPresent();
    }

    public Factor getFactor(String id) {
        Optional<Factor> factorOptional = factorRepository.findById(id);
        return factorOptional.orElse(null);
    }
}
