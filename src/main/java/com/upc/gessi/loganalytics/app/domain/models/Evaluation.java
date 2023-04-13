package com.upc.gessi.loganalytics.app.domain.models;

import com.upc.gessi.loganalytics.app.domain.models.pkey.EvaluationPrimaryKey;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Evaluation")
@IdClass(EvaluationPrimaryKey.class)
@Inheritance(strategy = InheritanceType.JOINED)
public class Evaluation {

    @Id @Column (name = "date", nullable = false)
    private String date;
    @Id @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "internalMetric", referencedColumnName = "name", nullable = false)
    private InternalMetric internalMetric;

    @Column (name = "value", nullable = false)
    private double value;

    public Evaluation() { }

    public Evaluation(String date, InternalMetric internalMetric) {
        this.date = date;
        this.internalMetric = internalMetric;
    }

    public Evaluation(String date, InternalMetric internalMetric, double value) {
        this.date = date;
        this.internalMetric = internalMetric;
        this.value = value;
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
        return "Evaluation{" +
                "date=" + date +
                ", internalMetric=" + internalMetric +
                ", value=" + value +
                '}';
    }
}
