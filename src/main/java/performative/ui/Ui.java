package performative.ui;

import java.util.ArrayList;

import performative.tasks.Task;

/**
 * Handles user interface operations for the Performative application.
 * Manages all console output and user interaction messages.
 */
public class Ui {
    /**
     * Constructs a new Ui instance.
     */
    public Ui() {
    }

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
     * Displays an error message for invalid number format input.
     */
    public void invalidNumberFormat() {
        System.out.println("Invalid number format");
    }

    /**
     * Displays an error message for invalid task number with the valid range.
     *
     * @param taskNumber The maximum valid task number.
     */
    public void invalidTaskNumber(int taskNumber) {
        System.out.println("Invalid task number. Input a task from 1 to " + taskNumber);
    }

    /**
     * Displays an error message for invalid mark/unmark command format.
     */
    public void invalidMarkCommand() {
        System.out.println("Invalid format for mark/unmark command. Correct format: mark/unmark <task number>");
    }

    /**
     * Displays an error message for invalid delete command format.
     */
    public void invalidDeleteCommand() {
        System.out.println("Invalid format for delete command. Correct format: delete <task number>");
    }

    /**
     * Displays an error message for unsupported commands.
     */
    public void unsupportedCommand() {
        System.out.println("Erm actually, your keyword's not supported");
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
     * Displays a warning message for unknown task types found in save file.
     *
     * @param type The unknown task type that was encountered.
     */
    public void unknownTaskFound(String type) {
        System.out.println("Unknown task type found in save: " + type);
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
     * Displays a confirmation message when a task is marked or unmarked.
     *
     * @param task The task that was marked or unmarked.
     */
    public void markTaskMessage(Task task) {
        printLine();
        System.out.println("Marked this task as " + (task.getStatus() ? "done:" : "undone:") + "\n" + task);
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
     * Displays a confirmation message when a task is added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks after addition.
     */
    public void addTaskMessage(Task task, int taskCount) {
        printLine();
        System.out.println("Added: " + task);
        System.out.println("There are now " + taskCount + " tasks in the list");
    }

    /**
     * Displays a confirmation message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param taskNumber The number of the deleted task.
     * @param taskCount The total number of tasks after deletion.
     */
    public void deleteTaskMessage(Task task, int taskNumber, int taskCount) {
        printLine();
        System.out.println("Deleted task " + taskNumber + ": " + task);
        System.out.println("There are now " + taskCount + " tasks in the list");
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
