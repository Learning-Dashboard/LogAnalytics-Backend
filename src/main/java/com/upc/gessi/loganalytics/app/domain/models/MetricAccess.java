package com.upc.gessi.loganalytics.app.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "MetricAccess")
public class MetricAccess extends Log {

    @Column (name = "historic", nullable = false)
    private boolean historic;
    @Column (name = "viewFormat", nullable = false)
    private String viewFormat;
    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable (
        name = "metric_access_metric",
        joinColumns = {
            @JoinColumn (name = "metric_access_time"),
            @JoinColumn (name = "metric_access_team")
        },
        inverseJoinColumns = @JoinColumn (name = "metric_id")
    ) @JsonIgnore
    private List<Metric> metrics;

    public MetricAccess() { }

    public MetricAccess(long time, String team, String message, String page, boolean historic, String viewFormat, List<Metric> metrics) {
        super(time, team, message, page);
        this.historic = historic;
        this.viewFormat = viewFormat;
        this.metrics = metrics;
    }

    public MetricAccess(long time, String team, String message, String page, Session session, boolean historic, String viewFormat, List<Metric> metrics) {
        super(time, team, message, page, session);
        this.historic = historic;
        this.viewFormat = viewFormat;
        this.metrics = metrics;
    }

    public boolean isHistoric() {
        return historic;
    }

    public void setHistoric(boolean historic) {
        this.historic = historic;
    }

    public String getViewFormat() {
        return viewFormat;
    }

    public void setViewFormat(String viewFormat) {
        this.viewFormat = viewFormat;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    @Override
    public String toString() {
        return "MetricAccess{" +
                "historic=" + historic +
                ", viewFormat='" + viewFormat + '\'' +
                ", metrics=" + metrics +
                "} " + super.toString();
    }
}
