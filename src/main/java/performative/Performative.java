package performative;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
     * Initializes the application for GUI mode.
     * Sets up the task list and storage without running the CLI loop.
     */
    private void initializeForGui() {
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
    public void updateFile() {
        try {
            storage.saveTasks(taskList.getTasks());
        } catch (IOException e) {
            ui.exceptionMessage("Error writing to save file");
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
     * Runs the main application loop.
     * Initializes the save file, loads existing tasks, and handles user interactions
     * until the user chooses to exit.
     */
    public void run() {
        // Initialize save file, or create one if it doesn't exist
        try {
            new TaskList();
            ui.detectSaveStatus(storage.fileExists());

            if (!storage.fileExists()) {
                // If save file doesn't exist, create it
                taskList = new TaskList();
                boolean created = storage.initializeFile();
                ui.saveCreatedStatus(created);
                if (!created) {
                    throw new IOException("Could not create save file");
                }
            } else {
                // If save file exists, load tasks from it
                taskList = new TaskList(storage.loadTasks());

                ui.loadTasksStatus(taskList.getTaskCount());
                ui.listTasks(taskList.getTasks());
            }
            ui.completeInitMessage();
        } catch (IOException e) {
            ui.cannotInitializeSaveFile();
        }

        ui.greetUser();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                scanner.close();
                ui.endChat();
                break;
            }

            // Get response from GUI-compatible parsing and display it
            String response = Parser.parseAndExecute(input, this, ui);
            ui.printLine();
            System.out.println(response);
            ui.printLine();
        }
    }

    public String getResponse(String input) {
        initializeForGui();

        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            return ui.getUnsupportedCommandMessage();
        }

        return Parser.parseAndExecute(trimmedInput, this, ui);
    }

    /**
     * Main entry point for the Performative application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Performative("./data/savefile.txt").run();
    }
}
