package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.InternalMetricController;
import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import com.upc.gessi.loganalytics.app.domain.models.Metric;
import com.upc.gessi.loganalytics.app.domain.repositories.InternalMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/internalMetrics")
public class InternalMetricRestController {

    @Autowired
    InternalMetricController internalMetricController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InternalMetric> findAllInternalMetrics() {
        return internalMetricController.getAll();
    }
}
