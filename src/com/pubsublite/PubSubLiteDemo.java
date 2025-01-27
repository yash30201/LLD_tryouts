package com.pubsublite;

import com.Runner.Runner;
import com.pubsublite.Model.ConsoleWriteSubscriber;
import com.pubsublite.Model.Subscriber;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PubSubLiteDemo implements Runner {
    private final PubSubLite pubSubLite;

    public PubSubLiteDemo() {
        this.pubSubLite = PubSubLite.getInstance();
    }

    public static void main(String[] args) {
        PubSubLiteDemo x = new PubSubLiteDemo();
        x.run();
    }

    @Override
    public void run() {
        Topic t1 = pubSubLite.getTopic("topic1");
        Publisher p1 = new Publisher();
        p1.registerTopic(t1);

        Subscriber s1 = new ConsoleWriteSubscriber();
        Subscriber s2 = new ConsoleWriteSubscriber();

        t1.addSubscribers(List.of(s1, s2));

        ExecutorService executor = Executors.newFixedThreadPool(1);
        long startNanos = System.nanoTime();

        Subscriber s3 = new ConsoleWriteSubscriber();
        executor.execute(() -> p1.publish(t1, "Publishing before loop at: " + (System.nanoTime() - startNanos)));
        for (int i = 0 ; i < 3 ; ++i) {
            System.out.println("Running loop for i : " + i);
            final int z = i;
            executor.execute(() -> p1.publish(t1, "Published for loop " + z + " at: " + (System.nanoTime() - startNanos)));
            if (i == 1) {
                System.out.println("Adding a new subscriber after " + i + "'th run");
                t1.addSubscriber(s3);
            }
        }
        executor.close();
    }
}
