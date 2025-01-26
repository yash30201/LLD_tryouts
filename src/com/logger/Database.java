package com.logger;

import com.logger.Model.Level;
import com.logger.Model.Log;

final public class Database extends Sink {
    public Database(Level configLevel) {
        super(configLevel);
    }

    @Override
    protected synchronized void write(String message, Level level, StackTraceElement[] stackTraceElements) {
        Log log = getLog(message, level, stackTraceElements);
        System.out.println("Logged to database:");
        System.out.println(log.debugString());
    }
}
