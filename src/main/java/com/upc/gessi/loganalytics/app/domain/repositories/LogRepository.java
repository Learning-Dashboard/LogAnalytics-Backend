package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Log;
import com.upc.gessi.loganalytics.app.domain.models.pkey.LogPrimaryKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log, LogPrimaryKey> {
    List<Log> findByTimeGreaterThanEqual(long epoch);
    List<Log> findByTimeLessThanEqual(long epoch);
    List<Log> findByTimeBetween(long epochBefore, long epochAfter);
    List<Log> findByTeam(String team);
}
