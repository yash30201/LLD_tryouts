package com.rentalsystem.Model;

import java.util.UUID;

public class RentalBooking {
    private final Customer customer;
    private final Range dateRange;
    private final Vehicle vehicle;
    private final String id;

    public RentalBooking(Customer customer, Range dateRange, Vehicle vehicle) {
        this.customer = customer;
        this.dateRange = dateRange;
        this.vehicle = vehicle;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Range getDateRange() {
        return dateRange;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
