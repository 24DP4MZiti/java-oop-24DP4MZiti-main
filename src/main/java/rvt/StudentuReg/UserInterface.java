package rvt.StudentuReg;

import java.util.Scanner;

public class UserInterface {
    private RegistrationManager manager = new RegistrationManager();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.print("\nCommands: register, show, remove, edit, exit\nChoice: ");
            String command = scanner.nextLine().toLowerCase().trim();

            if (command.equals("exit")) {
                break;
            } else if (command.equals("register")) {
                handleRegister();
            } else if (command.equals("show")) {
                manager.show();
            } else if (command.equals("remove")) {
                System.out.print("Enter personal code to remove: ");
                manager.remove(scanner.nextLine());
            } else if (command.equals("edit")) {
                handleEdit();
            } else {
                System.out.println("Unknown command!");
            }
        }
    }

    private void handleRegister() {
        System.out.print("First Name: "); String v = scanner.nextLine();
        System.out.print("Last Name: "); String u = scanner.nextLine();
        System.out.print("Email: "); String e = scanner.nextLine();
        System.out.print("Personal Code: "); String pk = scanner.nextLine();

        if (manager.isValidName(v) && manager.isValidName(u)) {
            manager.register(new Student(v, u, e, pk));
        } else {
            System.out.println("Error: Name/Surname must contain only letters (min. 3 symbols)!");
        }
    }

    private void handleEdit() {
        System.out.print("Enter personal code of the student to edit: ");
        String oldPk = scanner.nextLine();
        System.out.print("New First Name: "); String v = scanner.nextLine();
        System.out.print("New Last Name: "); String u = scanner.nextLine();
        System.out.print("New Email: "); String e = scanner.nextLine();
        System.out.print("New Personal Code: "); String newPk = scanner.nextLine();

        manager.edit(oldPk, new Student(v, u, e, newPk));
    }
}