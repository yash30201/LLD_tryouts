package com.rentalsystem.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Vehicle {
    private final String numberPlate;
    private final VehicleType type;
    private final int pricePerDay;
    private final String id;
    private final List<RentalBooking> bookings;

    public Vehicle(String numberPlate, VehicleType type, int pricePerDay) {
        this.numberPlate = numberPlate;
        this.type = type;
        this.pricePerDay = pricePerDay;
        this.id = UUID.randomUUID().toString();
        bookings = new ArrayList<>();
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public VehicleType getType() {
        return type;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public String getId() {
        return id;
    }

    public void addBooking(RentalBooking booking) {
        bookings.add(booking);
    }

    public void removeBooking(RentalBooking booking) {
        bookings.remove(booking);
    }

    public boolean freeOnRange(Range range) {
        boolean result = true;
        for (var booking : bookings) {
            if (booking.getDateRange().isIntersecting(range)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
