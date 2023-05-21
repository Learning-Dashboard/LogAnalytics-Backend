package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.MetricController;
import com.upc.gessi.loganalytics.app.domain.models.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class MetricRestController {

    @Autowired
    private MetricController metricController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Metric> findAllMetrics() {
        return metricController.getAll();
    }

    @GetMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void importMetrics() {
        metricController.storeAllMetrics();
    }
}
