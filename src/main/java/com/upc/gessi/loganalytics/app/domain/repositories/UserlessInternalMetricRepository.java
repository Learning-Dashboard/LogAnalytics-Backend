package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.UserlessInternalMetric;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserlessInternalMetricRepository extends CrudRepository<UserlessInternalMetric, String> {
    boolean existsByUserlessName(String noUserName);
}
