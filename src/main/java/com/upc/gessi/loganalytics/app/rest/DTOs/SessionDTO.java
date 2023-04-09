package com.upc.gessi.loganalytics.app.rest.DTOs;

import com.upc.gessi.loganalytics.app.domain.models.Session;
import com.upc.gessi.loganalytics.app.domain.models.Team;

import java.util.Objects;

public class SessionDTO {
    private String id;
    private long startTimestamp;
    private long endTimestamp;
    private Team team;
    private double duration;
    private int nInteractions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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

    public SessionDTO(Session session) {
        this.id = session.getId();
        this.startTimestamp = session.getStartTimestamp();
        this.endTimestamp = session.getEndTimestamp();
        this.team = session.getTeam();
        this.duration = session.getDuration();
        this.nInteractions = session.getnInteractions();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionDTO)) return false;
        SessionDTO that = (SessionDTO) o;
        return getStartTimestamp() == that.getStartTimestamp() && getEndTimestamp() == that.getEndTimestamp() && Double.compare(that.getDuration(), getDuration()) == 0 && getnInteractions() == that.getnInteractions() && getId().equals(that.getId()) && getTeam().equals(that.getTeam());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartTimestamp(), getEndTimestamp(), getTeam(), getDuration(), getnInteractions());
    }
}
