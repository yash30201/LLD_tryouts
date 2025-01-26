package com.logger;

import com.logger.Model.Level;
import com.logger.Model.Log;

public abstract class Sink {
    private final Level configLevel;

    protected Sink(Level configLevel) {
        this.configLevel = configLevel;
    }

    protected abstract void write(String message, Level level, StackTraceElement[] stackTraceElements);

    public void write(String message, Level level) {
        if (configLevel.ordinal() <= level.ordinal()) {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            write(message, level, stackTraceElements);
        }
    }

    protected Log getLog(String message, Level level, StackTraceElement[] stackTraceElements) {
        StringBuilder source = new StringBuilder();
        for (var element : stackTraceElements) {
            source.append(element.toString());
            source.append('\n');
        }
        return Log.of(message, level, source.toString());
    }
}
