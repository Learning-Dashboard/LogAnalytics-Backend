package com.upc.gessi.loganalytics.app.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "InternalMetric")
public abstract class InternalMetric implements Serializable {

    @Id @Column (name = "name", nullable = false)
    private String name;

    @OneToMany (mappedBy = "internalMetric", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Evaluation> evaluations;

    public InternalMetric() { }

    public InternalMetric(String name) {
        this.name = name;
    }

    public InternalMetric(String name, List<Evaluation> evaluations) {
        this.name = name;
        this.evaluations = evaluations;
    }

    public abstract double evaluate(Team team);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public String toString() {
        return "InternalMetric{" +
                "name='" + name + '\'' +
                '}';
    }
}
