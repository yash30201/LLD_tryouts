package com.rentalsystem.Model;

public class QueryResult {
    private final VehicleType type;
    private final int pricePerDay;
    private final String vehicleId;

    public QueryResult(String vehicleId, VehicleType type, int pricePerDay) {
        this.vehicleId = vehicleId;
        this.type = type;
        this.pricePerDay = pricePerDay;
    }

    public String prettyPrint() {
        return "Vehicle type : " + type.toString()
                + "\n"
                + "Price per day : " + pricePerDay
                + "\n"
                + "Vehicle ID: " + vehicleId;
    }

    public VehicleType getType() {
        return type;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public String getVehicleId() {
        return vehicleId;
    }
}
