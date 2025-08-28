package performative.tasks;

public class Task {
    private boolean status;
    private String description;

    public Task(String description) {
        this.status = false;
        this.description = description;
    }

    // mark the task as done and return the status as a string
    public void markDone() {
        this.status = true;
    }

    // mark the task as undone and return the status as a string
    public void markUndone() {
        this.status = false;
    }

    public boolean getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    // express the task in a format for saving to a file
    public String toSaveFormat() {
        return "performative.tasks.Task; " + (status ? "Complete" : "Incomplete") + "; " + description;
    }

    @Override
    public String toString() {
        return "[" + (status ? "X" : " ") + "] " + description;
    }
}
