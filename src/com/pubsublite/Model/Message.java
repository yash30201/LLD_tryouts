package com.pubsublite.Model;

public class Message {
    private final String content;
    private final long timestamp;

    public Message(String content) {
        this.content = content;
        this.timestamp = System.nanoTime();
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
