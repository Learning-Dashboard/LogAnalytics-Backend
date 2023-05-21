package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.IndicatorController;
import com.upc.gessi.loganalytics.app.domain.models.Indicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/indicators")
public class IndicatorRestController {

    @Autowired
    private IndicatorController indicatorController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Indicator> findAllIndicators() {
        return indicatorController.getAll();
    }

    @GetMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void importIndicators() {
        indicatorController.storeAllIndicators();
    }
}
