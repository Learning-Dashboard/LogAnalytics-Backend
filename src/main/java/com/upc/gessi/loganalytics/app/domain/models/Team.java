package com.upc.gessi.loganalytics.app.domain.models;

import com.upc.gessi.loganalytics.app.domain.models.pkey.TeamPrimaryKey;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;

@Entity
@Table(name = "Team")
@IdClass(TeamPrimaryKey.class)
public class Team {

    @Id @Column (name = "id", nullable = false)
    private String id;

    @Id @Column (name = "semester", nullable = false)
    private String semester;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject", nullable = false)
    private Subject subject;

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

    @Override
    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", semester='" + semester + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
