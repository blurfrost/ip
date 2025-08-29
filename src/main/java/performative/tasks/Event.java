package performative.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    public Event(String description, String start, String end) {
        super(description);
        this.start = parseDateTime(start);
        this.end = parseDateTime(end);
    }

    private LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return LocalDateTime.parse(dateTimeString, inputFormatter);
    }

    public String formatDateTime(LocalDateTime dateTime) {
        // Format as "dd MMM yyyy HHmm"
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
    }

    @Override
    public String toSaveFormat() {
        DateTimeFormatter saveFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "performative.tasks.Event; " + (super.getStatus() ? "Complete" : "Incomplete") + "; "
                + super.getDescription() + "; " + start.format(saveFormatter) + "; " + end.format(saveFormatter);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + formatDateTime(this.start) + ", to: "
                + formatDateTime(this.end) + ")";
    }
}