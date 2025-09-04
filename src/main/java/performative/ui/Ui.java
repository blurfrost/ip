package performative.ui;

import java.util.ArrayList;

import performative.tasks.Task;

/**
 * Handles user interface operations for the Performative application.
 * Manages all user interaction messages for GUI display.
 */
public class Ui {
    /**
     * Constructs a new Ui instance.
     */
    public Ui() {
    }

    /**
     * Returns an error message for unsupported commands.
     *
     * @return Error message string.
     */
    public String getUnsupportedCommandMessage() {
        return "Erm actually, your keyword's not supported";
    }

    /**
     * Returns a confirmation message when a task is added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks after addition.
     * @return Confirmation message string.
     */
    public String getAddTaskMessage(Task task, int taskCount) {
        return "Added: " + task + "\nThere are now " + taskCount + " tasks in the list";
    }

    /**
     * Returns a confirmation message when a task is marked or unmarked.
     *
     * @param task The task that was marked or unmarked.
     * @return Confirmation message string.
     */
    public String getMarkTaskMessage(Task task) {
        return "Marked this task as " + (task.getStatus() ? "done:" : "undone:") + "\n" + task;
    }

    /**
     * Returns a confirmation message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param taskNumber The number of the deleted task.
     * @param taskCount The total number of tasks after deletion.
     * @return Confirmation message string.
     */
    public String getDeleteTaskMessage(Task task, int taskNumber, int taskCount) {
        return "Deleted task " + taskNumber + ": " + task + "\nThere are now " + taskCount + " tasks in the list";
    }

    /**
     * Returns all tasks in the provided list with numbering.
     *
     * @param tasks ArrayList of tasks to be displayed.
     * @return Formatted task list string.
     */
    public String getListTasksMessage(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "No tasks in your list yet!";
        }

        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i));
            if (i < tasks.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Returns the search results for a find operation.
     *
     * @param matchingTasks ArrayList of tasks that contain the search keyword.
     * @param keyword The keyword that was searched for.
     * @return Search results message string.
     */
    public String getSearchResultsMessage(ArrayList<Task> matchingTasks, String keyword) {
        if (matchingTasks.isEmpty()) {
            return "No tasks found containing the keyword: " + keyword;
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append((i + 1)).append(". ").append(matchingTasks.get(i));
                if (i < matchingTasks.size() - 1) {
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
    }

    /**
     * Returns an error message for invalid number format input.
     *
     * @return Error message string.
     */
    public String getInvalidNumberFormatMessage() {
        return "Invalid number format";
    }

    /**
     * Returns an error message for invalid task number with the valid range.
     *
     * @param taskCount The maximum valid task number.
     * @return Error message string.
     */
    public String getInvalidTaskNumberMessage(int taskCount) {
        return "Invalid task number. Input a task from 1 to " + taskCount;
    }

    /**
     * Returns an error message for invalid mark/unmark command format.
     *
     * @return Error message string.
     */
    public String getInvalidMarkCommandMessage() {
        return "Invalid format for mark/unmark command. Correct format: mark/unmark <task number>";
    }

    /**
     * Returns an error message for invalid delete command format.
     *
     * @return Error message string.
     */
    public String getInvalidDeleteCommandMessage() {
        return "Invalid format for delete command. Correct format: delete <task number>";
    }

    /**
     * Returns an error message when no keyword is provided for the find command.
     *
     * @return Error message string.
     */
    public String getEmptyFindKeywordMessage() {
        return "Please provide a keyword to search for. Correct format: find <keyword>";
    }

    /**
     * Returns a goodbye message when the user exits the application.
     *
     * @return Goodbye message string.
     */
    public String getByeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    // Legacy console methods for CLI compatibility

    /**
     * Prints a horizontal line separator to the console.
     */
    public void printLine() {
        for (int i = 0; i < 40; i++) {
            System.out.print("─");
        }
        System.out.println();
    }

    /**
     * Displays an error message when the save file cannot be initialized.
     */
    public void cannotInitializeSaveFile() {
        System.out.println("Error initializing save file");
    }

    /**
     * Displays the welcome greeting to the user.
     */
    public void greetUser() {
        System.out.println("Hello! I'm Performative.\nWhat can I do for you?");
        printLine();
    }

    /**
     * Displays the goodbye message when the user exits the application.
     */
    public void endChat() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Displays the status of save file detection.
     *
     * @param doesExist True if the save file exists, false otherwise.
     */
    public void detectSaveStatus(boolean doesExist) {
        if (doesExist) {
            System.out.println("Save found, loading save");
        } else {
            System.out.println("No save found, creating new save");
        }
    }

    /**
     * Displays the status of save file creation.
     *
     * @param isCreated True if the save file was successfully created, false otherwise.
     */
    public void saveCreatedStatus(boolean isCreated) {
        if (isCreated) {
            System.out.println("Save created successfully");
        } else {
            System.out.println("Error creating save");
        }
    }

    /**
     * Displays the status of task loading from save file.
     *
     * @param taskCount The number of tasks loaded from the save file.
     */
    public void loadTasksStatus(int taskCount) {
        if (taskCount > 0) {
            System.out.println("Loading " + taskCount + " tasks from save");
        } else {
            System.out.println("No tasks in save");
        }
    }

    /**
     * Displays a message indicating that initialization is complete.
     */
    public void completeInitMessage() {
        printLine();
        System.out.println("Initialization complete");
        printLine();
    }

    /**
     * Displays all tasks in the provided list with numbering.
     *
     * @param tasks ArrayList of tasks to be displayed.
     */
    public void listTasks(ArrayList<Task> tasks) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Displays an exception or error message.
     *
     * @param message The error message to be displayed.
     */
    public void exceptionMessage(String message) {
        printLine();
        System.out.println(message);
    }
}

