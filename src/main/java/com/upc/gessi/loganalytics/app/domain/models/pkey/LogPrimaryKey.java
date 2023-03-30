package com.upc.gessi.loganalytics.app.domain.models.pkey;

import java.io.Serializable;
import java.util.Objects;

public class LogPrimaryKey implements Serializable {
    private long time;
    private String team;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogPrimaryKey that)) return false;
        return getTime() == that.getTime() && getTeam().equals(that.getTeam());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTime(), getTeam());
    }
}
