package com.upc.gessi.loganalytics.app.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "InternalMetric")
public class InternalMetric implements Serializable {

    @Id @Column (name = "id", nullable = false)
    private String id;
    @Column (name = "name", nullable = false)
    private String name;
    @Column (name = "param")
    private String param;
    @Column (name = "controller")
    private String controller;
    @Column (name = "team")
    private String team;

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

    public InternalMetric(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public InternalMetric(String id, String name, String param, String controller) {
        this.id = id;
        this.name = name;
        this.param = param;
        this.controller = controller;
    }

    public InternalMetric(String id, String name, String param, String controller, String team) {
        this.id = id;
        this.name = name;
        this.param = param;
        this.controller = controller;
        this.team = team;
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

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", param='" + param + '\'' +
                ", controller='" + controller + '\'' +
                ", team='" + team + '\'' +
                '}';
    }
}
