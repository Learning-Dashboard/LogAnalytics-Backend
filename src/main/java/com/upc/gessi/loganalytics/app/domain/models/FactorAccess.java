package com.upc.gessi.loganalytics.app.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "FactorAccess")
public class FactorAccess extends Log {

    @Column(name = "historic", nullable = false)
    private boolean historic;
    @Column (name = "viewFormat", nullable = false)
    private String viewFormat;
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "factor_access_factor",
            joinColumns = {
                    @JoinColumn(name = "factor_access_time", referencedColumnName = "time"),
                    @JoinColumn (name = "factor_access_session", referencedColumnName = "session_id")
            },
            inverseJoinColumns = @JoinColumn (name = "factor_id")
    ) @JsonIgnore
    private List<Factor> factors;

    public FactorAccess() { }

    public FactorAccess(long time, String team, String message, String page, boolean historic, String viewFormat, List<Factor> factors) {
        super(time, team, message, page);
        this.historic = historic;
        this.viewFormat = viewFormat;
        this.factors = factors;
    }

    public FactorAccess(long time, String team, String message, String page, Session session, boolean historic, String viewFormat, List<Factor> factors) {
        super(time, team, message, page, session);
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

    public List<Factor> getFactors() {
        return factors;
    }

    public void setFactors(List<Factor> factors) {
        this.factors = factors;
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
