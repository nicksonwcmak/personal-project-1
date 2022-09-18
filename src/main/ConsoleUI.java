import model.ToDoList;

import java.util.Scanner;

// represents a console UI for using ToDoList
public class ConsoleUI {
    private ToDoList toDoList;
    private Scanner scanner;

    // EFFECTS: starts UI with an empty ToDoList
    public ConsoleUI() {
        scanner = new Scanner(System.in);
        toDoList = new ToDoList();
        displayMenu();
    }

    // EFFECTS: displays the menu in console
    private void displayMenu() {
        // stub
    }

    // EFFECTS: processes input commands
    private void processCommand() {
        // stub
    }

    // MODIFIES: toDoList
    // EFFECTS: adds a task to ToDoList, as specified by user input
    private void addTaskToModel() {
        // stub
    }

    // EFFECTS: displays a Task's information
    private void displayTaskInfo() {
        // stub
    }

    // EFFECTS: outputs a list of tasks, in a valid ordering
    private void displayOrderedTasks() {
        // stub
    }
}