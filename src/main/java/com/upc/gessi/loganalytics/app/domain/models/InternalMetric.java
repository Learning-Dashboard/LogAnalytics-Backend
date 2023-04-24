package com.upc.gessi.loganalytics.app.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "InternalMetric")
public class InternalMetric implements Serializable {

    @Id @Column (name = "name", nullable = false)
    private String name;
    @Column (name = "param", nullable = true)
    private String param;
    @Column (name = "controller", nullable = true)
    private String controller;

    @OneToMany (mappedBy = "internalMetric", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Evaluation> evaluations;

    @OneToMany (mappedBy = "internalMetric", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TeamEvaluation> teamEvaluations;

    @OneToMany (mappedBy = "internalMetric", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SubjectEvaluation> subjectEvaluations;

    public InternalMetric() { }

    public InternalMetric(String name) {
        this.name = name;
    }

    public InternalMetric(String name, String param, String controller) {
        this.name = name;
        this.param = param;
        this.controller = controller;
    }

    public InternalMetric(String name, List<Evaluation> evaluations) {
        this.name = name;
        this.evaluations = evaluations;
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

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
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
    public String toString() {
        return "InternalMetric{" +
                "name='" + name + '\'' +
                ", param='" + param + '\'' +
                ", controller='" + controller + '\'' +
                '}';
    }
}
