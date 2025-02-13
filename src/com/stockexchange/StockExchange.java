package com.stockexchange;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StockExchange {
    private static final StockExchange instance = new StockExchange();
    private final Fulfiller fulfiller;
    private final ConcurrentMap<String, User> users;

    private StockExchange() {
        fulfiller = new NearestCompleteOrderFulfiller();
        users = new ConcurrentHashMap<>();
    }

    public static StockExchange getInstance() {
        return instance;
    }

    public User createUser(String fullName, String email) {
        User user = User.of(fullName, email);
        users.putIfAbsent(user.getUserId(), user);
        return user;
    }

    public @Nullable User getUser(String userId) {
        return users.get(userId);
    }

    public void placeOrder(User user, OrderType type, String ticker, Long qty, Double price) {
        Order order = Order.of(type, user, ticker, qty, price);
        fulfiller.processOrder(order);
    }
}
