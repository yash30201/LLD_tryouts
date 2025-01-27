package com.rentalsystem;

import com.Runner.Runner;
import com.rentalsystem.Model.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RentalSystemDemo implements Runner {

    public RentalSystemDemo() {
        run();
    }

    public static void main(String[] args) {
        RentalSystemDemo demo = new RentalSystemDemo();
        demo.run();
    }

    @Override
    public void run() {
        RentalSystem system1 = RentalSystem.getInstance();
        Customer c1 = system1.addCustomer("Customer1", "0000");
        Vehicle v1 = new Vehicle("lorem1", VehicleType.CAR_SEDAN, 1000);
        Vehicle v2 = new Vehicle("lorem2", VehicleType.BIKE_CRUISER, 500);
        Vehicle v3 = new Vehicle("lorem3", VehicleType.CAR_HATCH_BACK, 700);

        system1.addVehicle(v1);
        system1.addVehicle(v2);
        system1.addVehicle(v3);

        List<VehicleType> allList = List.of(VehicleType.values());

        Query q1 = Query.of(allList, 1000, LocalDate.of(2024, 11, 01), LocalDate.of(2024, 11, 10));
        Query q2 = Query.of(allList, 700, LocalDate.of(2024, 11, 01), LocalDate.of(2024, 11, 10));

        System.out.println("Running query 1");
        List<QueryResult> r1 = system1.search(q1);
        for (var r : r1) {
            System.out.println(r.prettyPrint());
        }
        System.out.println("Running query 2");
        List<QueryResult> r2 = system1.search(q2);
        for (var r : r2) {
            System.out.println(r.prettyPrint());
        }

        String b1 = "";
        try {
            b1 = system1.creatingBooking(c1, r1.get(0).getVehicleId(), q1.getDateRange());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Getting new instance variable of system and adding new vehicles");

        RentalSystem system2 = RentalSystem.getInstance();
        System.out.println("Trying to book the same vehicle again for the same duration");
        try {
            system2.creatingBooking(c1, r1.get(0).getVehicleId(), q1.getDateRange());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Modifying previous booking");
        Range range1 = new Range(LocalDate.of(2024, 11, 01), LocalDate.of(2024, 11, 5));
        try {
            system2.modifyBooking(c1, b1, range1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        System.out.println("Booking the same vehicle after modifying");
        String b2 = "";
        Range range2 = new Range(LocalDate.of(2024, 11, 06), LocalDate.of(2024, 11, 10));
        try {
            b2 = system2.creatingBooking(c1, r1.get(0).getVehicleId(), range2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Adding some more vehicles");

        Vehicle v4 = new Vehicle("lorem4", VehicleType.CAR_SUV, 1300);
        Vehicle v5 = new Vehicle("lorem5", VehicleType.BIKE_SPORTS, 600);
        Vehicle v6 = new Vehicle("lorem6", VehicleType.CAR_SEDAN, 900);

        system1.addVehicle(v4);
        system1.addVehicle(v5);
        system1.addVehicle(v6);

        Query q3 = Query.of(allList, 1500, LocalDate.of(2024, 11, 01), LocalDate.of(2024, 11, 10));
        Query q4 = Query.of(allList, 1000, LocalDate.of(2024, 12, 01), LocalDate.of(2024, 12, 10));
        System.out.println("Running query 3");
        List<QueryResult> r3 = system1.search(q3);
        for (var r : r3) {
            System.out.println(r.prettyPrint());
        }
        System.out.println("Running query 4");
        List<QueryResult> r4 = system1.search(q4);
        for (var r : r4) {
            System.out.println(r.prettyPrint());
        }
    }
}
