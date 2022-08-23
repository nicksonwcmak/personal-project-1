package model;

import model.exceptions.InvalidPrereqsException;

import java.util.List;

// represents a task with name, time to finish, and prerequisite tasks
public class Task {
    private String name;
    private int time;
    private List<Task> prereqs;

    // EFFECTS: creates a task with name, time, prereqs
    public Task(String name, int time, List<Task> prereqs) throws InvalidPrereqsException {
        this.name = name;
        this.time = time;
        if (noSelfPrereq(prereqs)) {
            this.prereqs = prereqs;
        } else {
            throw new InvalidPrereqsException();
        }
    }

    // helper for constructor
    // EFFECTS: returns true if list of prereqs does not contain this
    private boolean noSelfPrereq(List<Task> tasks) {
        for(Task task : tasks) {
            if (task == this) {
                return false;
            }
        }
        return true;
    }
}
