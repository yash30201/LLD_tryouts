import com.Runner.Runner;
import com.logger.LoggerDemo;
import com.parkingsystem.ParkingSystemDemo;
import com.stackoverflow.StackOverflowDemo;

import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final List<String> systems = List.of(
            "Parking System",
            "StackOverflow",
            "Logger"
    );

    public static void main(String[] args) throws IllegalStateException{
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome to LLD showcase");
        System.out.println("What do you want to demo?");
        for (int i = 0 ; i < systems.size() ; ++i) {
            System.out.println(i + " - " + systems.get(i));
        }

        // Take input
        Scanner scanner = new Scanner(System.in);
        var x = scanner.nextInt();
        switch (x) {
            case 0 -> {
                Runner runner = new ParkingSystemDemo();
                runner.run();
            }
            case 1 -> {
                Runner runner = new StackOverflowDemo();
                runner.run();
            }
            case 2 -> {
                Runner runner = new LoggerDemo();
                runner.run();
            }
            default -> throw new IllegalStateException("Unexpected value: " + x);
        }
    }
}