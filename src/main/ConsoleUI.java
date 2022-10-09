import model.Task;
import model.ToDoList;
import model.exceptions.AlreadyInToDoListException;
import model.exceptions.MissingPrerequisiteException;

import java.util.*;

// represents a console UI for using ToDoList
public class ConsoleUI {
    private ToDoList toDoList;
    private Scanner input;

    // EFFECTS: starts UI with an empty ToDoList
    public ConsoleUI() {
        input = new Scanner(System.in);
        toDoList = new ToDoList();
        runApp();
    }

    // EFFECTS: runs the console application
    private void runApp() {
        boolean keepRunning = true;
        String command;

        System.out.println("To-do list application running...");
        while (keepRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }
    }

    private void displayMenu() {
        System.out.println("Select one:");
        System.out.println("\ta - add a task");
        System.out.println("\td - display a task's information");
        System.out.println("\to - generate to-do list");
        System.out.println("\tq - quit");
    }

    // EFFECTS: processes input commands
    private void processCommand(String command) {
       switch (command) {
           case "a":
               addTaskToModel();
               break;
           case "d" :
               displayTaskInfo();
               break;
           case "o":
               displayOrderedTasks();
               break;
           default:
               System.out.println("Invalid command");
               break;
       }
    }

    // MODIFIES: toDoList
    // EFFECTS: adds a task to ToDoList, as specified by user input
    private void addTaskToModel() {
        System.out.println("Adding a task:");

        System.out.println("Input task name:");
        String name = input.next();
        System.out.println("Input task duration (must be an integer):");
        int dur = input.nextInt();

        Set<Task> prereqs = new HashSet<>();
        while(true) {
            System.out.println("Input name of a prerequisite task (press q to skip):");
            String prereqName = input.next();
            if (prereqName.equals("q")) {
                break;
            }

            try {
                Task prereq  = toDoList.findTaskWithName(prereqName);
                System.out.println("Prerequisite successfully added");
                prereqs.add(prereq);
            } catch (NoSuchElementException e) {
                System.out.println("There is no such task in the list.");
            }
        }

        try {
            toDoList.addTask(new Task(name,dur,prereqs));
            System.out.println("Task successfully added.");
        } catch (AlreadyInToDoListException e) {
            System.out.println("Task not added. There is already a task with this name in the to-do list.");
        } catch (MissingPrerequisiteException e) {
            System.out.println("At least one of the prerequisite tasks has not been added yet.");
        }
    }

    // EFFECTS: displays a Task's information
    private void displayTaskInfo() {
        System.out.println("Finding a task:");

        System.out.println("Input task name:");
        String name = input.next();
        try {
            Task returned = toDoList.findTaskWithName(name);
            System.out.println("Displaying task info:");
            System.out.println("Task name: " + returned.getName());
            System.out.println("Task duration: " + returned.getDuration());

            Collection<Task> prereqs = returned.getPrereqs();
            for (Task t : prereqs) {
                System.out.println("Prerequisite task: " + t.getName());
            }
        } catch (NoSuchElementException e) {
            System.out.println("To-do list contains no task with that name.");
        }
    }

    // EFFECTS: outputs a list of tasks, in a valid ordering
    private void displayOrderedTasks() {
        // stub
    }
}