package model;

import javafx.util.Pair;
import model.exceptions.AlreadyInToDoListException;

import java.util.*;

// represents a to-do list with tasks
public class ToDoList {
    private Map<String,Task> tasks;
    private Set<Pair<Task,Task>> prereqRelations; // set of prerequisite-task pairs

    // EFFECTS: creates an empty to-do list
    public ToDoList() {
        tasks = new HashMap<>();
        prereqRelations = new HashSet<>();
    }

    // REQUIRES: all of task's prerequisites are in the list
    // MODIFIES: this
    // EFFECTS: if no task with the same name in to-do list, adds task to the list
    //          throws DuplicateTaskException if a task with the same name is already in the list
    public void addTask(Task task) throws AlreadyInToDoListException {
        if (tasks.containsKey(task.getName())) {
            throw new AlreadyInToDoListException();
        } else {
            tasks.put(task.getName(),task);

            Set<Task> prereqs = task.getPrereqs();
            for (Task prereq : prereqs) {
                prereqRelations.add(new Pair<>(prereq,task));
            }
        }
    } // note: cycles are impossible, so long as prereqs can't be modified after task creation

    // MODIFIES: this
    // EFFECTS: removes task if list contains it, recursively removes all tasks with task as a prereq
    public void removeTask(Task task) {
        tasks.remove(task.getName());
        for (Pair<Task,Task> pair : prereqRelations) {
            if (pair.getValue() == task) {
                prereqRelations.remove(pair);
            } else if (pair.getKey() == task) {
                prereqRelations.remove(pair);
                removeTask(pair.getValue());
            }
        }
    }

    // EFFECTS: returns true if the list contains task with given name
    public boolean hasTaskWithName(String name) {
        return tasks.containsKey(name);
    }

    // EFFECTS: returns tasks stored in the to-do list, in no particular order
    public Collection<Task> getTasks() {
        return tasks.values();
    } // using this until I figure out how to return tasks in order


    // EFFECTS: returns tasks stored in the to-do list, in order
    //          tasks are returned such that every task is preceded by its prereqs
    public List<Task> orderedTasks() {
        return new ArrayList<>(); // stub
    }
}
