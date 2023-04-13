package com.upc.gessi.loganalytics.app.domain.models.pkey;

import com.upc.gessi.loganalytics.app.domain.models.InternalMetric;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

public class SubjectEvaluationPrimaryKey implements Serializable {
    private String date;
    private InternalMetric internalMetric;
    private String subject;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectEvaluationPrimaryKey)) return false;
        SubjectEvaluationPrimaryKey that = (SubjectEvaluationPrimaryKey) o;
        return getDate().equals(that.getDate()) && getInternalMetric().equals(that.getInternalMetric()) && getSubject().equals(that.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getInternalMetric(), getSubject());
    }
}
