package model;

import javafx.util.Pair;
import model.exceptions.AlreadyInToDoListException;
import model.exceptions.MissingPrerequisiteException;

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
    //          throws AlreadyInToDoListException if a task with the same name is already in the list
    public void addTask(Task task) throws AlreadyInToDoListException, MissingPrerequisiteException {
        if (tasks.containsKey(task.getName())) {
            throw new AlreadyInToDoListException();
        }
        for (Task prereq : task.getPrereqs()) {
            if (!tasks.containsKey(prereq.getName())) {
                throw new MissingPrerequisiteException();
            }
        }

        tasks.put(task.getName(),task);

        Collection<Task> prereqs = task.getPrereqs();
        for (Task prereq : prereqs) {
            prereqRelations.add(new Pair<>(prereq,task));
        }
    } // note: cycles are impossible, so long as prereqs can't be modified after task creation

    // MODIFIES: this
    // EFFECTS: removes task if list contains it, recursively removes all tasks with task as a prereq
    public void removeTask(Task task) {
        tasks.remove(task.getName());

        prereqRelations.removeIf(pair -> pair.getValue() == task);

        Set<Task> toRemove = new HashSet<>();
        for (Pair<Task,Task> pair : prereqRelations) {
            if (pair.getKey() == task) {
                toRemove.add(pair.getValue());
            }
        }
        for (Task t : toRemove) {
            removeTask(t);
        }
    }

    // EFFECTS: returns true if the list contains task with given name
    public boolean hasTaskWithName(String name) {
        return tasks.containsKey(name);
    }

    // EFFECTS: returns tasks stored in the to-do list, in order
    //          tasks are returned such that every task is preceded by its prereqs
    public List<Task> orderedTasks() { // return List or Queue?
        List<Task> list = new LinkedList<>();
        Set<Task> visited = new HashSet<>(); // keeps track of names of visited
        // note: since tasks with duplicate names aren't added, could just store strings
        Collection<Task> taskCollection = tasks.values();
        for (Task curr: taskCollection) {
            orderTask(list, visited, curr);
        }

        return list;
    }

    // helper for orderedTasks
    // EFFECTS: adds curr to list such that it is behind all of its prerequisites
    private void orderTask(List<Task> list, Set<Task> visited, Task curr) {
        // do a post order traversal of "tree" rooted at this Task
        // if this task hasn't yet been visited
        if (!visited.contains(curr)) {
            // mark as visited
            visited.add(curr);
            // recurse on prereqs
            for (Task next : curr.getPrereqs()) {
                orderTask(list, visited, next);
            }
            // add to end of list
            list.add(curr);
        }
    }

}
