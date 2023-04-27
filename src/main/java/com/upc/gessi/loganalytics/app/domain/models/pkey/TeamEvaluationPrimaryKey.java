package com.upc.gessi.loganalytics.app.domain.models.pkey;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;

import java.io.Serializable;
import java.util.Objects;

public class TeamEvaluationPrimaryKey implements Serializable {
    private String date;
    private InternalMetric internalMetric;
    private String team;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public InternalMetric getInternalMetric() {
        return internalMetric;
    }

    public void setInternalMetric(InternalMetric internalMetric) {
        this.internalMetric = internalMetric;
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
        if (!(o instanceof TeamEvaluationPrimaryKey that)) return false;
        return getDate().equals(that.getDate()) && getInternalMetric().equals(that.getInternalMetric()) && getTeam().equals(that.getTeam());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getInternalMetric(), getTeam());
    }
}
