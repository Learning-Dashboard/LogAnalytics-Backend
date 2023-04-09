package com.upc.gessi.loganalytics.app.domain.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Metric")
public class Metric implements Serializable {

    @Id @Column (name = "id", nullable = false)
    private String id;
    @ManyToMany (mappedBy = "metrics", cascade = CascadeType.ALL)
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Metric)) return false;
        Metric metric = (Metric) o;
        return getId().equals(metric.getId()) && Objects.equals(metricAccesses, metric.metricAccesses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), metricAccesses);
    }

    @Override
    public String toString() {
        return "Metric{" +
                "id='" + id + '\'' +
                '}';
    }
}
