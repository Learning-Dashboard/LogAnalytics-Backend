package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.models.MetricAccess;
import com.upc.gessi.loganalytics.app.domain.repositories.MetricAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/metricAccesses")
public class MetricAccessRestController {

    @Autowired
    private MetricAccessRepository metricAccessRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MetricAccess> findAllMetricAccesses() {
        Iterable<MetricAccess> metricAccessIterable = metricAccessRepository.findAll();
        List<MetricAccess> metricAccessList = new ArrayList<>();
        metricAccessIterable.forEach(metricAccessList::add);
        return metricAccessList;
    }
}
