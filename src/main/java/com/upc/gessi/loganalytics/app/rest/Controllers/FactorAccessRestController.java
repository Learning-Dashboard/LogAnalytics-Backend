package com.upc.gessi.loganalytics.app.rest.Controllers;

import com.upc.gessi.loganalytics.app.domain.models.FactorAccess;
import com.upc.gessi.loganalytics.app.domain.repositories.FactorAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/factorAccesses")
public class FactorAccessRestController {

    @Autowired
    private FactorAccessRepository factorAccessRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FactorAccess> findAllFactorAccesses() {
        Iterable<FactorAccess> factorAccessIterable = factorAccessRepository.findAll();
        List<FactorAccess> factorAccessList = new ArrayList<>();
        factorAccessIterable.forEach(factorAccessList::add);
        return factorAccessList;
    }
}
