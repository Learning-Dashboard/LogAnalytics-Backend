package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.MetricAccess;
import com.upc.gessi.loganalytics.app.domain.models.pkey.LogPrimaryKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricAccessRepository extends CrudRepository<MetricAccess, LogPrimaryKey> {

    List<MetricAccess> findByTeamAndMetricsIdAndTimeBetween(String team, String metric, long timeBefore, long timeAfter);
    List<MetricAccess> findByTeamAndViewFormatAndTimeBetween(String team, String viewFormat, long timeBefore, long timeAfter);
    List<MetricAccess> findByHistoricAndTeamAndTimeBetween(boolean historic, String team, long timeBefore, long timeAfter);
}
