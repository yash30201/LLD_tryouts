package com.logger.Model;

import java.time.Instant;

final public class Log {
    private final String message;
    private final Level level;
    private final Long timestamp;
    private final String source;

    private Log(String message, Level level, Long timestamp, String source) {
        this.message = message;
        this.level = level;
        this.timestamp = timestamp;
        this.source = source;
    }

    public static Log of(String message, Level level, String source) {
        return new Log(message, level, Instant.now().getEpochSecond(), source);
    }

    public String debugString() {
        String builder = "Message: " + message +
                "\n" +
                "Level: " + level.toString() +
                "\n" +
                "Timestamp: " + timestamp +
                "\n" +
                "Source: " + source;
        return builder;
    }
}
