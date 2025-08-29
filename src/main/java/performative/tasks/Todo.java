package performative.tasks;

/**
 * Represents a todo task that extends the basic Task class.
 * A simple task with only a description and completion status.
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the todo task in a format suitable for saving to a file.
     *
     * @return String representation for file storage with Todo type identifier.
     */
    @Override
    public String toSaveFormat() {
        return "performative.tasks.Todo; " + (getStatus() ? "Complete" : "Incomplete") + "; " + getDescription();
    }

    /**
     * Returns a string representation of the todo task for display purposes.
     *
     * @return String representation with [T] prefix indicating todo type.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
