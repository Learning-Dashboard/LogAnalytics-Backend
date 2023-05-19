package com.upc.gessi.loganalytics.app.domain.repositories;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalMetricRepository extends CrudRepository<InternalMetric, String> {
    boolean existsByParamName(String s);
}
