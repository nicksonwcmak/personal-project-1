package model;

import java.util.Collection;
import java.util.HashSet;

// represents a task with name, time to finish, and prerequisite tasks
public class Task {
    private String name; // name of task
    private int duration; // time to complete
    private Collection<Task> prereqs; // collection of prerequisite tasks; any collection should work (?)

    // EFFECTS: creates a task with name, time, no prereqs
    public Task(String name, int time) {
        this.name = name;
        this.duration = time;
        this.prereqs = new HashSet<>(); // just need an empty Collection, specific class doesn't matter
    }

    // EFFECTS: creates a task with name, time, listed prereqs
    public Task(String name, int time, Collection<Task> prereqs) {
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

    public Collection<Task> getPrereqs() {
        return prereqs;
    }

}
