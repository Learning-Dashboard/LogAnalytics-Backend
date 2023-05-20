package com.upc.gessi.loganalytics.app.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "Category")
public class Category implements Serializable {

    @Id @Column (name = "id", nullable = false)
    private String id;

    @OneToMany (mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InternalMetric> internalMetrics;

    public Category() {
    }

    public Category(String id) {
        this.id = id;
    }

    public Category(String id, List<InternalMetric> internalMetrics) {
        this.id = id;
        this.internalMetrics = internalMetrics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<InternalMetric> getInternalMetrics() {
        return internalMetrics;
    }

    public void setInternalMetrics(List<InternalMetric> internalMetrics) {
        this.internalMetrics = internalMetrics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category group)) return false;
        return Objects.equals(getId(), group.getId()) &&
            Objects.equals(getInternalMetrics(), group.getInternalMetrics());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInternalMetrics());
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                '}';
    }
}
