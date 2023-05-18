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
    @Column(name = "name", unique = true)
    private String name;
    @ManyToMany(mappedBy = "factors", cascade = CascadeType.REMOVE)
    private List<FactorAccess> factorAccesses;

    public Factor() { }

    public Factor(String id) {
        this.id = id;
    }

    public Factor(String id, String name) {
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
        if (!(o instanceof Factor factor)) return false;
        return Objects.equals(getId(), factor.getId()) && Objects.equals(getName(), factor.getName()) && Objects.equals(factorAccesses, factor.factorAccesses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), factorAccesses);
    }

    @Override
    public String toString() {
        return "Factor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
