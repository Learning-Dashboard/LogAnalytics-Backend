package com.upc.gessi.loganalytics.app.domain.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Factor factor)) return false;
        return getId().equals(factor.getId()) && Objects.equals(factorAccesses, factor.factorAccesses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), factorAccesses);
    }

    @Override
    public String toString() {
        return "Factor{" +
                "id='" + id + '\'' +
                '}';
    }
}
