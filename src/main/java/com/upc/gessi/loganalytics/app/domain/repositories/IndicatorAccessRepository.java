package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.IndicatorAccess;
import com.upc.gessi.loganalytics.app.domain.models.pkey.LogPrimaryKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndicatorAccessRepository extends CrudRepository<IndicatorAccess, LogPrimaryKey> {
    List<IndicatorAccess> findByTeamAndIndicatorsIdAndTimeBetween(String team, String indicator, long timeBefore, long timeAfter);
    List<IndicatorAccess> findByTeamAndViewFormatAndTimeBetween(String team, String viewFormat, long timeBefore, long timeAfter);
    List<IndicatorAccess> findByHistoricAndTeamAndTimeBetween(boolean historic, String team, long timeBefore, long timeAfter);
}
