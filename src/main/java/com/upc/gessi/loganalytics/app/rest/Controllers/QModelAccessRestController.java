package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.controllers.QModelAccessController;
import com.upc.gessi.loganalytics.app.domain.models.QModelAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/qModelAccesses")
public class QModelAccessRestController {

    @Autowired
    private QModelAccessController qModelAccessController;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<QModelAccess> findAllQModelAccesses() {
        return qModelAccessController.getAll();
    }
}
