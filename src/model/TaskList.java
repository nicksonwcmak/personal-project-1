package model;

import java.util.ArrayList;
import java.util.List;

// represents a list of tasks
public class TaskList {
    private List<Task> tasks;

    // EFFECTS: creates an empty task list
    public TaskList() {
        tasks = new ArrayList<>();
    }

    // EFFECTS: adds a task to the list
    public void addTask(Task task) {
        tasks.add(task);
    }

    // EFFECTS: removes task if list contains it; otherwise does nothing
    public void removeTask(Task task) {
        tasks.removeIf(listed -> listed == task); // from an IntelliJ suggestion
    }
}
