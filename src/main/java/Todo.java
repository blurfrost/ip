public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toSaveFormat() {
        return "Todo; " + (super.getStatus() ? "Complete" : "Incomplete") + "; " + super.getDescription();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
