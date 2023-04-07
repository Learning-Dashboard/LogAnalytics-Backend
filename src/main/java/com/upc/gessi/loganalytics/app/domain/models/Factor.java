package com.upc.gessi.loganalytics.app.domain.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Factor")
public class Factor implements Serializable {

    @Id @Column(name = "id", nullable = false)
    private String id;
    @ManyToMany(mappedBy = "factors", cascade = CascadeType.ALL)
    private List<FactorAccess> factorAccesses;

    public Factor() { }

    public Factor(String id) {
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
        return "Factor{" +
                "id='" + id + '\'' +
                '}';
    }
}
