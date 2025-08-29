package performative.parser;

import java.time.format.DateTimeParseException;

import performative.Performative;
import performative.exception.PerformativeException;
import performative.tasks.Deadline;
import performative.tasks.Event;
import performative.tasks.Task;
import performative.tasks.Todo;
import performative.ui.Ui;

public class Parser {

    public static boolean parseAndExecute(String input, Performative performative, Ui ui) {
        if (input.equals("bye")) {
            return false;
        } else if (input.equals("list")) {
            performative.listTasks();
        } else if (input.startsWith("mark ") || input.startsWith("unmark ")) {
            parseMarkUnmark(input, performative, ui);
        } else if (input.startsWith("delete")) {
            parseDelete(input, performative, ui);
        } else if (input.startsWith("find ")) {
            parseFind(input, performative, ui);
        } else if (input.startsWith("deadline") || input.startsWith("event") || input.startsWith("todo")) {
            performative.addTask(input);
        } else {
            ui.unsupportedCommand();
        }
        return true;
    }

    private static void parseMarkUnmark(String input, Performative performative, Ui ui) {
        String[] parts = input.split(" ");
        if (parts.length == 2) {
            try {
                int taskNumber = Integer.parseInt(parts[1]);
                if (input.startsWith("mark ")) {
                    performative.markTask(taskNumber);
                } else {
                    performative.unmarkTask(taskNumber);
                }
            } catch (NumberFormatException e) {
                ui.invalidNumberFormat();
            } catch (IndexOutOfBoundsException e) {
                ui.invalidTaskNumber(performative.getTaskCount());
            }
        } else {
            ui.invalidMarkCommand();
        }
    }

    private static void parseDelete(String input, Performative performative, Ui ui) {
        String[] parts = input.split(" ");
        if (parts.length == 2) {
            try {
                int taskNumber = Integer.parseInt(parts[1]);
                performative.deleteTask(taskNumber);
            } catch (NumberFormatException e) {
                ui.invalidNumberFormat();
            } catch (IndexOutOfBoundsException e) {
                ui.invalidTaskNumber(performative.getTaskCount());
            }
        } else {
            ui.invalidDeleteCommand();
        }
    }

    /**
     * Parses and executes find commands.
     * Extracts the keyword from the input and validates it before searching.
     *
     * @param input User input string containing find command.
     * @param performative The main Performative application instance.
     * @param ui The user interface instance for displaying messages.
     */
    private static void parseFind(String input, Performative performative, Ui ui) {
        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            ui.emptyFindKeyword();
        } else {
            performative.findTasks(keyword);
        }
    }

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

    private static Task parseTodo(String input) throws PerformativeException {
        if (input.equals("todo")) {
            throw new PerformativeException("The description of a todo cannot be empty");
        }
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new PerformativeException("The description of a todo cannot be empty");
        }
        return new Todo(description);
    }

    private static Task parseDeadline(String input) throws PerformativeException {
        if (input.equals("deadline")) {
            throw new PerformativeException("The description of a deadline cannot be empty");
        }
        String remaining = input.substring(9).trim();
        if (remaining.isEmpty()) {
            throw new PerformativeException("The description of a deadline cannot be empty");
        }
        int byIndex = remaining.indexOf(" /by ");
        if (byIndex != -1) {
            String description = remaining.substring(0, byIndex).trim();
            String by = remaining.substring(byIndex + 5).trim();
            if (description.isEmpty()) {
                throw new PerformativeException("The description of a deadline cannot be empty");
            }
            if (by.isEmpty()) {
                throw new PerformativeException("The deadline time cannot be empty");
            }
            try {
                return new Deadline(description, by);
            } catch (DateTimeParseException e) {
                throw new PerformativeException("The deadline time format is invalid, use YYYY-MM-DD HHMM or a valid date");
            }
        } else {
            throw new PerformativeException("performative.tasks.Deadline format should be: deadline <description> /by <time>");
        }
    }

    private static Task parseEvent(String input) throws PerformativeException {
        if (input.equals("event")) {
            throw new PerformativeException("The description of an event cannot be empty");
        }
        String remaining = input.substring(6).trim();
        if (remaining.isEmpty()) {
            throw new PerformativeException("The description of an event cannot be empty");
        }
        int fromIndex = remaining.indexOf(" /from ");
        int toIndex = remaining.indexOf(" /to ");
        if (fromIndex != -1 && toIndex != -1 && toIndex > fromIndex) {
            String description = remaining.substring(0, fromIndex).trim();
            String from = remaining.substring(fromIndex + 7, toIndex).trim();
            String to = remaining.substring(toIndex + 5).trim();
            if (description.isEmpty()) {
                throw new PerformativeException("The description of an event cannot be empty");
            }
            if (from.isEmpty()) {
                throw new PerformativeException("The start time of an event cannot be empty");
            }
            if (to.isEmpty()) {
                throw new PerformativeException("The end time of an event cannot be empty");
            }
            try {
                return new Event(description, from, to);
            } catch (DateTimeParseException e) {
                throw new PerformativeException("Invalid event format, should be: event <description> /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm");
            }
        } else {
            throw new PerformativeException("Invalid event format, should be: event <description> /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm");
        }
    }
}
