package model;

import java.util.HashSet;
import java.util.Set;

// represents a task with name, time to finish, and prerequisite tasks
public class Task {
    private String name; // name of task
    private int duration; // time to complete
    private Set<Task> prereqs;

    // EFFECTS: creates a task with name, time, no prereqs
    public Task(String name, int time) {
        this.name = name;
        this.duration = time;
        this.prereqs = new HashSet<>();
    }

    // EFFECTS: creates a task with name, time, listed prereqs
    public Task(String name, int time, Set<Task> prereqs) {
        this.name = name;
        this.duration = time;
        this.prereqs = prereqs; // impossible to construct task w/ itself as a prereq, no need to check
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public Set<Task> getPrereqs() {
        return prereqs;
    }

}
