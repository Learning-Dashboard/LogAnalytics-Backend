package com.upc.gessi.loganalytics.app.domain.models.pkey;

import com.upc.gessi.loganalytics.app.domain.models.Team;

import java.io.Serializable;
import java.util.Objects;

public class SessionPrimaryKey implements Serializable {

    private Team team;
    private long startTimestamp;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionPrimaryKey that)) return false;
        return getStartTimestamp() == that.getStartTimestamp() && getTeam().equals(that.getTeam());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeam(), getStartTimestamp());
    }
}
