package com.upc.gessi.loganalytics.app.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "Session")
public class Session {

    @Id @Column (name = "id", nullable = false)
    private String id;
    @Column (name = "start_timestamp", nullable = false)
    private long startTimestamp;
    @Column (name = "end_timestamp")
    private long endTimestamp;
    @ManyToOne(fetch = FetchType.EAGER) @JsonIgnore
    @JoinColumns({
            @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false),
            @JoinColumn(name = "team_semester", referencedColumnName = "semester" ,nullable = false)
    })
    private Team team;
    @Column (name = "duration")
    private double duration;
    @Column (name = "n_interactions")
    private int nInteractions;
    @OneToMany (mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Log> logs;

    public Session() { }

    public Session(String id, Team team, long startTimestamp) {
        this.id = id;
        this.team = team;
        this.startTimestamp = startTimestamp;
    }

    public Session(String id, Team team, long startTimestamp, long endTimestamp, double duration, int nInteractions) {
        this.id = id;
        this.team = team;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.duration = duration;
        this.nInteractions = nInteractions;
    }

    public Session(String id, Team team, long startTimestamp, long endTimestamp, double duration, int nInteractions, List<Log> logs) {
        this.id = id;
        this.team = team;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.duration = duration;
        this.nInteractions = nInteractions;
        this.logs = logs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getnInteractions() {
        return nInteractions;
    }

    public void setnInteractions(int nInteractions) {
        this.nInteractions = nInteractions;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                ", team=" + team +
                ", startTimestamp=" + startTimestamp +
                ", endTimestamp=" + endTimestamp +
                ", duration=" + duration +
                ", nInteractions=" + nInteractions +
                '}';
    }
}
