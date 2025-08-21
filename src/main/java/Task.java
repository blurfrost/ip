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

    @Override
    public String toString() {
        return "[" + (status ? "X" : " ") + "] " + description;
    }
}
