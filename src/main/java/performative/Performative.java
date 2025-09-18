package performative;

import java.io.IOException;
import java.util.ArrayList;

import performative.exception.PerformativeException;
import performative.parser.Parser;
import performative.storage.Storage;
import performative.tasks.Task;
import performative.tasks.TaskList;
import performative.ui.Ui;

/**
 * Represents the main application class for the Performative task management system.
 * Manages the interaction between the user interface, task storage, and task operations.
 */
public class Performative {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private boolean isInitialized = false;

    /**
     * Constructs a new Performative application instance.
     *
     * @param filePath Path to the file where tasks will be saved and loaded from.
     */
    public Performative(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
    }

    /**
     * Initializes the application.
     * Sets up the task list and storage.
     */
    private void initialize() {
        if (isInitialized) {
            return;
        }

        try {
            if (!storage.fileExists()) {
                taskList = new TaskList();
                storage.initializeFile();
            } else {
                taskList = new TaskList(storage.loadTasks());
            }
            isInitialized = true;
        } catch (IOException e) {
            taskList = new TaskList();
            isInitialized = true;
        }
    }

    /**
     * Adds a new task based on the user input string.
     * Returns a confirmation message string.
     *
     * @param input User input string containing task details.
     * @return Confirmation message string.
     */
    public String addTask(String input) {
        try {
            Task task = Parser.parseTask(input);
            taskList.addTask(task);
            storage.saveTask(task);
            return ui.getAddTaskMessage(task, taskList.getTaskCount());
        } catch (PerformativeException e) {
            return e.getMessage();
        } catch (IOException e) {
            return "Error writing to save file";
        }
    }

    /**
     * Deletes a task at the specified task number.
     * Returns a confirmation message string.
     *
     * @param taskNumber The number of the task to delete (1-indexed).
     * @return Confirmation message string.
     */
    public String deleteTask(int taskNumber) {
        try {
            Task deletedTask = taskList.deleteTask(taskNumber);
            updateFile();
            return ui.getDeleteTaskMessage(deletedTask, taskNumber, taskList.getTaskCount());
        } catch (IndexOutOfBoundsException e) {
            return ui.getInvalidTaskNumberMessage(taskList.getTaskCount());
        }
    }

    /**
     * Updates the save file with the current list of tasks.
     * Rewrites the entire save file with all tasks in the current task list.
     */
    private void updateFile() {
        try {
            storage.saveTasks(taskList.getTasks());
        } catch (IOException e) {
            return;
        }
    }

    /**
     * Marks a task as completed.
     * Returns a confirmation message string.
     *
     * @param taskNumber The number of the task to mark as done (1-indexed).
     * @return Confirmation message string.
     */
    public String markTask(int taskNumber) {
        try {
            Task task = taskList.getTask(taskNumber);
            task.markDone();
            updateFile();
            return ui.getMarkTaskMessage(task);
        } catch (IndexOutOfBoundsException e) {
            return ui.getInvalidTaskNumberMessage(taskList.getTaskCount());
        }
    }

    /**
     * Marks a task as not completed.
     * Returns a confirmation message string.
     *
     * @param taskNumber The number of the task to mark as undone (1-indexed).
     * @return Confirmation message string.
     */
    public String unmarkTask(int taskNumber) {
        try {
            Task task = taskList.getTask(taskNumber);
            task.markUndone();
            updateFile();
            return ui.getMarkTaskMessage(task);
        } catch (IndexOutOfBoundsException e) {
            return ui.getInvalidTaskNumberMessage(taskList.getTaskCount());
        }
    }

    /**
     * Returns all tasks in the current task list.
     *
     * @return Formatted task list string.
     */
    public String listTasks() {
        return ui.getListTasksMessage(taskList.getTasks());
    }

    /**
     * Returns the current number of tasks in the task list.
     *
     * @return The total count of tasks.
     */
    public int getTaskCount() {
        return taskList.getTaskCount();
    }

    /**
     * Searches for tasks containing the specified keyword.
     * Returns search results as a formatted string.
     *
     * @param keyword The keyword to search for in task descriptions.
     * @return Search results string.
     */
    public String findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        ArrayList<Task> allTasks = taskList.getTasks();

        for (Task task : allTasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        return ui.getSearchResultsMessage(matchingTasks, keyword);
    }

    /**
     * Returns the welcome message from the UI.
     *
     * @return Welcome message string.
     */
    public String getWelcomeMessage() {
        return ui.getWelcomeMessage();
    }

    /**
     * Handles user input and returns appropriate response for GUI.
     *
     * @param input User input string.
     * @return Response string to be displayed in GUI.
     */
    public String getResponse(String input) {
        initialize();

        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            return ui.getUnsupportedCommandMessage();
        }

        return Parser.parseAndExecute(trimmedInput, this, ui);
    }

    /**
     * Main entry point for the Performative application.
     * Launches the GUI application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Hello!");
    }
}
