package com.logger;

import com.logger.Model.Level;

/**
 * - Logger
 *      - Support different log levels, DEBUG, INFO, WARNING, ERROR and FATAL
 *      - Allow logging messages with timestamp, level and message
 *      - Support multiple destinations, file, console and database
 *      - Should be thread safe to handle concurrent logging from multiple places
 *      - Extensible to onboard new logging levels and sinks
 *  - Data model
 *      - Sink (Interface) - Strategy design pattern because they are independent
 *          - Console
 *          - File
 *          - Database
 *          ...
 *          - Should allow writing (concurrently)
*       - Enum Level
 *          - DEBUG
 *          - INFO
 *          - WARNING
 *          - ERROR
 *          - FATAL
 *      - class Log
 *          - Message
 *          - Level
 *          - Timestamp
 *          - Source
 */
public class Logger {
    private Sink sink;
    private static final Logger instance = new Logger();

    private Logger() {
        sink = new Console(Level.INFO);
    }

    public static Logger getInstance() {
        return instance;
    }

    public void atInfo(String message) {
        sink.write(message, Level.INFO);
    }

    public void atDebug(String message) {
        sink.write(message, Level.DEBUG);
    }

    public void atWarning(String message) {
        sink.write(message, Level.WARNING);
    }

    public void atError(String message) {
        sink.write(message, Level.ERROR);
    }

    public void atFatal(String message) {
        sink.write(message, Level.FATAL);
    }

    public synchronized void changeSink(Sink newSink) {
        sink = newSink;
    }
}
