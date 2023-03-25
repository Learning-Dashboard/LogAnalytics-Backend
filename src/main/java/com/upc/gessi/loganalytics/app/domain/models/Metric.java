package com.upc.gessi.loganalytics.app.domain.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Metric")
public class Metric implements Serializable {

    @Id
    @Column (name = "id", nullable = false)
    private String id;
    @ManyToMany (mappedBy = "metrics")
    private List<MetricAccess> metricAccesses;

    public Metric() { }

    public Metric(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "id='" + id + '\'' +
                '}';
    }
}
