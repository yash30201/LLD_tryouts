package com.parkingsystem.vehicle;

public abstract class Vehicle {
    private final VehicleType type;
    private final String numberPlate;
    private Integer spotNumber;

    public Vehicle(VehicleType type, String numberPlate) {
        this.type = type;
        this.numberPlate = numberPlate;
    }

    public static Vehicle createVehicle(String type, String numberPlate) throws UnsupportedOperationException {
        VehicleType vehicleType = VehicleType.valueOf(type);
        switch (vehicleType) {
            case BUS -> {
                return new Bus(numberPlate);
            }
            case CAR -> {
                return new Car(numberPlate);
            }
            case BIKE -> {
                return new Bike(numberPlate);
            }
            default -> throw new UnsupportedOperationException("Vehicle type " + type + " not supported");
        }
    }

    public VehicleType getType() {
        return type;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public void unsetSpotNumber() {
        this.spotNumber = null;
    }
}
