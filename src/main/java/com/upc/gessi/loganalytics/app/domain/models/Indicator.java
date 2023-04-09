package com.upc.gessi.loganalytics.app.domain.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Indicator")
public class Indicator implements Serializable {

    @Id @Column(name = "id", nullable = false)
    private String id;
    @ManyToMany(mappedBy = "indicators", cascade = CascadeType.ALL)
    private List<IndicatorAccess> factorAccesses;

    public Indicator() { }

    public Indicator(String id) {
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
        if (!(o instanceof Indicator)) return false;
        Indicator indicator = (Indicator) o;
        return getId().equals(indicator.getId()) && Objects.equals(factorAccesses, indicator.factorAccesses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), factorAccesses);
    }

    @Override
    public String toString() {
        return "Indicator{" +
                "id='" + id + '\'' +
                '}';
    }
}
