package com.parkingsystem;

import com.parkingsystem.vehicle.Vehicle;
import com.parkingsystem.vehicle.VehicleType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Level {
    private final Map<VehicleType, List<ParkingSpot>> parkingSpots;
    private final Map<String, ParkingSpot> vehicleToSpot;

    public Level(Map<VehicleType, Integer> specs) {
        parkingSpots = new HashMap<>();
        vehicleToSpot = new HashMap<>();
        AtomicInteger spotId = new AtomicInteger(1);
        specs.forEach((key, value) -> {
            parkingSpots.put(key, new ArrayList<>());
            var spots = parkingSpots.get(key);
            for (int i = 0; i < value; ++i) {
                spots.add(new ParkingSpot(spotId.getAndIncrement()));
            }
        });
    }

    /**
     * Try to park vehicle
     *
     * @param vehicle The vehicle to park
     * @return boolean Return `true` if parked, else `false`.
     */
    public boolean park(Vehicle vehicle) {
        ParkingSpot spot = null;
        synchronized (parkingSpots) {
            var spots = parkingSpots.get(vehicle.getType());
            if (!(spots.isEmpty())) {
                spot = spots.removeLast();
            }
        }
        if (spot == null) {
            return false;
        }
        spot.assign(vehicle.getNumberPlate());
        vehicleToSpot.put(vehicle.getNumberPlate(), spot);
        vehicle.setSpotNumber(spot.getSpotNumber());
        return true;
    }

    public void unPark(Vehicle vehicle) {
        vehicle.unsetSpotNumber();
        var spot = vehicleToSpot.get(vehicle.getNumberPlate());
        vehicleToSpot.remove(vehicle.getNumberPlate());
        spot.unAssign();
        parkingSpots.get(vehicle.getType()).add(spot);
        System.out.println("Spot " + spot.getSpotNumber() + " freed");
    }
}
