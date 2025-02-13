package com.stockexchange;

import java.util.UUID;

public class User {
    private final String userId;
    private final String fullName;
    private final String email;

    private User(String userId, String fullName, String email) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
    }

    public static User of(String fullName, String email) {
        return new User(
                UUID.randomUUID().toString(),
                fullName,
                email
        );
    }

    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void onFulfillment(OrderFulfillmentResult result) {
        System.out.println("Order fulfilled for user : " + fullName);
        System.out.println("Ticker : " + result.getOrder().getTicker());
        System.out.println("Order type : " + (result.getOrder().getOrderType().toString()));
        System.out.println("Asking price: " + result.getOrder().getPrice());
        System.out.println("Average price: " + result.getAvgPrice());
        System.out.println("");
    }
}
