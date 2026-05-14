package rvt.UserInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TodoList {

    private ArrayList<String> tasks;
    private final String filePath = "data/todo.csv";

    private int index = 0;
    private int nextId = 1;
    public TodoList() {
        this.tasks = new ArrayList<>();
        loadFromFile();
    }

    public boolean checkEventStrings(String value) {
        if (value == null || value.length() < 3) {
            return false;
        }
        return value.matches("^[a-zA-Z0-9āčēģīķļņšūžĀČĒĢĪĶĻŅŠŪŽ ]*$");
    }
    private void loadFromFile(){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",", 2);
                    if (parts.length > 1) {
                        this.tasks.add(parts[1]);
                    } else {
                        this.tasks.add(line);
                    }
                }
            }
            this.nextId = tasks.size() + 1;
            getLastId();
        } catch (IOException e) {
            System.out.println("Error reading file.");
            }
    }


    private int getLastId(){
        int lastId = index;
        System.out.println(lastId);
        return lastId;
    }

    public void add(String taskName) {
        if (!checkEventStrings(taskName)) {
            System.out.println("Kļūda: Aktivitatei jāsatur tikai burti/cipari un jābūt vismaz 3 simbolus garai!");
            return;
        }
        this.tasks.add(taskName);
        String formattedTask = this.nextId + "," + taskName;
        saveTaskToFile(formattedTask);
        this.nextId++;
    }

    private void saveTaskToFile(String taskLine){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(taskLine);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }
    public void printLastId(){
        System.out.println(tasks.size());
    }

    public void print() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void remove(int number) {
        int indexToRemove = number - 1;

        if (indexToRemove >= 0 && indexToRemove < tasks.size()) {
            tasks.remove(indexToRemove);
            rewriteFile();
            this.nextId = tasks.size() + 1;
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private void rewriteFile(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < tasks.size(); i++) {
                int newId = i + 1;
                String taskName = tasks.get(i);
                bw.write(newId + "," + taskName);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating file.");
        }
    }
}