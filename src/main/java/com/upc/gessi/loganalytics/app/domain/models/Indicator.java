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
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "indicators", cascade = CascadeType.REMOVE)
    private List<IndicatorAccess> indicatorAccesses;

    public Indicator() { }

    public Indicator(String id) {
        this.id = id;
    }

    public Indicator(String id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Indicator indicator)) return false;
        return Objects.equals(getId(), indicator.getId()) && Objects.equals(getName(), indicator.getName()) && Objects.equals(indicatorAccesses, indicator.indicatorAccesses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), indicatorAccesses);
    }

    @Override
    public String toString() {
        return "Indicator{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
