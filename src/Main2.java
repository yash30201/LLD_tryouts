import com.stackoverflow.StackOverflowDemo;

import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) throws IllegalStateException{
        StackOverflowDemo demo1 = new StackOverflowDemo();
        StackOverflowDemo demo2 = new StackOverflowDemo();
        Scanner scanner = new Scanner(System.in);
        int x;
        while (true) {
            System.out.print("Which demo to use?(1, 2) : ");
            x = scanner.nextInt();
            scanner.nextLine();
            if (x == 0) {
                demo1.run();
            } else if (x == 1) {
                demo2.run();
            } else break;
        }
    }
}