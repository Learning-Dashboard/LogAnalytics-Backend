package com.upc.gessi.loganalytics.app.rest.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upc.gessi.loganalytics.app.domain.models.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EvaluationDTO { //Constructor, getters, setters, hashcode y equals

    private String name;
    private String subject;
    private String team;
    private String date;
    @JsonIgnore
    private InternalMetric internalMetric;
    private double value;
    private boolean groupable;
    private String category;
    private Map<String, Double> entities;

    public EvaluationDTO(Evaluation e) {
        this.date = e.getDate();
        this.internalMetric = e.getInternalMetric();
        this.value = e.getValue();
        String cName = e.getInternalMetric().getControllerName();
        if (cName == null) this.name = e.getInternalMetric().getName();
        else this.name = cName;
        this.entities = new HashMap<>();
        this.groupable = internalMetric.isGroupable();
        Category c = internalMetric.getCategory();
        if (c != null) this.category = internalMetric.getCategory().getId();
    }

    public EvaluationDTO(SubjectEvaluation e) {
        this.date = e.getDate();
        this.subject = e.getSubject();
        this.internalMetric = e.getInternalMetric();
        this.value = e.getValue();
        String cName = e.getInternalMetric().getControllerName();
        if (cName == null) this.name = e.getInternalMetric().getName();
        else this.name = cName;
        this.entities = new HashMap<>();
        this.groupable = internalMetric.isGroupable();
        Category c = internalMetric.getCategory();
        if (c != null) this.category = internalMetric.getCategory().getId();
    }

    public EvaluationDTO(TeamEvaluation e) {
        this.date = e.getDate();
        this.team = e.getTeam();
        this.internalMetric = e.getInternalMetric();
        this.value = e.getValue();
        String cName = e.getInternalMetric().getControllerName();
        if (cName == null) this.name = e.getInternalMetric().getName();
        else this.name = cName;
        this.entities = new HashMap<>();
        this.groupable = internalMetric.isGroupable();
        Category c = internalMetric.getCategory();
        if (c != null) this.category = internalMetric.getCategory().getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
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

    public boolean isGroupable() {
        return groupable;
    }

    public void setGroupable(boolean groupable) {
        this.groupable = groupable;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        return Double.compare(that.getValue(), getValue()) == 0 && isGroupable() == that.isGroupable() && Objects.equals(getName(), that.getName()) && Objects.equals(getSubject(), that.getSubject()) && Objects.equals(getTeam(), that.getTeam()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getInternalMetric(), that.getInternalMetric()) && Objects.equals(getCategory(), that.getCategory()) && Objects.equals(getEntities(), that.getEntities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSubject(), getTeam(), getDate(), getInternalMetric(), getValue(), isGroupable(), getCategory(), getEntities());
    }

    @Override
    public String toString() {
        return "EvaluationDTO{" +
                "name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", team='" + team + '\'' +
                ", date='" + date + '\'' +
                ", value=" + value +
                ", groupable=" + groupable +
                ", category='" + category + '\'' +
                ", entities=" + entities +
                '}';
    }
}
