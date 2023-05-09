package com.upc.gessi.loganalytics.app.rest.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upc.gessi.loganalytics.app.domain.models.Evaluation;
import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;

import java.util.Map;
import java.util.Objects;

public class EvaluationDTO { //Constructor, getters, setters, hashcode y equals

    private String name;
    private String date;
    @JsonIgnore
    private InternalMetric internalMetric;
    private double value;
    private Map<String, Double> entities;

    public EvaluationDTO(Evaluation e) {
        this.date = e.getDate();
        this.internalMetric = e.getInternalMetric();
        this.value = e.getValue();
        String cName = e.getInternalMetric().getControllerName();
        if (cName == null) this.name = e.getInternalMetric().getName();
        else this.name = cName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public InternalMetric getInternalMetric() {
        return internalMetric;
    }

    public void setInternalMetric(InternalMetric internalMetric) {
        this.internalMetric = internalMetric;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Map<String, Double> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, Double> entities) {
        this.entities = entities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvaluationDTO)) return false;
        EvaluationDTO that = (EvaluationDTO) o;
        return Double.compare(that.getValue(), getValue()) == 0 && getDate().equals(that.getDate()) && getInternalMetric().equals(that.getInternalMetric()) && Objects.equals(getEntities(), that.getEntities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getInternalMetric(), getValue(), getEntities());
    }
}
