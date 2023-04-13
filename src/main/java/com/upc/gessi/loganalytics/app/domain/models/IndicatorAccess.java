package com.upc.gessi.loganalytics.app.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "IndicatorAccess")
public class IndicatorAccess extends Log {

    @Column(name = "historic", nullable = false)
    private boolean historic;
    @Column (name = "viewFormat", nullable = false)
    private String viewFormat;
    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "indicator_access_indicator",
            joinColumns = {
                    @JoinColumn(name = "indicator_access_time"),
                    @JoinColumn (name = "indicator_access_team")
            },
            inverseJoinColumns = @JoinColumn (name = "indicator_id")
    ) @JsonIgnore
    private List<Indicator> indicators;

    public IndicatorAccess() { }

    public IndicatorAccess(long time, String team, String message, String page, boolean historic, String viewFormat, List<Indicator> indicators) {
        super(time, team, message, page);
        this.historic = historic;
        this.viewFormat = viewFormat;
        this.indicators = indicators;
    }

    public IndicatorAccess(long time, String team, String message, String page, Session session, boolean historic, String viewFormat, List<Indicator> indicators) {
        super(time, team, message, page, session);
        this.historic = historic;
        this.viewFormat = viewFormat;
        this.indicators = indicators;
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

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    @Override
    public String toString() {
        return "IndicatorAccess{" +
                "historic=" + historic +
                ", viewFormat='" + viewFormat + '\'' +
                ", indicators=" + indicators +
                "} " + super.toString();
    }
}
