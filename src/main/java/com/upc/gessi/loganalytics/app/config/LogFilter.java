package com.upc.gessi.loganalytics.app.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

import java.util.Objects;

public class LogFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        //if (Objects.equals(event.getLoggerName(), "ActionLogger"))
            return FilterReply.ACCEPT;
        //else return FilterReply.DENY;
    }
}
