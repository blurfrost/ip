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
     * Adds a new task based on the user input string.
     * Parses the input, adds the task to the task list, displays confirmation, and saves to file.
     *
     * @param input User input string containing task details.
     */
    public void addTask(String input) {
        try {
            Task task = Parser.parseTask(input);
            taskList.addTask(task);

            // Output to terminal
            ui.addTaskMessage(task, taskList.getTaskCount());

            // Save task to file
            storage.saveTask(task);
        } catch (PerformativeException e) {
            ui.exceptionMessage(e.getMessage());
        } catch (IOException e) {
            ui.exceptionMessage("Error writing to save file");
        }
    }

    /**
     * Deletes a task at the specified task number.
     * Displays deletion confirmation and updates the save file.
     *
     * @param taskNumber The number of the task to delete (1-indexed).
     */
    public void deleteTask(int taskNumber) {
        ui.deleteTaskMessage(taskList.deleteTask(taskNumber), taskNumber, taskList.getTaskCount());
        updateFile();
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
     * Updates the task status, displays confirmation, and saves changes to file.
     *
     * @param taskNumber The number of the task to mark as done (1-indexed).
     */
    public void markTask(int taskNumber) {
        Task task = taskList.getTask(taskNumber);
        task.markDone();
        ui.markTaskMessage(task);
        updateFile();
    }

    /**
     * Marks a task as not completed.
     * Updates the task status, displays confirmation, and saves changes to file.
     *
     * @param taskNumber The number of the task to mark as undone (1-indexed).
     */
    public void unmarkTask(int taskNumber) {
        Task task = taskList.getTask(taskNumber);
        task.markUndone();
        ui.markTaskMessage(task);
        updateFile();
    }

    /**
     * Displays all tasks in the current task list.
     */
    public void listTasks() {
        ui.listTasks(taskList.getTasks());
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
     * Searches for tasks containing the specified keyword in their descriptions.
     * Performs case-insensitive matching and displays the results through the UI.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public void findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        ArrayList<Task> allTasks = taskList.getTasks();

        for (Task task : allTasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        ui.showSearchResults(matchingTasks, keyword);
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

            // Use performative.parser.Parser to handle command parsing and execution
            boolean shouldContinue = Parser.parseAndExecute(input, this, ui);
            if (!shouldContinue) {
                scanner.close();
                ui.endChat();
                break;
            }

            ui.printLine();
        }
    }

    public String getResponse(String input) {
        return "Performative heard: " + input;
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