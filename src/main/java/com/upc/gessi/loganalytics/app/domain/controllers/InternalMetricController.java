package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.repositories.InternalMetricRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InternalMetricController {

    @Autowired
    InternalMetricRepository internalMetricRepository;

    @PostConstruct
    public void storeInternalMetrics() {

        List<InternalMetric> internalMetrics = new ArrayList<>();

        /*
        internalMetrics.add(new InternalMetric("interactionsPerSession"));
        internalMetrics.add(new InternalMetric("weeklyLogins"));
        internalMetrics.add(new InternalMetric("monthlyLogins"));

        internalMetrics.add(new InternalMetric("IChartAccesses"));
        internalMetrics.add(new InternalMetric("ITableAccesses"));
        internalMetrics.add(new InternalMetric("DIChartRadarAccesses"));
        internalMetrics.add(new InternalMetric("DIChartStackedAccesses"));
        internalMetrics.add(new InternalMetric("DIChartPolarAccesses"));
        internalMetrics.add(new InternalMetric("DITableAccesses"));

        internalMetrics.add(new InternalMetric("FChartAccesses"));
        internalMetrics.add(new InternalMetric("FTableAccesses"));
        internalMetrics.add(new InternalMetric("DFChartRadarAccesses"));
        internalMetrics.add(new InternalMetric("DFChartStackedAccesses"));
        internalMetrics.add(new InternalMetric("DFChartPolarAccesses"));
        internalMetrics.add(new InternalMetric("DFTableAccesses"));

        internalMetrics.add(new InternalMetric("MTableAccesses"));
        internalMetrics.add(new InternalMetric("MSliderAccesses"));
        internalMetrics.add(new InternalMetric("MGaugeChartAccesses"));

        internalMetrics.add(new InternalMetric("QMGraphAccesses"));
        internalMetrics.add(new InternalMetric("QMSunburstAccesses"));

        internalMetrics.add(new InternalMetric("HistoricAccesses"));
        internalMetrics.add(new InternalMetric("CurrentAccesses"));
         */

        internalMetricRepository.saveAll(internalMetrics);
    }

    public List<InternalMetric> getAll() {
        Iterable<InternalMetric> internalMetricIterable = internalMetricRepository.findAll();
        List<InternalMetric> internalMetricList = new ArrayList<>();
        internalMetricIterable.forEach(internalMetricList::add);
        return internalMetricList;
    }
}
