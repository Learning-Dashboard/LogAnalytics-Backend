package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.IndicatorAccessController;
import com.upc.gessi.loganalytics.app.domain.models.IndicatorAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/indicatorAccesses")
public class IndicatorAccessRestController {

    @Autowired
    private IndicatorAccessController indicatorAccessController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IndicatorAccess> findAllIndicatorAccesses() {
        return indicatorAccessController.getAll();
    }
}
