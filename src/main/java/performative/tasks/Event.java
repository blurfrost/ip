package performative.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task that extends the basic Task class.
 * A task with a description, completion status, and start/end date/time.
 */
public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Constructs a new Event task with the specified description, start time, and end time.
     *
     * @param description The description of the event task.
     * @param start The start date and time in "yyyy-MM-dd HHmm" format.
     * @param end The end date and time in "yyyy-MM-dd HHmm" format.
     * @throws DateTimeParseException If either date-time string cannot be parsed.
     */

    public Event(String description, String start, String end) {
        super(description);
        this.start = parseDateTime(start);
        this.end = parseDateTime(end);
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
     * Returns the event task in a format suitable for saving to a file.
     *
     * @return String representation for file storage with Event type identifier and start/end times.
     */
    @Override
    public String toSaveFormat() {
        DateTimeFormatter saveFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "Event; " + (super.getStatus() ? "Complete" : "Incomplete") + "; "
                + super.getDescription() + "; " + start.format(saveFormatter) + "; " + end.format(saveFormatter);
    }

    /**
     * Returns a string representation of the event task for display purposes.
     *
     * @return String representation with [E] prefix and formatted start/end times.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + formatDateTime(this.start) + ", to: "
                + formatDateTime(this.end) + ")";
    }
}