package performative.tasks;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a deadline task that extends the basic Task class.
 * A task with a description, completion status, and a deadline date/time.
 */
public class Deadline extends Task {
    private static final Map<String, DayOfWeek> DAY_OF_WEEK_MAP = setupDayOfWeekMap();

    private LocalDateTime by;

    /**
     * Constructs a new Deadline task with the specified description and deadline.
     *
     * @param description The description of the deadline task.
     * @param by The deadline date and time in "yyyy-MM-dd HHmm" format or day of week.
     * @throws DateTimeParseException If the deadline string cannot be parsed.
     */
    public Deadline(String description, String by) throws DateTimeParseException {
        super(description);
        this.by = parseDateTime(by);
    }

    private static HashMap<String, DayOfWeek> setupDayOfWeekMap() {
        HashMap<String, DayOfWeek> daysOfWeek = new HashMap<>();

        daysOfWeek.put("monday", DayOfWeek.MONDAY);
        daysOfWeek.put("tuesday", DayOfWeek.TUESDAY);
        daysOfWeek.put("wednesday", DayOfWeek.WEDNESDAY);
        daysOfWeek.put("thursday", DayOfWeek.THURSDAY);
        daysOfWeek.put("friday", DayOfWeek.FRIDAY);
        daysOfWeek.put("saturday", DayOfWeek.SATURDAY);
        daysOfWeek.put("sunday", DayOfWeek.SUNDAY);
        daysOfWeek.put("mon", DayOfWeek.MONDAY);
        daysOfWeek.put("tue", DayOfWeek.TUESDAY);
        daysOfWeek.put("wed", DayOfWeek.WEDNESDAY);
        daysOfWeek.put("thu", DayOfWeek.THURSDAY);
        daysOfWeek.put("fri", DayOfWeek.FRIDAY);
        daysOfWeek.put("sat", DayOfWeek.SATURDAY);
        daysOfWeek.put("sun", DayOfWeek.SUNDAY);

        return daysOfWeek;
    }

    /**
     * Parses a date-time string into a LocalDateTime object.
     * Supports both "yyyy-MM-dd HHmm" format and day-of-week formats.
     *
     * @param dateTimeString Date-time string in "yyyy-MM-dd HHmm" format or day of week.
     * @return LocalDateTime object representing the parsed date-time.
     * @throws DateTimeParseException If the string cannot be parsed.
     */
    private LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        // First try to parse as day of week
        DayOfWeek dayOfWeek = DAY_OF_WEEK_MAP.get(dateTimeString.toLowerCase().trim());
        if (dayOfWeek != null) {
            return getNextDayOfWeek(dayOfWeek);
        }

        // If not a day of week, try the original format
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return LocalDateTime.parse(dateTimeString, inputFormatter);
    }

    /**
     * Gets the next occurrence of the specified day of the week.
     * If today is the specified day, returns next week's occurrence.
     * Sets the time to 23:59 (end of day).
     *
     * @param dayOfWeek The target day of the week.
     * @return LocalDateTime representing the next occurrence of the day.
     */
    private LocalDateTime getNextDayOfWeek(DayOfWeek dayOfWeek) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextOccurrence = now.with(TemporalAdjusters.next(dayOfWeek));

        // Set time to 23:59 (end of day) for day-of-week deadlines
        return nextOccurrence.withHour(23).withMinute(59).withSecond(0).withNano(0);
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
        return "Deadline; " + (super.getStatus() ? "Complete" : "Incomplete") + "; "
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
