package performative.parser;

import java.time.format.DateTimeParseException;

import performative.Performative;
import performative.exception.PerformativeException;
import performative.tasks.Deadline;
import performative.tasks.Event;
import performative.tasks.Task;
import performative.tasks.Todo;
import performative.ui.Ui;


/**
 * Handles parsing and execution of user commands in the Performative application.
 * Processes user input and delegates to appropriate methods for task operations.
 */
public class Parser {

    private static final int EXPECTED_COMMAND_PARTS = 2;
    private static final int TASK_NUMBER_INDEX = 1;
    private static final int FIND_KEYWORD_START_INDEX = 5;
    private static final int TODO_DESCRIPTION_START_INDEX = 5;
    private static final int DEADLINE_PREFIX_LENGTH = 9;
    private static final int EVENT_PREFIX_LENGTH = 6;
    private static final int BY_KEYWORD_LENGTH = 5;
    private static final int FROM_KEYWORD_LENGTH = 7;
    private static final int TO_KEYWORD_LENGTH = 5;
    private static final int NOT_FOUND = -1;

    /**
     * Parses user input and executes the corresponding command.
     * Returns a string response for the GUI.
     *
     * @param input User input string containing the command.
     * @param performative The main Performative application instance.
     * @param ui The user interface instance for generating messages.
     * @return String response to be displayed in the GUI.
     */
    public static String parseAndExecute(String input, Performative performative, Ui ui) {
        if (input.equals("bye")) {
            return ui.getByeMessage();
        } else if (input.equals("list")) {
            return performative.listTasks();
        } else if (input.startsWith("mark ") || input.startsWith("unmark ")) {
            return parseMarkUnmark(input, performative, ui);
        } else if (input.startsWith("delete")) {
            return parseDelete(input, performative, ui);
        } else if (input.startsWith("find ")) {
            return parseFind(input, performative, ui);
        } else if (input.startsWith("deadline") || input.startsWith("event") || input.startsWith("todo")) {
            return performative.addTask(input);
        } else {
            return ui.getUnsupportedCommandMessage();
        }
    }

    /**
     * Parses and executes mark or unmark commands.
     * Returns a string response for the GUI.
     *
     * @param input User input string containing mark/unmark command.
     * @param performative The main Performative application instance.
     * @param ui The user interface instance for generating messages.
     * @return String response for the GUI.
     */
    private static String parseMarkUnmark(String input, Performative performative, Ui ui) {
        String[] parts = input.split(" ");
        if (parts.length == EXPECTED_COMMAND_PARTS) {
            try {
                int taskNumber = Integer.parseInt(parts[TASK_NUMBER_INDEX]);
                if (input.startsWith("mark ")) {
                    return performative.markTask(taskNumber);
                } else {
                    return performative.unmarkTask(taskNumber);
                }
            } catch (NumberFormatException e) {
                return ui.getInvalidNumberFormatMessage();
            } catch (IndexOutOfBoundsException e) {
                return ui.getInvalidTaskNumberMessage(performative.getTaskCount());
            }
        } else {
            return ui.getInvalidMarkCommandMessage();
        }
    }

    /**
     * Parses and executes delete commands.
     * Returns a string response for the GUI.
     *
     * @param input User input string containing delete command.
     * @param performative The main Performative application instance.
     * @param ui The user interface instance for generating messages.
     * @return String response for the GUI.
     */
    private static String parseDelete(String input, Performative performative, Ui ui) {
        String[] parts = input.split(" ");
        if (parts.length == EXPECTED_COMMAND_PARTS) {
            try {
                int taskNumber = Integer.parseInt(parts[TASK_NUMBER_INDEX]);
                return performative.deleteTask(taskNumber);
            } catch (NumberFormatException e) {
                return ui.getInvalidNumberFormatMessage();
            } catch (IndexOutOfBoundsException e) {
                return ui.getInvalidTaskNumberMessage(performative.getTaskCount());
            }
        } else {
            return ui.getInvalidDeleteCommandMessage();
        }
    }

    /**
     * Parses task creation input and creates the appropriate task object.
     * Supports todo, deadline, and event task types.
     *
     * @param input User input string containing task details.
     * @return The created Task object.
     * @throws PerformativeException If the input format is invalid or required fields are missing.
     */
    public static Task parseTask(String input) throws PerformativeException {
        if (input.startsWith("todo")) {
            return parseTodo(input);
        } else if (input.startsWith("deadline")) {
            return parseDeadline(input);
        } else if (input.startsWith("event")) {
            return parseEvent(input);
        }
        return new Task(input);
    }

    /**
     * Parses and executes find commands.
     * Returns a string response for the GUI.
     *
     * @param input User input string containing find command.
     * @param performative The main Performative application instance.
     * @param ui The user interface instance for generating messages.
     * @return String response for the GUI.
     */
    private static String parseFind(String input, Performative performative, Ui ui) {
        String keyword = input.substring(FIND_KEYWORD_START_INDEX).trim();
        if (keyword.isEmpty()) {
            return ui.getEmptyFindKeywordMessage();
        } else {
            return performative.findTasks(keyword);
        }
    }

    private static Task parseTodo(String input) throws PerformativeException {
        if (input.equals("todo")) {
            throw new PerformativeException("The description of a todo cannot be empty");
        }
        String description = input.substring(TODO_DESCRIPTION_START_INDEX).trim();
        if (description.isEmpty()) {
            throw new PerformativeException("The description of a todo cannot be empty");
        }
        return new Todo(description);
    }

    private static Task parseDeadline(String input) throws PerformativeException {
        validateDeadlineInput(input);
        String remaining = extractRemainingContent(input, DEADLINE_PREFIX_LENGTH);

        int byIndex = remaining.indexOf(" /by ");
        if (byIndex == NOT_FOUND) {
            throw new PerformativeException(
                    "Deadline format should be: deadline <description> /by <time>");
        }

        String description = remaining.substring(0, byIndex).trim();
        String by = remaining.substring(byIndex + BY_KEYWORD_LENGTH).trim();

        validateDeadlineComponents(description, by);

        try {
            return new Deadline(description, by);
        } catch (DateTimeParseException e) {
            throw new PerformativeException(
                    "The deadline time format is invalid, use YYYY-MM-DD HHMM or a valid date");
        }
    }

    private static Task parseEvent(String input) throws PerformativeException {
        validateEventInput(input);
        String remaining = extractRemainingContent(input, EVENT_PREFIX_LENGTH);

        int fromIndex = remaining.indexOf(" /from ");
        int toIndex = remaining.indexOf(" /to ");

        if (!isValidEventFormat(fromIndex, toIndex)) {
            throw new PerformativeException("Invalid event format, should be: "
                    + "event <description> /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm");
        }

        String description = remaining.substring(0, fromIndex).trim();
        String from = remaining.substring(fromIndex + FROM_KEYWORD_LENGTH, toIndex).trim();
        String to = remaining.substring(toIndex + TO_KEYWORD_LENGTH).trim();

        validateEventComponents(description, from, to);

        try {
            return new Event(description, from, to);
        } catch (DateTimeParseException e) {
            throw new PerformativeException("Invalid event format, should be: "
                    + "event <description> /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm");
        }
    }

    private static void validateDeadlineInput(String input) throws PerformativeException {
        if (input.equals("deadline")) {
            throw new PerformativeException("The description of a deadline cannot be empty");
        }
    }

    private static void validateEventInput(String input) throws PerformativeException {
        if (input.equals("event")) {
            throw new PerformativeException("The description of an event cannot be empty");
        }
    }

    private static String extractRemainingContent(String input, int prefixLength) throws PerformativeException {
        String remaining = input.substring(prefixLength).trim();
        if (remaining.isEmpty()) {
            String taskType = prefixLength == DEADLINE_PREFIX_LENGTH ? "deadline" : "event";
            throw new PerformativeException("The description of a " + taskType + " cannot be empty");
        }
        return remaining;
    }

    private static void validateDeadlineComponents(String description, String by) throws PerformativeException {
        if (description.isEmpty()) {
            throw new PerformativeException("The description of a deadline cannot be empty");
        }
        if (by.isEmpty()) {
            throw new PerformativeException("The deadline time cannot be empty");
        }
    }

    private static boolean isValidEventFormat(int fromIndex, int toIndex) {
        boolean fromIndexExists = fromIndex != NOT_FOUND;
        boolean toIndexExists = toIndex != NOT_FOUND;
        boolean correctOrder = toIndex > fromIndex;

        return fromIndexExists && toIndexExists && correctOrder;
    }

    private static void validateEventComponents(String description, String from, String to)
            throws PerformativeException {
        if (description.isEmpty()) {
            throw new PerformativeException("The description of an event cannot be empty");
        }
        if (from.isEmpty()) {
            throw new PerformativeException("The start time of an event cannot be empty");
        }
        if (to.isEmpty()) {
            throw new PerformativeException("The end time of an event cannot be empty");
        }
    }
}
