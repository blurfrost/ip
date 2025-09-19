package performative.tasks;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an event task that extends the basic Task class.
 * A task with a description, completion status, and start/end date/time.
 */
public class Event extends Task {
    private static final Map<String, DayOfWeek> DAY_OF_WEEK_MAP = setupDayOfWeekMap();
    private static final String FOUR_DIGIT_FORMAT = "\\d{4}";
    private static final String SPACES_REGEX = "\\s+";
    private static final String DATE_DISPLAY_FORMAT = "dd MMM yyyy HHmm";
    private static final String DATE_SAVE_FORMAT = "yyyy-MM-dd HHmm";

    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Constructs a new Event task with the specified description, start time, and end time.
     *
     * @param description The description of the event task.
     * @param start The start date and time in "yyyy-MM-dd HHmm" format or day of week.
     * @param end The end date and time in "yyyy-MM-dd HHmm" format or day of week.
     * @throws DateTimeParseException If either date-time string cannot be parsed.
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = parseDateTime(start);
        this.end = parseDateTime(end);
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
     * Supports "yyyy-MM-dd HHmm" format, day-of-week formats, and day-of-week with time (e.g., "Mon 1900").
     *
     * @param dateTimeString Date-time string in various supported formats.
     * @return LocalDateTime object representing the parsed date-time.
     * @throws DateTimeParseException If the string cannot be parsed.
     */
    private LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        String input = dateTimeString.toLowerCase().trim();

        LocalDateTime dayResult = tryParseDayFormat(input);
        if (dayResult != null) {
            return dayResult;
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(DATE_SAVE_FORMAT);
        return LocalDateTime.parse(dateTimeString, inputFormatter);
    }

    /**
     * Attempts to parse input as a day-of-week format (with or without time).
     * Handles both "Day Time" (e.g., "Mon 1900") and "Day" (e.g., "Monday") formats.
     *
     * @param input The trimmed and lowercase input string.
     * @return LocalDateTime if parsing succeeds, null if parsing fails.
     */
    private LocalDateTime tryParseDayFormat(String input) {
        String[] parts = input.split(SPACES_REGEX);

        if (parts.length == 2) {
            DayOfWeek dayOfWeek = DAY_OF_WEEK_MAP.get(parts[0]);
            if (dayOfWeek != null) {
                return tryParseDayWithTime(dayOfWeek, parts[1]);
            }
        }

        if (parts.length == 1) {
            DayOfWeek dayOfWeek = DAY_OF_WEEK_MAP.get(input);
            if (dayOfWeek != null) {
                return getNextDayOfWeek(dayOfWeek);
            }
        }

        return null;
    }

    /**
     * Gets the next occurrence of the specified day of the week.
     * If today is the specified day, returns next week's occurrence.
     * Sets the time to 09:00 (start of work day) for day-of-week events.
     *
     * @param dayOfWeek The target day of the week.
     * @return LocalDateTime representing the next occurrence of the day.
     */
    private LocalDateTime getNextDayOfWeek(DayOfWeek dayOfWeek) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextOccurrence = now.with(TemporalAdjusters.next(dayOfWeek));

        return nextOccurrence.withHour(9).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * Attempts to parse a day of week with a time component.
     *
     * @param dayOfWeek The day of the week.
     * @param timeStr The time string to parse (expected format: HHMM).
     * @return LocalDateTime if parsing succeeds, null if parsing fails.
     */
    private LocalDateTime tryParseDayWithTime(DayOfWeek dayOfWeek, String timeStr) {
        if (!timeStr.matches(FOUR_DIGIT_FORMAT)) {
            return null;
        }

        try {
            int hour = Integer.parseInt(timeStr.substring(0, 2));
            int minute = Integer.parseInt(timeStr.substring(2, 4));

            if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59) {
                return getNextDayOfWeekWithTime(dayOfWeek, hour, minute);
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return null;
        }

        return null;
    }

    /**
     * Gets the next occurrence of the specified day of the week with custom time.
     * If today is the specified day, returns next week's occurrence.
     *
     * @param dayOfWeek The target day of the week.
     * @param hour The hour (0-23).
     * @param minute The minute (0-59).
     * @return LocalDateTime representing the next occurrence of the day with specified time.
     */
    private LocalDateTime getNextDayOfWeekWithTime(DayOfWeek dayOfWeek, int hour, int minute) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextOccurrence = now.with(TemporalAdjusters.next(dayOfWeek));

        return nextOccurrence.withHour(hour).withMinute(minute).withSecond(0).withNano(0);
    }

    /**
     * Formats a LocalDateTime object into a human-readable string.
     *
     * @param dateTime The LocalDateTime object to format.
     * @return Formatted date-time string in "dd MMM yyyy HHmm" format.
     */
    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_DISPLAY_FORMAT));
    }

    /**
     * Returns the event task in a format suitable for saving to a file.
     *
     * @return String representation for file storage with Event type identifier and start/end times.
     */
    @Override
    public String toSaveFormat() {
        DateTimeFormatter saveFormatter = DateTimeFormatter.ofPattern(DATE_SAVE_FORMAT);
        return "Event; " + (super.isDone() ? "Complete" : "Incomplete") + "; "
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
