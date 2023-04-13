package com.upc.gessi.loganalytics.app.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "QModelAccess")
public class QModelAccess extends Log {

    @Column (name = "viewFormat", nullable = false)
    private String viewFormat;

    public QModelAccess() { }

    public QModelAccess(long time, String team, String message, String page, String viewFormat) {
        super(time, team, message, page);
        this.viewFormat = viewFormat;
    }

    public QModelAccess(long time, String team, String message, String page, Session session, String viewFormat) {
        super(time, team, message, page, session);
        this.viewFormat = viewFormat;
    }

    public String getViewFormat() {
        return viewFormat;
    }

    public void setViewFormat(String viewFormat) {
        this.viewFormat = viewFormat;
    }

    @Override
    public String toString() {
        return "QModelAccess{" +
                "viewFormat='" + viewFormat + '\'' +
                "} " + super.toString();
    }
}
