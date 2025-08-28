import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Performative {
    private ArrayList<Task> tasks;
    private int taskCount;
    private Storage storage;
    private Ui ui;

    public Performative(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new ArrayList<>();
        taskCount = 0;
    }

    public void addTask(String input) {
        try {
            // parse input and create task object (according to type)
            Task task = Parser.parseTask(input);
            tasks.add(task);
            taskCount += 1;

            // output to terminal
            ui.addTaskMessage(task, taskCount);

            // save task to file
            storage.saveTask(task);
        } catch (PerformativeException e) {
            ui.exceptionMessage(e.getMessage());
        } catch (IOException e) {
            ui.exceptionMessage("Error writing to save file");
        }
    }

    public void deleteTask(int taskNumber) {
        Task removedTask = tasks.remove(taskNumber - 1);
        taskCount -= 1;
        ui.deleteTaskMessage(removedTask, taskNumber, taskCount);
        updateFile();
    }

    // rewrite the entire save file with the current list of tasks
    public void updateFile() {
        try {
            storage.saveTasks(tasks);
        } catch (IOException e) {
            ui.exceptionMessage("Error writing to save file");
        }
    }

    public void markTask(int taskNumber) {
        Task task = tasks.get(taskNumber - 1);
        task.markDone();
        ui.markTaskMessage(task);
        updateFile();
    }

    public void unmarkTask(int taskNumber) {
        Task task = tasks.get(taskNumber - 1);
        task.markUndone();
        ui.markTaskMessage(task);
        updateFile();
    }

    public void listTasks() {
        ui.listTasks(tasks);
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void initializeSave() throws IOException {
        ui.detectSaveStatus(storage.fileExists());

        if (!storage.fileExists()) {
            // if save file doesn't exist, create it
            boolean created = storage.initializeFile();
            ui.saveCreatedStatus(created);
            if (!created) {
                throw new IOException("Could not create save file");
            }
        } else {
            // if save file exists, load tasks from it
            tasks = storage.loadTasks();
            taskCount = tasks.size();

            // Check for unknown task types during loading
            for (Task task : tasks) {
                if (task == null) {
                    ui.unknownTaskFound("Unknown");
                }
            }

            ui.loadTasksStatus(taskCount);
            ui.listTasks(tasks);
        }
        ui.completeInitMessage();
    }
    
    public void run() {
        // initialize save file, or create one if it doesn't exist
        try {
            initializeSave();
        } catch (IOException e) {
            ui.cannotInitializeSaveFile();
        }

        ui.greetUser();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            // Use Parser to handle command parsing and execution
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
        new Performative("../../../data/savefile.txt").run();
    }
}