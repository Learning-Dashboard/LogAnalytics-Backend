package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.FactorAccess;
import com.upc.gessi.loganalytics.app.domain.models.pkey.LogPrimaryKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactorAccessRepository extends CrudRepository<FactorAccess, LogPrimaryKey> {
    List<FactorAccess> findByTeamAndFactorsIdAndTimeBetween(String team, String factor, long timeBefore, long timeAfter);
    List<FactorAccess> findByTeamAndViewFormatAndTimeBetween(String team, String viewFormat, long timeBefore, long timeAfter);
    List<FactorAccess> findByHistoricAndTeamAndTimeBetween(boolean historic, String team, long timeBefore, long timeAfter);
}
