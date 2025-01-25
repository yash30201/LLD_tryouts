package com.parkingsystem;

import com.parkingsystem.vehicle.Vehicle;
import com.parkingsystem.vehicle.VehicleType;

import java.util.*;

record Pair<K, V>(K key, V value) {}

public class ParkingSystem {
    private final List<Level> levels;
    private final Map<String, Pair<Vehicle, Level>> numberPlateToVehicleAndLevel;
    private static ParkingSystem instance;

    private ParkingSystem(int numLevels, int bikeSpots, int carSpots, int busSpots) {
        levels = new ArrayList<>();
        numberPlateToVehicleAndLevel = new HashMap<>();
        for (int i = 0 ; i < numLevels ; ++i) {
            levels.add(new Level(Map.of(
                    VehicleType.BIKE, bikeSpots,
                    VehicleType.CAR, carSpots,
                    VehicleType.BUS, busSpots
            )));
        }

        System.out.println("Currently we have " + numLevels + " levels and each level has:");
        System.out.println("    " + bikeSpots + " bike spots");
        System.out.println("    " + carSpots + " car spots");
        System.out.println("    " + busSpots + " bus spots");
    }

    public static synchronized ParkingSystem getInstance() {
        if (instance == null) {
            instance = new ParkingSystem(2, 2, 2, 2);
        }
        return instance;
    }

    public void park(Vehicle vehicle) throws Exception {
        for (int i = 0 ; i < levels.size() ; ++i) {
            if (levels.get(i).park(vehicle)) {
                numberPlateToVehicleAndLevel.put(vehicle.getNumberPlate(), new Pair<>(vehicle, levels.get(i)));
                System.out.println("Vehicle parked. Level " + i + ", Parking spot " + vehicle.getSpotNumber() + " assigned");
                return;
            }
        }
        throw new Exception("Parking spot full");
    }

    public void unPark(String numberPlate) {
        var entry = numberPlateToVehicleAndLevel.get(numberPlate);
        numberPlateToVehicleAndLevel.remove(numberPlate);
        Level level = entry.value();
        Vehicle vehicle = entry.key();
        level.unPark(vehicle);
    }
}
