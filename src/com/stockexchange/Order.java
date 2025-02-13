package com.stockexchange;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.Callable;

public class Order implements Comparable<Order> {
    private final OrderType orderType;
    private final String orderId;
    private final User user;
    private final Long timestamp;
    private Long qty;
    private final Double price;
    private final String ticker;

    private Order(OrderType orderType, String orderId, String ticker, User user, Long timestamp, Long qty, Double price) {
        this.orderType = orderType;
        this.orderId = orderId;
        this.ticker = ticker;
        this.user = user;
        this.timestamp = timestamp;
        this.qty = qty;
        this.price = price;
    }

    public static Order of(OrderType orderType, User user, String ticker, Long qty, Double price) {
        return new Order(
                orderType,
                UUID.randomUUID().toString(),
                ticker,
                user,
                System.nanoTime(),
                qty,
                price
        );
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public String getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Long getQty() {
        return qty;
    }

    public Double getPrice() {
        return price;
    }

    public String getTicker() {
        return ticker;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Override
    public int compareTo(@NotNull Order o) {
        if (orderId.equals(o.getOrderId())) return 0;
        return price.compareTo(o.getPrice());
    }
}
