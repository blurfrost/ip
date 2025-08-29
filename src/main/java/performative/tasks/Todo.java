package performative.tasks;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toSaveFormat() {
        return "performative.tasks.Todo; " + (super.getStatus() ? "Complete" : "Incomplete")
                + "; " + super.getDescription();
    }
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
