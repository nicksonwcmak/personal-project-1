package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// represents a list of tasks
public class TaskList {
    private List<Task> tasks;

    // EFFECTS: creates an empty task list
    public TaskList() {
        tasks = new ArrayList<>();
    }

    // REQUIRES: all of task's prereqs are in the list
    // MODIFIES: this
    // EFFECTS: adds a task to the list if it is not already in the list and all of its prereqs are
    public void addTask(Task task) {
        if (!hasTask(task)) tasks.add(task);
    }

    // MODIFIES: this
    // EFFECTS: removes task if list contains it; otherwise does nothing
    public void removeTask(Task task) {
        tasks.removeIf(listed -> listed == task); // from an IntelliJ suggestion
    }

    // EFFECTS: returns true if the list contains task
    public boolean hasTask(Task task) {
        for(Task listed : tasks) {
            if (listed == task) return true;
        }
        return false;
    }

    // EFFECTS: returns a queue containing tasks, where every task's prerequisites are before it in the queue
    public Queue<Task> queueTasks() {
        return new LinkedList<>(); // stub
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
