package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.repositories.UserlessInternalMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserlessInternalMetricController {

    @Autowired
    UserlessInternalMetricRepository userlessInternalMetricRepository;

    public boolean checkNoUserNameExistence(String noUserName) {
        return userlessInternalMetricRepository.existsByUserlessName(noUserName);
    }
}
