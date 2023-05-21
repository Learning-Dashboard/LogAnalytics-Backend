package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.MetricAccessController;
import com.upc.gessi.loganalytics.app.domain.models.MetricAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metricAccesses")
public class MetricAccessRestController {

    @Autowired
    private MetricAccessController metricAccessController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MetricAccess> findAllMetricAccesses() {
        return metricAccessController.getAll();
    }
}
