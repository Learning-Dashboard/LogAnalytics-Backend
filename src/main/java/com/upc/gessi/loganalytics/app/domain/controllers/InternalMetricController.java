package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.InternalMetricRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.UserlessInternalMetricRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@DependsOn("categoryController")
public class InternalMetricController {

    @Autowired
    InternalMetricRepository internalMetricRepository;
    @Autowired
    UserlessInternalMetricRepository userlessInternalMetricRepository;

    @Autowired
    CategoryController categoryController;

    @PostConstruct
    public void storeInternalMetrics() {
        List<InternalMetric> internalMetrics = new ArrayList<>();

        Category c1 = categoryController.getByName("Session management");
        Category c2 = categoryController.getByName("Other kinds of accesses");

        InternalMetric im1 = new InternalMetric("InteractionsPerSession", "Interactions per session", c1);
        InternalMetric im2 = new InternalMetric("7DaysLogins", "7 days logins", "7", null, "DaysLogins", null, false, c1);
        InternalMetric im3 = new InternalMetric("30DaysLogins", "30 days logins", "30", null, "DaysLogins", null, false, c1);
        InternalMetric im4 = new InternalMetric("HistoricAccesses", "Historical data accesses", "Historical", null, "HistoricAccesses", "Current vs. Historical accesses", true, c2);
        InternalMetric im5 = new InternalMetric("CurrentAccesses", "Current data accesses", "Current", null, "HistoricAccesses", "Current vs. Historical accesses", true, c2);

        internalMetrics.add(im1); internalMetrics.add(im2); internalMetrics.add(im3);
        internalMetrics.add(im4); internalMetrics.add(im5);

        internalMetricRepository.saveAll(internalMetrics);
    }

    public List<InternalMetric> getAll() {
        Iterable<InternalMetric> internalMetricIterable = internalMetricRepository.findAll();
        List<InternalMetric> internalMetricList = new ArrayList<>();
        internalMetricIterable.forEach(internalMetricList::add);
        return internalMetricList;
    }

    public boolean checkParamNameExistence(String paramName) {
        return internalMetricRepository.existsByParamName(paramName);
    }

    public void createPageMetric(String page) {
        String id = page + "PageAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            Category c = categoryController.getByName("Other kinds of accesses");
            String name = page + " page accesses";
            InternalMetric im = new InternalMetric(id, name, page, null, "PageAccesses", "Page accesses", true, c);
            internalMetricRepository.save(im);
        }
    }

    public void createFactorMetric(Factor factor) {
        String id = factor.getId() + "FactorAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            Category c = categoryController.getByName("Factor accesses");
            String name = factor.getName() + " factor accesses";
            InternalMetric im = new InternalMetric(id, name, factor.getId(), factor.getName(), "FactorAccesses", "Factor accesses", true, c);
            internalMetricRepository.save(im);
        }
    }

    public void createIndicatorMetric(Indicator indicator) {
        String id = indicator.getId() + "IndicatorAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            Category c = categoryController.getByName("Indicator accesses");
            String name = indicator.getName() + " indicator accesses";
            InternalMetric im = new InternalMetric(id, name, indicator.getId(), indicator.getName(), "IndicatorAccesses", "Indicator accesses", true, c);
            internalMetricRepository.save(im);
        }
    }

    public void createMetricMetric(Metric metric, String team) {
        String id = metric.getId() + "MetricAccesses";
        Optional<UserlessInternalMetric> internalMetricOptional = userlessInternalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            Category c = categoryController.getByName("Metric accesses");
            String name = metric.getName() + " metric accesses";
            UserlessInternalMetric im;
            if (team != null) {
                List<String> teams = new ArrayList<>();
                teams.add(team);
                im = new UserlessInternalMetric(id, name, metric.getId(), metric.getName(), "MetricAccesses", "Metric accesses", true, c, teams, metric.getNoUserId(), metric.getNoUserName());
            }
            else im = new UserlessInternalMetric(id, name, metric.getId(), metric.getName(), "MetricAccesses", "Metric accesses", true, c, metric.getNoUserId(), metric.getNoUserName());
            internalMetricRepository.save(im);
        }
        else {
            UserlessInternalMetric im = internalMetricOptional.get();
            if (team != null) {
                List<String> imTeams = im.getTeams();
                if (!imTeams.contains(team)) {
                    imTeams.add(team);
                    im.setTeams(imTeams);
                }
            }
            if (!Objects.equals(im.getUserlessId(), metric.getNoUserId()))
                im.setUserlessId(metric.getNoUserId());
            if (!Objects.equals(im.getUserlessName(), metric.getNoUserName()))
                im.setUserlessName(metric.getNoUserName());
            internalMetricRepository.save(im);
        }
    }

    public void createIndicatorViewMetric(String view) {
        String id = view + "IViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            Category c = categoryController.getByName("Indicator accesses");
            String name = view + " indicator view accesses";
            InternalMetric im = new InternalMetric(id, name, view, null, "IViewAccesses", "Indicator view accesses", true, c);
            internalMetricRepository.save(im);
        }
    }

    public void createFactorViewMetric(String view) {
        String id = view + "FViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            Category c = categoryController.getByName("Factor accesses");
            String name = view + " factor view accesses";
            InternalMetric im = new InternalMetric(id, name, view, null, "FViewAccesses", "Factor view accesses", true, c);
            internalMetricRepository.save(im);
        }
    }

    public void createMetricViewMetric(String view) {
        String id = view + "MViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            Category c = categoryController.getByName("Metric accesses");
            String name = view + " metric view accesses";
            InternalMetric im = new InternalMetric(id, name, view, null, "MViewAccesses", "Metric view accesses", true, c);
            internalMetricRepository.save(im);
        }
    }

    public void createQModelViewMetric(String view) {
        String id = view + "QModViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            Category c = categoryController.getByName("Quality Model accesses");
            String name = view + " quality model view accesses";
            InternalMetric im = new InternalMetric(id, name, view, null, "QModViewAccesses", "Quality Model view accesses", true, c);
            internalMetricRepository.save(im);
        }
    }
}
