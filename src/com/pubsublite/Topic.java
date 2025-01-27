package com.pubsublite;

import com.pubsublite.Model.Message;
import com.pubsublite.Model.Subscriber;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class Topic {
    private final CopyOnWriteArraySet<Subscriber> subscribers;
    private final String topicName;

    public Topic(String topicName) {
        this.subscribers = new CopyOnWriteArraySet<>();
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void addSubscribers(List<Subscriber> subsToAdd) {
        subscribers.addAll(subsToAdd);
    }

    public void addSubscriber(Subscriber sub) {
        subscribers.add(sub);
    }

    public void removeSubscriber(Subscriber sub) {
        subscribers.remove(sub);
    }

    public void addMessage(Message message) {
        for (Subscriber sub : subscribers) {
            sub.onMessage(message);
        }
    }
}
