package performative.tasks;

public class Task {
    private boolean status;
    private String description;

    public Task(String description) {
        this.status = false;
        this.description = description;
    }

    public void markDone() {
        this.status = true;
    }

    public void markUndone() {
        this.status = false;
    }

    public boolean getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public String toSaveFormat() {
        return "performative.tasks.Task; " + (status ? "Complete" : "Incomplete") + "; " + description;
    }

    @Override
    public String toString() {
        return "[" + (status ? "X" : " ") + "] " + description;
    }
}
