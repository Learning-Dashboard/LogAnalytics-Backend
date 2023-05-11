package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Subject;
import com.upc.gessi.loganalytics.app.domain.models.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {
    List<Session> findByStartTimestampLessThan(long time);
    List<Session> findByTeamAndStartTimestampLessThan(Team team, long time);
    List<Session> findByTeamSubjectAndStartTimestampLessThan(Subject subject, long time);
    List<Session> findByStartTimestampBetweenAndTeam(long timeBefore, long timeAfter, Team team);
}
