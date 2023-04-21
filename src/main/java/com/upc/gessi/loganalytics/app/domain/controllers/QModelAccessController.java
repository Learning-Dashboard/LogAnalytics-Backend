package com.upc.gessi.loganalytics.app.domain.controllers;

import com.upc.gessi.loganalytics.app.domain.models.QModelAccess;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import com.upc.gessi.loganalytics.app.domain.repositories.QModelAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QModelAccessController {

    @Autowired
    QModelAccessRepository qModelAccessRepository;

    public List<QModelAccess> getAllByTeamAndViewFormat(Team team, String viewFormat) {
        Iterable<QModelAccess> qModelAccessIterable =
            qModelAccessRepository.findByTeamAndViewFormat(team.getId(), viewFormat);
        List<QModelAccess> qModelAccesses = new ArrayList<>();
        qModelAccessIterable.forEach(qModelAccesses::add);
        return qModelAccesses;
    }

}
