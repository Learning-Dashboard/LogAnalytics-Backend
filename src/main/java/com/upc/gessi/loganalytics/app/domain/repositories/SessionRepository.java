package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.pkey.SessionPrimaryKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<Session, SessionPrimaryKey> {
    List<Session> findByTeamIdAndTeamSemester(String teamId, String teamSemester);
    List<Session> findByNInteractions(int nInteractions);
}
