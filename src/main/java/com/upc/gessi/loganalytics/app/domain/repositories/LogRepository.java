package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.Log;
import com.upc.gessi.loganalytics.app.domain.models.pkey.LogPrimaryKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log, LogPrimaryKey> {
    List<Log> findAllByOrderByTimeDesc();
    Page<Log> findAllByOrderByTimeDesc(Pageable pageable);
    List<Log> findByTimeGreaterThanEqualOrderByTimeDesc(long epoch);
    Page<Log> findByTimeGreaterThanEqualOrderByTimeDesc(long epoch, Pageable pageable);
    List<Log> findByTimeLessThanEqualOrderByTimeDesc(long epoch);
    Page<Log> findByTimeLessThanEqualOrderByTimeDesc(long epoch, Pageable pageable);
    List<Log> findByTimeBetweenOrderByTimeDesc(long epochBefore, long epochAfter);
    Page<Log> findByTimeBetweenOrderByTimeDesc(long epochBefore, long epochAfter, Pageable pageable);
    List<Log> findByTeamOrderByTimeDesc(String team);
    Page<Log> findByTeamOrderByTimeDesc(String team, Pageable pageable);
    Log findFirstByOrderByTimeDesc();
    List<Log> findByMessageContainingOrderByTimeDesc(String keyword);
    Page<Log> findByMessageContainingOrderByTimeDesc(String keyword, Pageable pageable);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?1 ORDER BY time DESC")
    List<Log> findBySubjectOrderByTimeDesc(String subject);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?1 ORDER BY time DESC")
    Page<Log> findBySubjectOrderByTimeDesc(String subject, Pageable pageable);
}
