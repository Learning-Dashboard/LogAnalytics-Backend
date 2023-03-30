package com.upc.gessi.loganalytics.app.domain.models;

import com.upc.gessi.loganalytics.app.domain.models.pkey.LogPrimaryKey;
import jakarta.persistence.*;

@Entity
@Table(name = "Log")
@IdClass(LogPrimaryKey.class)
@Inheritance(strategy = InheritanceType.JOINED)
public class Log {

    @Id @Column (name = "time", nullable = false)
    private long time;
    @Id @Column (name = "team", nullable = false)
    private String team;
    @Column (name = "message", nullable = false)
    private String message;
    @Column (name = "page")
    private String page;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "teamId"),
            @JoinColumn(name = "teamSemester"),
            @JoinColumn(name = "startTimestamp")
    })
    private Session session;

    public Log() { }

    public Log(long time, String team, String message, String page) {
        this.time = time;
        this.team = team;
        this.message = message;
        this.page = page;
    }

    public Log(long time, String team, String message) {
        this.time = time;
        this.team = team;
        this.message = message;
    }

    public Log(long time, String team, String message, Session session) {
        this.time = time;
        this.team = team;
        this.message = message;
        this.session = session;
    }

    public Log(long time, String team, String message, String page, Session session) {
        this.time = time;
        this.team = team;
        this.message = message;
        this.page = page;
        this.session = session;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Log{" +
                "time=" + time +
                ", team='" + team + '\'' +
                ", message='" + message + '\'' +
                ", page='" + page + '\'' +
                ", session=" + session +
                '}';
    }
}
