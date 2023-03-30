package com.upc.gessi.loganalytics.app.domain.models.pkey;

import java.io.Serializable;
import java.util.Objects;

public class SessionPrimaryKey implements Serializable {

    private String teamId;
    private long startTimestamp;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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
        return getStartTimestamp() == that.getStartTimestamp() && getTeamId().equals(that.getTeamId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeamId(), getStartTimestamp());
    }
}
