package com.upc.gessi.loganalytics.app.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.List;
import java.util.Objects;

@Entity
public class UserlessInternalMetric extends InternalMetric {
    @Column (name = "userlessId")
    private String userlessId;
    @Column (name = "userlessName")
    private String userlessName;

    public UserlessInternalMetric() {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserlessInternalMetric that)) return false;
        return Objects.equals(getUserlessId(), that.getUserlessId()) && Objects.equals(getUserlessName(), that.getUserlessName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserlessId(), getUserlessName());
    }

    @Override
    public String toString() {
        return "UserlessInternalMetric{" +
                "userlessId='" + userlessId + '\'' +
                ", userlessName='" + userlessName + '\'' +
                "} " + super.toString();
    }
}
