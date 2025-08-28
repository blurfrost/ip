package performative;

// Java standard library imports
import performative.parser.Parser;

import java.io.IOException;
import java.util.Scanner;

public class Performative {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    public Performative(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
    }

    public void addTask(String input) {
        try {
            Task task = Parser.parseTask(input);
            taskList.addTask(task);

            // output to terminal
            ui.addTaskMessage(task, taskList.getTaskCount());

            // save task to file
            storage.saveTask(task);
        } catch (PerformativeException e) {
            ui.exceptionMessage(e.getMessage());
        } catch (IOException e) {
            ui.exceptionMessage("Error writing to save file");
        }
    }

    public void deleteTask(int taskNumber) {
        ui.deleteTaskMessage(taskList.deleteTask(taskNumber), taskNumber, taskList.getTaskCount());
        updateFile();
    }

    // rewrite the entire save file with the current list of tasks
    public void updateFile() {
        try {
            storage.saveTasks(taskList.getTasks());
        } catch (IOException e) {
            ui.exceptionMessage("Error writing to save file");
        }
    }

    public void markTask(int taskNumber) {
        Task task = taskList.getTask(taskNumber);
        task.markDone();
        ui.markTaskMessage(task);
        updateFile();
    }

    public void unmarkTask(int taskNumber) {
        Task task = taskList.getTask(taskNumber);
        task.markUndone();
        ui.markTaskMessage(task);
        updateFile();
    }

    public void listTasks() {
        ui.listTasks(taskList.getTasks());
    }

    public int getTaskCount() {
        return taskList.getTaskCount();
    }

    public void run() {
        // initialize save file, or create one if it doesn't exist
        try {
            new TaskList();
            ui.detectSaveStatus(storage.fileExists());

            if (!storage.fileExists()) {
                // if save file doesn't exist, create it
                taskList = new TaskList();
                boolean created = storage.initializeFile();
                ui.saveCreatedStatus(created);
                if (!created) {
                    throw new IOException("Could not create save file");
                }
            } else {
                // if save file exists, load tasks from it
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

    public static void main(String[] args) {
        new Performative("./data/savefile.txt").run();
    }
}