package performative.ui;

import performative.Task;

import java.util.ArrayList;

public class Ui {
    public Ui() {
    }

    public void printLine() {
        for (int i = 0; i < 40; i++) {
            System.out.print("─");
        }
        System.out.println();
    }

    public void cannotInitializeSaveFile() {
        System.out.println("Error initializing save file");
    }

    public void greetUser() {
        System.out.println("Hello! I'm Performative.\nWhat can I do for you?");
        printLine();
    }

    public void endChat() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    public void invalidNumberFormat() {
        System.out.println("Invalid number format");
    }

    public void invalidTaskNumber(int taskNumber) {
        System.out.println("Invalid task number. Input a task from 1 to " + taskNumber);
    }

    public void invalidMarkCommand() {
        System.out.println("Invalid format for mark/unmark command. Correct format: mark/unmark <task number>");
    }

    public void invalidDeleteCommand() {
        System.out.println("Invalid format for delete command. Correct format: delete <task number>");
    }

    public void unsupportedCommand() {
        System.out.println("Erm actually, your keyword's not supported");
    }

    public void detectSaveStatus(boolean doesExist) {
        if (doesExist) {
            System.out.println("Save found, loading save");
        } else {
            System.out.println("No save found, creating new save");
        }
    }

    public void saveCreatedStatus(boolean isCreated) {
        if (isCreated) {
            System.out.println("Save created successfully");
        } else {
            System.out.println("Error creating save");
        }
    }

    public void unknownTaskFound(String type) {
        System.out.println("Unknown task type found in save: " + type);
    }

    public void loadTasksStatus(int taskCount) {
        if (taskCount > 0) {
            System.out.println("Loading " + taskCount + " tasks from save");
        } else {
            System.out.println("No tasks in save");
        }
    }

    public void completeInitMessage() {
        printLine();
        System.out.println("Initialization complete");
        printLine();
    }

    public void markTaskMessage(Task task) {
        printLine();
        System.out.println("Marked this task as " + (task.getStatus() ? "done:" : "undone:") + "\n" + task);
    }

    public void listTasks(ArrayList<Task> tasks) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void addTaskMessage(Task task, int taskCount) {
        printLine();
        System.out.println("Added: " + task);
        System.out.println("There are now " + taskCount + " tasks in the list");
    }

    public void deleteTaskMessage(Task task, int taskNumber, int taskCount) {
        printLine();
        System.out.println("Deleted task " + taskNumber + ": " + task);
        System.out.println("There are now " + taskCount + " tasks in the list");
    }

    public void exceptionMessage(String message) {
        printLine();
        System.out.println(message);
    }
}

