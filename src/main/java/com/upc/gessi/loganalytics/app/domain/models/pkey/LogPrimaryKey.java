package com.upc.gessi.loganalytics.app.domain.models.pkey;

import com.upc.gessi.loganalytics.app.domain.models.Session;

import java.io.Serializable;
import java.util.Objects;

public class LogPrimaryKey implements Serializable {
    private long time;
    private Session session;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogPrimaryKey that)) return false;
        return getTime() == that.getTime() && getSession().equals(that.getSession());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTime(), getSession());
    }
}
