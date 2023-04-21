package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.FactorAccess;
import com.upc.gessi.loganalytics.app.domain.models.pkey.LogPrimaryKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactorAccessRepository extends CrudRepository<FactorAccess, LogPrimaryKey> {
    List<FactorAccess> findByTeamAndFactorsId(String team, String factor);
    List<FactorAccess> findByTeamAndViewFormat(String team, String viewFormat);
    List<FactorAccess> findByHistoricAndTeam(boolean historic, String team);
}
