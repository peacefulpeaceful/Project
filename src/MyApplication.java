import controllers.interfaces.IParcelController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IParcelController controller;

    public MyApplication(IParcelController controller) {
        this.controller = controller;
    }

    private void mainMenu() {
        System.out.println();
        System.out.println("Welcome to my post office app");
        System.out.println("1. Create package");
        System.out.println("2. Show all packages");
        System.out.println("3. Change status");
        System.out.println("0. Exit");
        System.out.print("Enter option (0-3): ");
    }

    public void start() {
        while (true) {
            mainMenu();
            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> createParcelMenu();
                    case 2 -> showAllMenu();
                    case 3 -> changeStatusMenu();
                    case 0 -> { System.out.println("Bye!"); return; }
                    default -> System.out.println("Wrong option!");
                }

            } catch (InputMismatchException e) {
                System.out.println("Input must be integer!");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("*************************");
        }
    }

    private void createParcelMenu() {
        System.out.println("Enter weight:");
        double weight = scanner.nextDouble();

        System.out.println("Sender name:");
        String sName = scanner.next();
        System.out.println("Sender surname:");
        String sSurname = scanner.next();
        System.out.println("Sender address:");
        String sAddress = scanner.next();

        System.out.println("Receiver name:");
        String rName = scanner.next();
        System.out.println("Receiver address:");
        String rAddress = scanner.next();

        String response = controller.createParcel(weight, sName, sSurname, sAddress, rName, rAddress);
        System.out.println(response);
    }

    private void showAllMenu() {
        String response = controller.getAllParcels();
        System.out.println(response);
    }

    private void changeStatusMenu() {
        System.out.println("Enter parcel id:");
        int id = scanner.nextInt();

        System.out.println("Enter new status (CREATED / IN_TRANSIT / DELIVERED):");
        String status = scanner.next();

        String response = controller.changeStatus(id, status);
        System.out.println(response);
    }
}