package com.upc.gessi.loganalytics.app.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class UserlessInternalMetric extends InternalMetric {
    @Column (name = "userlessId")
    private String userlessId;
    @Column (name = "userlessName")
    private String userlessName;

    public UserlessInternalMetric() {

    }

    public UserlessInternalMetric(String userlessId, String userlessName) {
        this.userlessId = userlessId;
        this.userlessName = userlessName;
    }

    public UserlessInternalMetric(String id, String name, String userlessId, String userlessName) {
        super(id, name);
        this.userlessId = userlessId;
        this.userlessName = userlessName;
    }

    public UserlessInternalMetric(String id, String name, String param,
        String paramName, String controller, String controllerName, boolean groupable,
        String userlessId, String userlessName) {
        super(id, name, param, paramName, controller, controllerName, groupable);
        this.userlessId = userlessId;
        this.userlessName = userlessName;
    }

    public UserlessInternalMetric(String id, String name, String param,
        String paramName, String controller, String controllerName, boolean groupable,
        List<String> teams, String userlessId, String userlessName) {
        super(id, name, param, paramName, controller, controllerName, groupable, teams);
        this.userlessId = userlessId;
        this.userlessName = userlessName;
    }

    public String getUserlessId() {
        return userlessId;
    }

    public void setUserlessId(String userlessId) {
        this.userlessId = userlessId;
    }

    public String getUserlessName() {
        return userlessName;
    }

    public void setUserlessName(String userlessName) {
        this.userlessName = userlessName;
    }

    @Override
    public String toString() {
        return "UserlessInternalMetric{" +
                "userlessId='" + userlessId + '\'' +
                ", userlessName='" + userlessName + '\'' +
                "} " + super.toString();
    }
}
