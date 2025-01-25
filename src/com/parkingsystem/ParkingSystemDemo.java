package com.parkingsystem;

import com.Runner.Runner;
import com.parkingsystem.vehicle.Vehicle;
import com.parkingsystem.vehicle.VehicleType;

import java.util.Scanner;

public class ParkingSystemDemo implements Runner {
    private static final String CLOSE = "CLOSE";
    private static final String PARK = "PARK";
    private static final String UNPARK = "UNPARK";

    public void run() {
        System.out.println("Welcome to parking system");
        System.out.println("We support the following operations");
        System.out.println("    \"CLOSE\" => Close the parking system.");
        System.out.println("    \"PARK\" => Park a vehicle.");
        System.out.println("    \"UNPARK\" => Park a vehicle.");
        ParkingSystem parkingSystem = ParkingSystem.getInstance();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String type;
            System.out.print("What do you want to do? : ");
            type = scanner.nextLine();

            switch (type) {
                case CLOSE: {
                    System.out.println("Thank you for using this parking system");
                    return;
                }
                case PARK: {
                    System.out.println("Please enter the following details for your vehicle:");
                    System.out.print("Vehicle type (Currently support BIKE, CAR and BUS) : ");
                    var vehicleTypeString = scanner.next();
                    scanner.nextLine();
                    try {
                        VehicleType.valueOf(vehicleTypeString);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Vehicle type " + vehicleTypeString + "not supported");
                    }
                    System.out.print("Vehicle number plate: ");
                    var numberPlate = scanner.next();
                    scanner.nextLine();
                    Vehicle vehicle;
                    try {
                        vehicle = Vehicle.createVehicle(vehicleTypeString, numberPlate);
                    } catch (UnsupportedOperationException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    try {
                        parkingSystem.park(vehicle);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                }
                case UNPARK: {
                    System.out.print("Please enter vehicle number plate: ");
                    String numberPlate = scanner.next();
                    scanner.nextLine();
                    parkingSystem.unPark(numberPlate);
                    break;
                }
                default:
                    System.out.println("Unsupported operation");
            }
        }
    }
}
