package com.rentalsystem;

import com.rentalsystem.Model.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RentalSystem {
    private static final RentalSystem instance = new RentalSystem();
    private final ConcurrentMap<String, Customer> idToCustomer;
    private final ConcurrentMap<String, Vehicle> idToVehicle;
    private final List<Vehicle> vehicles;
    private final ConcurrentMap<String, RentalBooking> idToBooking;
    private final PaymentProcessor paymentProcessor;

    private RentalSystem() {
        this.idToCustomer = new ConcurrentHashMap<>();
        this.idToVehicle = new ConcurrentHashMap<>();
        this.vehicles = new ArrayList<>();
        this.idToBooking = new ConcurrentHashMap<>();
        paymentProcessor = new CreditCardPaymentProcessor();
    }

    public static RentalSystem getInstance() {
        return instance;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        idToVehicle.put(vehicle.getId(), vehicle);
    }

    public Customer addCustomer(String fullName, String phoneNumber) {
        Customer customer = Customer.create(fullName, phoneNumber);
        idToCustomer.put(customer.getId(), customer);
        return customer;
    }

    public @Nullable Customer getCustomer(String phoneNumber) {
        Customer customer = null;
        for (Map.Entry<String, Customer> entry : idToCustomer.entrySet()) {
            String id = entry.getKey();
            Customer currCustomer = entry.getValue();
            if (currCustomer.getPhoneNumber().equals(phoneNumber)) {
                customer = currCustomer;
                break;
            }
        }
        return customer;
    }

    public List<QueryResult> search(Query query) {
        List<QueryResult> result = new ArrayList<>();
        for (var vehicle : vehicles) {
            if (!query.isVehicleType(vehicle.getType()) || (vehicle.getPricePerDay() > query.getMaxPerDayPrice()) || !vehicle.freeOnRange(query.getDateRange())) {
                continue;
            }
            result.add(new QueryResult(vehicle.getId(), vehicle.getType(), vehicle.getPricePerDay()));
        }
        return result;
    }

    public String creatingBooking(Customer customer, String vehicleId, Range dateRange) throws Exception {
        final List<String> result = new ArrayList<>();
        final List<Boolean> paymentResult = new ArrayList<>();
        idToVehicle.compute(vehicleId, (key, vehicle) -> {
            RentalBooking booking = new RentalBooking(customer, dateRange, vehicle);
            if (!paymentProcessor.transact(customer, costOfBooking(vehicle, dateRange))) {
                paymentResult.add(false);
                return vehicle;
            }
            paymentResult.add(true);
            if (vehicle.freeOnRange(dateRange)) {
                vehicle.addBooking(booking);
                result.add(booking.getId());
                idToBooking.put(booking.getId(), booking);
            }
            return vehicle;
        });
        if (result.isEmpty()) {
            if (paymentResult.get(0)) {
                throw new Exception("Vehicle got booked! Please try again");
            } else {
                throw new Exception("Payment failed! Please book again");
            }
        }
        return result.get(0);
    }

    public String modifyBooking(Customer customer, String oldBookingId, Range newDateRange) throws Exception {
        final RentalBooking oldBooking = idToBooking.remove(oldBookingId);
        final Vehicle vehicle = oldBooking.getVehicle();
        RentalBooking booking = new RentalBooking(customer, newDateRange, vehicle);
        String bookingId = null;
        synchronized (vehicle) {
            vehicle.removeBooking(oldBooking);
            if (vehicle.freeOnRange(newDateRange)) {
                vehicle.addBooking(booking);
                bookingId = booking.getId();
            }
        }
        if (bookingId == null) {
            throw new Exception("Vehicle not available for the new booking dates. Please book another vehicle");
        }
        idToBooking.put(booking.getId(), booking);
        return bookingId;
    }

    public void deleteBooking(Customer customer, String bookingId) {
        final RentalBooking booking = idToBooking.remove(bookingId);
        final Vehicle vehicle = booking.getVehicle();
        synchronized (vehicle) {
            vehicle.removeBooking(booking);
        }
    }

    private long costOfBooking(Vehicle vehicle, Range dateRange) {
        int numOfDays = dateRange.getRangeDays();
        return (long) numOfDays * vehicle.getPricePerDay();
    }
}







































