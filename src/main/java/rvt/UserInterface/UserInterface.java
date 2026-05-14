package rvt.UserInterface;

import java.util.Scanner;

public class UserInterface {

    public void start(){
        TodoList list = new TodoList();
        Scanner scanner = new Scanner(System.in);
        while (true){
            String answer;
            System.out.println("Command: ");
            answer = scanner.nextLine();
            if (answer.equals("stop")){
                break;
            }
            else if (answer.equals("add")){
                System.out.print("To add? ");
                String word = scanner.nextLine();
                list.add(word);
            } else if (answer.equals("remove")){
                System.out.print("To remove? ");
                int number = Integer.valueOf(scanner.nextLine());
                list.remove(number);
            } else if (answer.equals("list")){
                list.print();
            } else if (answer.equals("last")){
                list.printLastId();
            }
        }
    }
}