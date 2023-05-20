package com.upc.gessi.loganalytics.app.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "InternalMetric", uniqueConstraints =
    @UniqueConstraint(columnNames = {"param", "controller"}))
@Inheritance(strategy = InheritanceType.JOINED)
public class InternalMetric implements Serializable {

    @Id @Column (name = "id", nullable = false)
    private String id;
    @Column (name = "name", nullable = false, unique = true)
    private String name;
    @Column (name = "param")
    private String param;
    @Column (name = "paramName")
    private String paramName;
    @Column (name = "controller")
    private String controller;
    @Column (name = "controllerName")
    private String controllerName;
    @Column (name = "groupable")
    private boolean groupable;
    @Column (name = "teams")
    private List<String> teams;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "category", referencedColumnName = "id")
    private Category category;

    @OneToMany (mappedBy = "internalMetric", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Evaluation> evaluations;

    @OneToMany (mappedBy = "internalMetric", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TeamEvaluation> teamEvaluations;

    @OneToMany (mappedBy = "internalMetric", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SubjectEvaluation> subjectEvaluations;

    public InternalMetric() { }

    public InternalMetric(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public InternalMetric(String id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public InternalMetric(String id, String name, String param, String paramName,
        String controller, String controllerName, boolean groupable, Category category) {
        this.id = id;
        this.name = name;
        this.param = param;
        this.paramName = paramName;
        this.controller = controller;
        this.controllerName = controllerName;
        this.groupable = groupable;
        this.category = category;
    }

    public InternalMetric(String id, String name, String param, String paramName,
        String controller, String controllerName, boolean groupable, Category category, List<String> teams) {
        this.id = id;
        this.name = name;
        this.param = param;
        this.paramName = paramName;
        this.controller = controller;
        this.controllerName = controllerName;
        this.groupable = groupable;
        this.teams = teams;
        this.category = category;
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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public boolean isGroupable() {
        return groupable;
    }

    public void setGroupable(boolean groupable) {
        this.groupable = groupable;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public List<TeamEvaluation> getTeamEvaluations() {
        return teamEvaluations;
    }

    public void setTeamEvaluations(List<TeamEvaluation> teamEvaluations) {
        this.teamEvaluations = teamEvaluations;
    }

    public List<SubjectEvaluation> getSubjectEvaluations() {
        return subjectEvaluations;
    }

    public void setSubjectEvaluations(List<SubjectEvaluation> subjectEvaluations) {
        this.subjectEvaluations = subjectEvaluations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternalMetric)) return false;
        InternalMetric that = (InternalMetric) o;
        return isGroupable() == that.isGroupable() && Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getParam(), that.getParam()) && Objects.equals(getParamName(), that.getParamName()) && Objects.equals(getController(), that.getController()) && Objects.equals(getControllerName(), that.getControllerName()) && Objects.equals(getTeams(), that.getTeams()) && Objects.equals(getCategory(), that.getCategory()) && Objects.equals(getEvaluations(), that.getEvaluations()) && Objects.equals(getTeamEvaluations(), that.getTeamEvaluations()) && Objects.equals(getSubjectEvaluations(), that.getSubjectEvaluations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getParam(), getParamName(), getController(), getControllerName(), isGroupable(), getTeams(), getCategory(), getEvaluations(), getTeamEvaluations(), getSubjectEvaluations());
    }

    @Override
    public String toString() {
        return "InternalMetric{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", param='" + param + '\'' +
                ", paramName='" + paramName + '\'' +
                ", controller='" + controller + '\'' +
                ", controllerName='" + controllerName + '\'' +
                ", groupable=" + groupable +
                ", teams=" + teams +
                '}';
    }
}
