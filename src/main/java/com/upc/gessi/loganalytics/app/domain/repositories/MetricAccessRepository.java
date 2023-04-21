package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.MetricAccess;
import com.upc.gessi.loganalytics.app.domain.models.pkey.LogPrimaryKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricAccessRepository extends CrudRepository<MetricAccess, LogPrimaryKey> {

    List<MetricAccess> findByTeamAndMetricsId(String team, String metric);
    List<MetricAccess> findByTeamAndViewFormat(String team, String viewFormat);
    List<MetricAccess> findByHistoricAndTeam(boolean historic, String team);
}
