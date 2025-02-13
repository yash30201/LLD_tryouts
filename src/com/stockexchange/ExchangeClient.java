package com.stockexchange;

import com.Runner.Runner;

import java.util.Scanner;

public class ExchangeClient implements Runner {
    private final StockExchange exchange;

    public ExchangeClient() {
        this.exchange = StockExchange.getInstance();
    }

    public static void main(String[] vars) {
        ExchangeClient client = new ExchangeClient();
        try {
            client.run();
        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }


    @Override
    public void run() throws InterruptedException {
        User u1 = exchange.createUser("yash", "a@b.com");
        User u2 = exchange.createUser("vidit", "b@c.com");

        System.out.println("User 1 sends buy order for 10 stocks at 200.0");
        exchange.placeOrder(u1, OrderType.BUY, "GOOG", 10L, 200.0);
        Thread.sleep(2000L);

        System.out.println("User 2 sends sell order for 5 stocks at 300.0");
        exchange.placeOrder(u2, OrderType.SELL, "GOOG", 5L, 300.0);
        Thread.sleep(2000L);

        System.out.println("User 1 sends buy order for 10 stocks at 300.0");
        exchange.placeOrder(u1, OrderType.BUY, "GOOG", 10L, 300.0);
        Thread.sleep(2000L);

        System.out.println("User 2 sends sell order for 20 stocks at 400.0");
        exchange.placeOrder(u2, OrderType.SELL, "GOOG", 20L, 400.0);
        Thread.sleep(2000L);

        System.out.println("User 1 sends buy order for 5 stocks at 400.0");
        exchange.placeOrder(u1, OrderType.BUY, "GOOG", 5L, 400.0);
        Thread.sleep(2000L);

        System.out.println("Thank you for using stock exchange");
    }
}
