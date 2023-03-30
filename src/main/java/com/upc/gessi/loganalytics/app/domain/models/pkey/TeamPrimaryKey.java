package com.upc.gessi.loganalytics.app.domain.models.pkey;

import java.io.Serializable;
import java.util.Objects;

public class TeamPrimaryKey implements Serializable {
    private String id;
    private String semester;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getSemester() { return semester; }

    public void setSemester(String semester) { this.semester = semester; }

    public TeamPrimaryKey() { }

    public TeamPrimaryKey(String id, String semester) {
        this.id = id;
        this.semester = semester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamPrimaryKey that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getSemester(), that.getSemester());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSemester());
    }
}
