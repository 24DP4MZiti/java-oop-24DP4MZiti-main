package rvt.StudentuReg;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RegistrationManager {
    private ArrayList<Student> students;
    private final String filePath = "data/students.csv";

    public RegistrationManager() {
        this.students = new ArrayList<>();
        loadFromFile();
    }

    public boolean isValidName(String value) {
        if (value == null || value.length() < 3) {
            return false;
        }
        return value.matches("^[a-zA-ZāčēģīķļņšūžĀČĒĢĪĶĻŅŠŪŽ ]*$");
    }

    public void register(Student s) {
        this.students.add(s);
        saveToFile();
        System.out.println("Student registered successfully!");
    }

    public void show() {
        if (students.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        System.out.println("+------------+------------+----------------------+--------------+---------------------+");
        System.out.println("| First Name | Last Name  | Email                | Pers. Code   | Registered At       |");
        System.out.println("+------------+------------+----------------------+--------------+---------------------+");
        for (Student s : students) {
            System.out.printf("| %-10s | %-10s | %-20s | %-12s | %-19s |\n", 
                s.getFirstName(), s.getLastName(), s.getEmail(), s.getPersonalCode(), s.getRegistrationTime());
        }
        System.out.println("+------------+------------+----------------------+--------------+---------------------+");
    }

    public void remove(String pk) {
        boolean removed = students.removeIf(s -> s.getPersonalCode().equals(pk));
        if (removed) {
            saveToFile();
            System.out.println("Student with code " + pk + " has been removed.");
        } else {
            System.out.println("Error: Student with this code does not exist.");
        }
    }

    public void edit(String pk, Student updated) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getPersonalCode().equals(pk)) {
                students.set(i, updated);
                saveToFile();
                System.out.println("Data updated successfully.");
                return;
            }
        }
        System.out.println("Error: Student with code " + pk + " not found.");
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            for (Student s : students) {
                bw.write(s.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 5) {
                    students.add(new Student(p[0], p[1], p[2], p[3], p[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}