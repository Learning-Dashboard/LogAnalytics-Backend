package com.upc.gessi.loganalytics.app.domain.models.pkey;

import java.io.Serializable;
import java.util.Objects;

public class TeamPrimaryKey implements Serializable {
    private String id;
    private String semester;
    private String subject;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getSemester() { return semester; }

    public void setSemester(String semester) { this.semester = semester; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamPrimaryKey that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getSemester(), that.getSemester()) && Objects.equals(getSubject(), that.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSemester(), getSubject());
    }
}
