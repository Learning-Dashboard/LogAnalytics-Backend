package com.upc.gessi.loganalytics.app.domain.models.pkey;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;

import java.io.Serializable;
import java.util.Objects;

public class EvaluationPrimaryKey implements Serializable {
    private String date;
    private InternalMetric internalMetric;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvaluationPrimaryKey that)) return false;
        return getDate().equals(that.getDate()) && getInternalMetric().equals(that.getInternalMetric());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getInternalMetric());
    }
}
