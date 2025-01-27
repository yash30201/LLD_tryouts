package com.pubsublite.Model;

public class ConsoleWriteSubscriber implements Subscriber {

    @Override
    public void onMessage(Message message) {
        System.out.println("Timestamp (in nanos): " + message.getTimestamp());
        System.out.println("Content: " + message.getContent());
    }
}
