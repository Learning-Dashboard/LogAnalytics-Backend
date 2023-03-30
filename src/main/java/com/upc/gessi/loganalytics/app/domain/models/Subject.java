package com.upc.gessi.loganalytics.app.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Subject")
public class Subject implements Serializable {

    @Id @Column (name = "acronym", nullable = false)
    private String acronym;
    @OneToMany (mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams;

    public Subject() { }

    public Subject(String acronym) {
        this.acronym = acronym;
    }

    public Subject(String acronym, List<Team> teams) {
        this.acronym = acronym;
        this.teams = teams;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "acronym='" + acronym + '\'' +
                '}';
    }
}
