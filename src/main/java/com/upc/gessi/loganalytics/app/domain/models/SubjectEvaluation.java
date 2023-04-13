package com.upc.gessi.loganalytics.app.domain.models;

import com.upc.gessi.loganalytics.app.domain.models.pkey.SubjectEvaluationPrimaryKey;
import jakarta.persistence.*;

@Entity
@Table(name = "SubjectEvaluation")
@IdClass(SubjectEvaluationPrimaryKey.class)
public class SubjectEvaluation {

    @Id @Column (name = "date", nullable = false)
    private String date;
    @Id @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "internalMetric", referencedColumnName = "name", nullable = false)
    private InternalMetric internalMetric;
    @Id @Column (name = "subject", nullable = false)
    private String subject;

    @Column (name = "value", nullable = false)
    private double value;

    public SubjectEvaluation() {
    }

    public SubjectEvaluation(String date, InternalMetric internalMetric, String subject) {
        this.date = date;
        this.internalMetric = internalMetric;
        this.subject = subject;
    }

    public SubjectEvaluation(String date, InternalMetric internalMetric, String subject, double value) {
        this.date = date;
        this.internalMetric = internalMetric;
        this.subject = subject;
        this.value = value;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SubjectEvaluation{" +
                "date='" + date + '\'' +
                ", internalMetric=" + internalMetric +
                ", subject='" + subject + '\'' +
                ", value=" + value +
                '}';
    }
}
