package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.*;
import com.upc.gessi.loganalytics.app.domain.repositories.InternalMetricRepository;
import com.upc.gessi.loganalytics.app.domain.repositories.UserlessInternalMetricRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class InternalMetricController {

    @Autowired
    InternalMetricRepository internalMetricRepository;
    @Autowired
    UserlessInternalMetricRepository userlessInternalMetricRepository;

    @Autowired
    SessionController sessionController;

    @PostConstruct
    public void storeInternalMetrics() {
        List<InternalMetric> internalMetrics = new ArrayList<>();

        internalMetrics.add(new InternalMetric("InteractionsPerSession", "Interactions per session"));
        internalMetrics.add(new InternalMetric("7DaysLogins", "7 days logins", "7", null, "DaysLogins", null, false));
        internalMetrics.add(new InternalMetric("30DaysLogins", "30 days logins", "30", null, "DaysLogins", null, false));
        internalMetrics.add(new InternalMetric("HistoricAccesses", "Historical data accesses", "Historical", null, "HistoricAccesses", "Current vs. Historical accesses", true));
        internalMetrics.add(new InternalMetric("CurrentAccesses", "Current data accesses", "Current", null, "HistoricAccesses", "Current vs. Historical accesses", true));

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
            String name = page + " page accesses";
            InternalMetric im = new InternalMetric(id, name, page, null, "PageAccesses", "Page accesses", true);
            internalMetricRepository.save(im);
        }
    }

    public void createFactorMetric(Factor factor) {
        String id = factor.getId() + "FactorAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = factor.getName() + " factor accesses";
            InternalMetric im = new InternalMetric(id, name, factor.getId(), factor.getName(), "FactorAccesses", "Factor accesses", true);
            internalMetricRepository.save(im);
        }
    }

    public void createIndicatorMetric(Indicator indicator) {
        String id = indicator.getId() + "IndicatorAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = indicator.getName() + " indicator accesses";
            InternalMetric im = new InternalMetric(id, name, indicator.getId(), indicator.getName(), "IndicatorAccesses", "Indicator accesses", true);
            internalMetricRepository.save(im);
        }
    }

    public void createMetricMetric(Metric metric, String team) {
        String id = metric.getId() + "MetricAccesses";
        Optional<UserlessInternalMetric> internalMetricOptional = userlessInternalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = metric.getName() + " metric accesses";
            UserlessInternalMetric im;
            if (team != null) {
                List<String> teams = new ArrayList<>();
                teams.add(team);
                im = new UserlessInternalMetric(id, name, metric.getId(), metric.getName(), "MetricAccesses", "Metric accesses", true, teams, metric.getNoUserId(), metric.getNoUserName());
            }
            else im = new UserlessInternalMetric(id, name, metric.getId(), metric.getName(), "MetricAccesses", "Metric accesses", true, metric.getNoUserId(), metric.getNoUserName());
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
            String name = view + " indicator view accesses";
            InternalMetric im = new InternalMetric(id, name, view, null, "IViewAccesses", "Indicator view accesses", true);
            internalMetricRepository.save(im);
        }
    }

    public void createFactorViewMetric(String view) {
        String id = view + "FViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = view + " factor view accesses";
            InternalMetric im = new InternalMetric(id, name, view, null, "FViewAccesses", "Factor view accesses", true);
            internalMetricRepository.save(im);
        }
    }

    public void createMetricViewMetric(String view) {
        String id = view + "MViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = view + " metric view accesses";
            InternalMetric im = new InternalMetric(id, name, view, null, "MViewAccesses", "Metric view accesses", true);
            internalMetricRepository.save(im);
        }
    }

    public void createQModelViewMetric(String view) {
        String id = view + "QModViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = view + " quality model view accesses";
            InternalMetric im = new InternalMetric(id, name, view, null, "QModViewAccesses", "Quality Model view accesses", true);
            internalMetricRepository.save(im);
        }
    }
}
