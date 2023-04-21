package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.FactorAccess;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.FactorAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FactorAccessController {

    @Autowired
    FactorAccessRepository factorAccessRepository;

    public List<FactorAccess> getAllByTeamAndFactor(Team team, String factor) {
        Iterable<FactorAccess> factorAccessIterable =
            factorAccessRepository.findByTeamAndFactorsId(team.getId(), factor);
        List<FactorAccess> factorAccesses = new ArrayList<>();
        factorAccessIterable.forEach(factorAccesses::add);
        return factorAccesses;
    }
}
