package com.parkingsystem;

/**
 * Defines an interface for a generic parking spot.
 */
public class ParkingSpot {
    private final int spotNumber;
    private String parkedVehiclePlate;


    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public void assign(String parkedVehiclePlate) {
        this.parkedVehiclePlate = parkedVehiclePlate;
    }

    public void unAssign() {
        parkedVehiclePlate = null;
    }

    public int getSpotNumber () {
        return spotNumber;
    }
}
