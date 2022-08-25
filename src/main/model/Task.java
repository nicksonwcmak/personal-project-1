package model;

import java.util.ArrayList;
import java.util.List;

// represents a task with name, time to finish, and prerequisite tasks
public class Task {
    private String name;
    private int time;
    private List<Task> prereqs;

    // EFFECTS: creates a task with name, time, no prereqs
    public Task(String name, int time) {
        this.name = name;
        this.time = time;
        this.prereqs = new ArrayList<>();
    }

    // EFFECTS: creates a task with name, time, listed prereqs
    public Task(String name, int time, List<Task> prereqs) {
        this.name = name;
        this.time = time;
        this.prereqs = prereqs; // impossible to construct task w/ itself as a prereq, no need to check
    }

    // EFFECTS: returns true if list of prereqs does not contain this
//    private boolean noSelfPrereq(List<Task> tasks) {
//        for(Task task : tasks) {
//            if (task == this) {
//                return false;
//            }
//        }
//        return true;
//    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public List<Task> getPrereqs() {
        return prereqs;
    }
}
