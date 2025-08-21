public class Task {
    private static int currentTasks = 0;
    private int id;
    private boolean status;
    private String description;

    public Task(String description) {
        // increment currentTasks everytime a new Task is created
        currentTasks += 1;
        this.id = currentTasks;
        this.status = false;
        this.description = description;
    }

    // mark the task as done and return the status as a string
    public String markDone() {
        this.status = true;
        return "[X] " + description;
    }

    // mark the task as undone and return the status as a string
    public String markUndone() {
        this.status = false;
        return "[ ] " + description;
    }

    public static int getCurrentTasks() {
        return currentTasks;
    }

    @Override
    public String toString() {
        return id + "." + "[" + (status ? "X" : " ") + "] " + description;
    }
}
