package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.FactorController;
import com.upc.gessi.loganalytics.app.domain.models.Factor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/factors")
public class FactorRestController {

    @Autowired
    private FactorController factorController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Factor> findAllFactors() {
        return factorController.getAll();
    }

    @GetMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void importFactors() {
        factorController.storeAllFactors();
    }
}
