import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private LocalDateTime by;

    public Deadline(String description, String by) throws DateTimeParseException {
        super(description);
        this.by = parseDateTime(by);
    }

    private LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return LocalDateTime.parse(dateTimeString, inputFormatter);
    }

    public String formatBy() {
        // Format as "dd MMM yyyy HHmm"
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HHmm");
        return by.format(outputFormatter);
    }

    @Override
    public String toSaveFormat() {
        // Save in original YYYY-MM-DD HHMM format
        DateTimeFormatter saveFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "Deadline; " + (super.getStatus() ? "Complete" : "Incomplete") + "; " + super.getDescription() + "; " + by.format(saveFormatter);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatBy() + ")";
    }
}