package com.pubsublite;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PubSubLite {
    private final ConcurrentMap<String, Topic> topics;
    private static final PubSubLite instance = new PubSubLite();

    private PubSubLite() {
        this.topics = new ConcurrentHashMap<>();
    }

    public static PubSubLite getInstance() {
        return instance;
    }

    public Topic getTopic(String topicName) {
        return topics.computeIfAbsent(topicName, (key) -> new Topic(key));
    }
}
