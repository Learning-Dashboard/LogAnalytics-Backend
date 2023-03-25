package com.upc.gessi.loganalytics.app.domain.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "FactorAccess")
public class FactorAccess extends Log {

    @Column(name = "historic", nullable = false)
    private boolean historic;
    @Column (name = "viewFormat", nullable = false)
    private String viewFormat;
    @ManyToMany
    @JoinTable(
            name = "factor_access_factor",
            joinColumns = {
                    @JoinColumn(name = "factor_access_time"),
                    @JoinColumn (name = "factor_access_team")
            },
            inverseJoinColumns = @JoinColumn (name = "factor_id")
    )
    private List<Factor> factors;

    public FactorAccess(long time, String team, String message, String page, boolean historic, String viewFormat, List<Factor> factors) {
        super(time, team, message, page);
        this.historic = historic;
        this.viewFormat = viewFormat;
        this.factors = factors;
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

    @Override
    public String toString() {
        return "FactorAccess{" +
                "historic=" + historic +
                ", viewFormat='" + viewFormat + '\'' +
                ", factors=" + factors +
                "} " + super.toString();
    }
}
