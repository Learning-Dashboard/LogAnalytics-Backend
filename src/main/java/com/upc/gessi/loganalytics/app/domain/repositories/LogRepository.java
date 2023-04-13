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
    List<Log> findByTimeGreaterThanEqualAndMessageContainingOrderByTimeDesc(long epoch, String keyword);
    Page<Log> findByTimeGreaterThanEqualAndMessageContainingOrderByTimeDesc(long epoch, String keyword, Pageable pageable);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?2 AND l.time >= ?1 " +
            "ORDER BY time DESC")
    List<Log> findByTimeGreaterThanEqualAndSubjectOrderByTimeDesc(long epoch, String subject);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?2 AND l.time >= ?1 " +
            "ORDER BY time DESC")
    Page<Log> findByTimeGreaterThanEqualAndSubjectOrderByTimeDesc(long epoch, String subject, Pageable pageable);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?2 AND l.time >= ?1 AND l.message LIKE %?3% " +
            "ORDER BY time DESC")
    List<Log> findByTimeGreaterThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(long epoch, String subject, String keyword);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?2 AND l.time >= ?1 AND l.message LIKE %?3% " +
            "ORDER BY time DESC")
    Page<Log> findByTimeGreaterThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(long epoch, String subject, String keyword, Pageable pageable);
    List<Log> findByTimeGreaterThanEqualAndTeamOrderByTimeDesc(long epoch, String team);
    Page<Log> findByTimeGreaterThanEqualAndTeamOrderByTimeDesc(long epoch, String team, Pageable pageable);
    List<Log> findByTimeGreaterThanEqualAndTeamAndMessageContainingOrderByTimeDesc(long epoch, String team, String keyword);
    Page<Log> findByTimeGreaterThanEqualAndTeamAndMessageContainingOrderByTimeDesc(long epoch, String team, String keyword, Pageable pageable);

    List<Log> findByTimeLessThanEqualOrderByTimeDesc(long epoch);
    Page<Log> findByTimeLessThanEqualOrderByTimeDesc(long epoch, Pageable pageable);
    List<Log> findByTimeLessThanEqualAndMessageContainingOrderByTimeDesc(long epoch, String keyword);
    Page<Log> findByTimeLessThanEqualAndMessageContainingOrderByTimeDesc(long epoch, String keyword, Pageable pageable);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?2 AND l.time <= ?1 " +
            "ORDER BY time DESC")
    List<Log> findByTimeLessThanEqualAndSubjectOrderByTimeDesc(long epoch, String subject);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?2 AND l.time <= ?1 " +
            "ORDER BY time DESC")
    Page<Log> findByTimeLessThanEqualAndSubjectOrderByTimeDesc(long epoch, String subject, Pageable pageable);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?2 AND l.time <= ?1 AND l.message LIKE %?3% " +
            "ORDER BY time DESC")
    List<Log> findByTimeLessThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(long epoch, String subject, String keyword);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?2 AND l.time <= ?1 AND l.message LIKE %?3% " +
            "ORDER BY time DESC")
    Page<Log> findByTimeLessThanEqualAndSubjectAndMessageContainingOrderByTimeDesc(long epoch, String subject, String keyword, Pageable pageable);
    List<Log> findByTimeLessThanEqualAndTeamOrderByTimeDesc(long epoch, String team);
    Page<Log> findByTimeLessThanEqualAndTeamOrderByTimeDesc(long epoch, String team, Pageable pageable);
    List<Log> findByTimeLessThanEqualAndTeamAndMessageContainingOrderByTimeDesc(long epoch, String team, String keyword);
    Page<Log> findByTimeLessThanEqualAndTeamAndMessageContainingOrderByTimeDesc(long epoch, String team, String keyword, Pageable pageable);

    List<Log> findByTimeBetweenOrderByTimeDesc(long epochBefore, long epochAfter);
    Page<Log> findByTimeBetweenOrderByTimeDesc(long epochBefore, long epochAfter, Pageable pageable);
    List<Log> findByTimeBetweenAndMessageContainingOrderByTimeDesc(long epochBefore, long epochAfter, String keyword);
    Page<Log> findByTimeBetweenAndMessageContainingOrderByTimeDesc(long epochBefore, long epochAfter, String keyword, Pageable pageable);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?3 AND l.time >= ?1 " +
            "AND l.time <= ?2 ORDER BY time DESC")
    List<Log> findByTimeBetweenAndSubjectOrderByTimeDesc(long epochBefore, long epochAfter, String subject);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?3 AND l.time >= ?1 " +
            "AND l.time <= ?2 ORDER BY time DESC")
    Page<Log> findByTimeBetweenAndSubjectOrderByTimeDesc(long epochBefore, long epochAfter, String subject, Pageable pageable);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?3 AND l.time >= ?1 " +
            "AND l.time <= ?2 AND l.message LIKE %?4% " +
            "ORDER BY time DESC")
    List<Log> findByTimeBetweenAndSubjectAndMessageContainingOrderByTimeDesc(long epochBefore, long epochAfter, String subject, String keyword);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?3 AND l.time >= ?1 " +
            "AND l.time <= ?2 AND l.message LIKE %?4% " +
            "ORDER BY time DESC")
    Page<Log> findByTimeBetweenAndSubjectAndMessageContainingOrderByTimeDesc(long epochBefore, long epochAfter, String subject, String keyword, Pageable pageable);
    List<Log> findByTimeBetweenAndTeamOrderByTimeDesc(long epochBefore, long epochAfter, String team);
    Page<Log> findByTimeBetweenAndTeamOrderByTimeDesc(long epochBefore, long epochAfter, String team, Pageable pageable);
    List<Log> findByTimeBetweenAndTeamAndMessageContainingOrderByTimeDesc(long epochBefore, long epochAfter, String team, String keyword);
    Page<Log> findByTimeBetweenAndTeamAndMessageContainingOrderByTimeDesc(long epochBefore, long epochAfter, String team, String keyword, Pageable pageable);


    List<Log> findByTeamOrderByTimeDesc(String team);
    Page<Log> findByTeamOrderByTimeDesc(String team, Pageable pageable);
    List<Log> findByTeamAndMessageContainingOrderByTimeDesc(String team, String keyword);
    Page<Log> findByTeamAndMessageContainingOrderByTimeDesc(String team, String keyword, Pageable pageable);

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
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?1 AND l.message LIKE %?2% ORDER BY time DESC")
    List<Log> findBySubjectAndMessageContainingOrderByTimeDesc(String subject, String keyword);
    @Query("SELECT l FROM Log l " +
            "JOIN Subject s ON l.session.team.subject.acronym = s.acronym " +
            "WHERE s.acronym = ?1 AND l.message LIKE %?2% ORDER BY time DESC")
    Page<Log> findBySubjectAndMessageContainingOrderByTimeDesc(String subject, String keyword, Pageable pageable);
}
