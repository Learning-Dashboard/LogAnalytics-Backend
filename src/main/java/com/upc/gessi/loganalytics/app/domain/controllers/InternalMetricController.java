package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.repositories.InternalMetricRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class InternalMetricController {

    @Autowired
    InternalMetricRepository internalMetricRepository;

    @Autowired
    SessionController sessionController;

    @PostConstruct
    public void storeInternalMetrics() {
        List<InternalMetric> internalMetrics = new ArrayList<>();

        internalMetrics.add(new InternalMetric("InteractionsPerSession", "Interactions per session"));
        internalMetrics.add(new InternalMetric("7DaysLogins", "7 days logins", "7", "DaysLogins"));
        internalMetrics.add(new InternalMetric("30DaysLogins", "30 days logins", "30", "DaysLogins"));
        internalMetrics.add(new InternalMetric("HistoricAccesses", "Historical data accesses", "true", "HistoricAccesses"));
        internalMetrics.add(new InternalMetric("CurrentAccesses", "Current data accesses", "false", "HistoricAccesses"));

        internalMetricRepository.saveAll(internalMetrics);
    }

    public List<InternalMetric> getAll() {
        Iterable<InternalMetric> internalMetricIterable = internalMetricRepository.findAll();
        List<InternalMetric> internalMetricList = new ArrayList<>();
        internalMetricIterable.forEach(internalMetricList::add);
        return internalMetricList;
    }

    public void createPageMetric(String page) {
        String id = page + "PageAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = page + " page accesses";
            InternalMetric im = new InternalMetric(id, name, page, "PageAccesses");
            internalMetricRepository.save(im);
        }
    }

    public void createFactorMetric(String factor) {
        String id = factor + "FactorAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = factor + " factor accesses";
            InternalMetric im = new InternalMetric(id, name, factor, "FactorAccesses");
            internalMetricRepository.save(im);
        }
    }

    public void createIndicatorMetric(String indicator) {
        String id = indicator + "IndicatorAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = indicator + " indicator accesses";
            InternalMetric im = new InternalMetric(id, name, indicator, "IndicatorAccesses");
            internalMetricRepository.save(im);
        }
    }

    public void createMetricMetric(String metric, String team) {
        String id = metric + "MetricAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = metric + " metric accesses";
            InternalMetric im;
            if (team != null) {
                List<String> teams = new ArrayList<>();
                teams.add(team);
                im = new InternalMetric(id, name, metric, "MetricAccesses", teams);
            }
            else {
                im = new InternalMetric(id, name, metric, "MetricAccesses");
            }
            internalMetricRepository.save(im);
        }
        else {
            InternalMetric im = internalMetricOptional.get();
            if (team != null) {
                List<String> imTeams = im.getTeams();
                if (!imTeams.contains(team)) {
                    imTeams.add(team);
                    im.setTeams(imTeams);
                    internalMetricRepository.save(im);
                }
            }
        }
    }

    public void createIndicatorViewMetric(String view) {
        String id = view + "IViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = view + " indicator view accesses";
            InternalMetric im = new InternalMetric(id, name, view, "IViewAccesses");
            internalMetricRepository.save(im);
        }
    }

    public void createFactorViewMetric(String view) {
        String id = view + "FViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = view + " factor view accesses";
            InternalMetric im = new InternalMetric(id, name, view, "FViewAccesses");
            internalMetricRepository.save(im);
        }
    }

    public void createMetricViewMetric(String view) {
        String id = view + "MViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = view + " metric view accesses";
            InternalMetric im = new InternalMetric(id, name, view, "MViewAccesses");
            internalMetricRepository.save(im);
        }
    }

    public void createQModelViewMetric(String view) {
        String id = view + "QModViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(id);
        if (internalMetricOptional.isEmpty()) {
            String name = view + " quality model view accesses";
            InternalMetric im = new InternalMetric(id, name, view, "QModViewAccesses");
            internalMetricRepository.save(im);
        }
    }
}
