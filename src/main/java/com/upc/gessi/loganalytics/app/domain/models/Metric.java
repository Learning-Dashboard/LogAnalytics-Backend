package com.upc.gessi.loganalytics.app.domain.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Metric")
public class Metric implements Serializable {

    @Id @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "noUserId")
    private String noUserId;
    @Column(name = "noUserName")
    private String noUserName;
    @ManyToMany (mappedBy = "metrics", cascade = CascadeType.REMOVE)
    private List<MetricAccess> metricAccesses;

    public Metric() { }

    public Metric(String id) {
        this.id = id;
    }

    public Metric(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Metric(String id, String name, String noUserId, String noUserName) {
        this.id = id;
        this.name = name;
        this.noUserId = noUserId;
        this.noUserName = noUserName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoUserId() {
        return noUserId;
    }

    public void setNoUserId(String noUserId) {
        this.noUserId = noUserId;
    }

    public String getNoUserName() {
        return noUserName;
    }

    public void setNoUserName(String noUserName) {
        this.noUserName = noUserName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Metric metric)) return false;
        return Objects.equals(getId(), metric.getId()) && Objects.equals(getName(), metric.getName()) && Objects.equals(getNoUserId(), metric.getNoUserId()) && Objects.equals(getNoUserName(), metric.getNoUserName()) && Objects.equals(metricAccesses, metric.metricAccesses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getNoUserId(), getNoUserName(), metricAccesses);
    }

    @Override
    public String toString() {
        return "Metric{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", noUserId='" + noUserId + '\'' +
                ", noUserName='" + noUserName + '\'' +
                '}';
    }
}
