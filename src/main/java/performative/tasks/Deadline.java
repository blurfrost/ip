package performative.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task that extends the basic Task class.
 * A task with a description, completion status, and a deadline date/time.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Constructs a new Deadline task with the specified description and deadline.
     *
     * @param description The description of the deadline task.
     * @param by The deadline date and time in "yyyy-MM-dd HHmm" format.
     * @throws DateTimeParseException If the deadline string cannot be parsed.
     */
    public Deadline(String description, String by) throws DateTimeParseException {
        super(description);
        this.by = parseDateTime(by);
    }

    /**
     * Parses a date-time string into a LocalDateTime object.
     *
     * @param dateTimeString Date-time string in "yyyy-MM-dd HHmm" format.
     * @return LocalDateTime object representing the parsed date-time.
     * @throws DateTimeParseException If the string cannot be parsed.
     */
    private LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return LocalDateTime.parse(dateTimeString, inputFormatter);
    }

    /**
     * Formats a LocalDateTime object into a human-readable string.
     *
     * @param dateTime The LocalDateTime object to format.
     * @return Formatted date-time string in "dd MMM yyyy HHmm" format.
     */
    public String formatDateTime(LocalDateTime dateTime) {
        // Format as "dd MMM yyyy HHmm"
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
    }

    /**
     * Returns the deadline task in a format suitable for saving to a file.
     *
     * @return String representation for file storage with Deadline type identifier and deadline.
     */
    @Override
    public String toSaveFormat() {
        // Save in original YYYY-MM-DD HHMM format
        DateTimeFormatter saveFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "performative.tasks.Deadline; " + (super.getStatus() ? "Complete" : "Incomplete") + "; "
                + super.getDescription() + "; " + by.format(saveFormatter);
    }

    /**
     * Returns a string representation of the deadline task for display purposes.
     *
     * @return String representation with [D] prefix and formatted deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatDateTime(this.by) + ")";
    }
}