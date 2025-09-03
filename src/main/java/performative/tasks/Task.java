package performative.tasks;

/**
 * Represents a basic task with a description and completion status.
 * Serves as the base class for more specific task types.
 */
public class Task {
    private boolean status;
    private String description;

    /**
     * Constructs a new Task with the specified description.
     * The task is initially marked as incomplete.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.status = false;
        this.description = description;
    }

    /**
     * Marks the task as completed.
     */
    public void markDone() {
        this.status = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markUndone() {
        this.status = false;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return True if the task is completed, false otherwise.
     */
    public boolean getStatus() {
        return this.status;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the task in a format suitable for saving to a file.
     * Includes task type, completion status, and description.
     *
     * @return String representation for file storage.
     */
    public String toSaveFormat() {
        return "Task; " + (status ? "Complete" : "Incomplete") + "; " + description;
    }

    /**
     * Returns a string representation of the task for display purposes.
     * Shows completion status with an X or space, followed by the description.
     *
     * @return String representation of the task.
     */
    @Override
    public String toString() {
        return "[" + (status ? "X" : " ") + "] " + description;
    }
}
