package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.Team;
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

        internalMetrics.add(new InternalMetric("InteractionsPerSession"));
        internalMetrics.add(new InternalMetric("7DaysLogins", "7", "DaysLogins"));
        internalMetrics.add(new InternalMetric("30DaysLogins", "30", "DaysLogins"));
        internalMetrics.add(new InternalMetric("HistoricAccesses", "true", "HistoricAccesses"));
        internalMetrics.add(new InternalMetric("CurrentAccesses", "false", "HistoricAccesses"));

        internalMetricRepository.saveAll(internalMetrics);
    }

    public List<InternalMetric> getAll() {
        Iterable<InternalMetric> internalMetricIterable = internalMetricRepository.findAll();
        List<InternalMetric> internalMetricList = new ArrayList<>();
        internalMetricIterable.forEach(internalMetricList::add);
        return internalMetricList;
    }

    public void createPageMetric(String page) {
        String name = page + "PageAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(name);
        if (internalMetricOptional.isEmpty()) {
            InternalMetric im = new InternalMetric(name, page, "PageAccesses");
            internalMetricRepository.save(im);
        }
    }

    public void createFactorMetric(String factor) {
        String name = factor + "FactorAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(name);
        if (internalMetricOptional.isEmpty()) {
            InternalMetric im = new InternalMetric(name, factor, "FactorAccesses");
            internalMetricRepository.save(im);
        }
    }

    public void createIndicatorMetric(String indicator) {
        String name = indicator + "IndicatorAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(name);
        if (internalMetricOptional.isEmpty()) {
            InternalMetric im = new InternalMetric(name, indicator, "IndicatorAccesses");
            internalMetricRepository.save(im);
        }
    }

    public void createMetricMetric(String metric) {
        String name = metric + "MetricAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(name);
        if (internalMetricOptional.isEmpty()) {
            InternalMetric im = new InternalMetric(name, metric, "MetricAccesses");
            internalMetricRepository.save(im);
        }
    }

    public void createIndicatorViewMetric(String view) {
        String name = view + "IViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(name);
        if (internalMetricOptional.isEmpty()) {
            InternalMetric im = new InternalMetric(name, view, "IViewAccesses");
            internalMetricRepository.save(im);
        }
    }
    public void createFactorViewMetric(String view) {
        String name = view + "FViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(name);
        if (internalMetricOptional.isEmpty()) {
            InternalMetric im = new InternalMetric(name, view, "FViewAccesses");
            internalMetricRepository.save(im);
        }
    }
    public void createMetricViewMetric(String view) {
        String name = view + "MViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(name);
        if (internalMetricOptional.isEmpty()) {
            InternalMetric im = new InternalMetric(name, view, "MViewAccesses");
            internalMetricRepository.save(im);
        }
    }
    public void createQModelViewMetric(String view) {
        String name = view + "QModViewAccesses";
        Optional<InternalMetric> internalMetricOptional = internalMetricRepository.findById(name);
        if (internalMetricOptional.isEmpty()) {
            InternalMetric im = new InternalMetric(name, view, "QModViewAccesses");
            internalMetricRepository.save(im);
        }
    }
}
