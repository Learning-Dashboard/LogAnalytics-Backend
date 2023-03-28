package com.upc.gessi.loganalytics.app.domain.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Indicator")
public class Indicator implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @ManyToMany(mappedBy = "indicators")
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
    public String toString() {
        return "Indicator{" +
                "id='" + id + '\'' +
                '}';
    }
}
