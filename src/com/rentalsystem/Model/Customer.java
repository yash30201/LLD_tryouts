package com.rentalsystem.Model;

import java.util.UUID;

public class Customer {
    private final String fullName;
    private final String id;
    private final String phoneNumber;

    private Customer(String fullName, String id, String phoneNumber) {
        this.fullName = fullName;
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public static Customer create(String fullName, String phoneNumber) {
        UUID uuid = UUID.randomUUID();
        return new Customer(fullName, uuid.toString(), phoneNumber);
    }

    public String getFullName() {
        return fullName;
    }

    public String getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
