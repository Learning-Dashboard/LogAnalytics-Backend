package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {
    List<Session> findByTeamAndEndTimestampLessThan(Team team, long time);
    List<Session> findByTeamSubject(Subject subject);
    List<Session> findByStartTimestampBetweenAndTeam(long timeBefore, long timeAfter, Team team);
}
