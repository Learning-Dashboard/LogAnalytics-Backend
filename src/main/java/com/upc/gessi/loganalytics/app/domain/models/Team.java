package com.upc.gessi.loganalytics.app.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upc.gessi.loganalytics.app.domain.models.pkey.TeamPrimaryKey;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Team")
@IdClass(TeamPrimaryKey.class)
public class Team {

    @Id @Column (name = "id", nullable = false)
    private String id;

    @Id @Column (name = "semester", nullable = false)
    private String semester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject", referencedColumnName = "acronym", nullable = false)
    private Subject subject;

    @OneToMany (mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Session> sessions;

    public Team() { }

    public Team(String id, Subject subject) {
        this.id = id;
        this.subject = subject;
    }

    public Team(String id, String semester, Subject subject) {
        this.id = id;
        this.subject = subject;
        this.semester = semester;
    }

    public Team(String id, String semester, Subject subject, List<Session> sessions) {
        this.id = id;
        this.semester = semester;
        this.subject = subject;
        this.sessions = sessions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return getId().equals(team.getId()) && getSemester().equals(team.getSemester()) && getSubject().equals(team.getSubject()) && Objects.equals(getSessions(), team.getSessions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSemester(), getSubject(), getSessions());
    }

    @Override
    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", semester='" + semester + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
