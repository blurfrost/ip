import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Performative {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static int taskCount = 0;
    private static File saveFile;
    private Ui ui;
    
    public Performative() {
        ui = new Ui();
    }

    public void addTask(String input) {
        try {
            // parse input and create task object (according to type)
            Task task = parseTask(input);
            tasks.add(task);
            taskCount += 1;

            // output to terminal
            ui.addTaskMessage(task, taskCount);

            // write task to save file
            FileWriter writer = new FileWriter(saveFile, true);
            writer.write(task.toSaveFormat() + "\n");
            writer.close();
        } catch (PerformativeException e) {
            ui.exceptionMessage(e.getMessage());
        } catch (IOException e) {
            ui.exceptionMessage("Error writing to save file");
        }
    }


    public Task parseTask(String input) throws PerformativeException {
        if (input.startsWith("todo")) {
            if (input.equals("todo")) {
                throw new PerformativeException("The description of a todo cannot be empty");
            }
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new PerformativeException("The description of a todo cannot be empty");
            }
            return new Todo(description);
        } else if (input.startsWith("deadline")) {
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
                throw new PerformativeException("Deadline format should be: deadline <description> /by <time>");
            }
        } else if (input.startsWith("event")) {
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
        return new Task(input);
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
            FileWriter writer = new FileWriter(saveFile, false); // false = overwrite mode
            for (Task task : tasks) {
                writer.write(task.toSaveFormat() + "\n");
            }
            writer.close();
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

    public void initializeSave() throws IOException {
        saveFile = new File("../../../data/savefile.txt");
        ui.detectSaveStatus(saveFile.exists());
        if (!saveFile.exists()) {
            // if save file doesn't exist, create it
            saveFile.createNewFile();
            ui.saveCreatedStatus(saveFile.exists());
        } else {
            // if save file exists, load tasks from it
            Scanner fileScanner = new Scanner(saveFile);
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                String[] parts = data.split("; ");
                String type = parts[0];
                Task task;
                // create task object according to type
                switch (type) {
                    case "Task":
                        task = new Task(parts[2]);
                        break;
                    case "Todo":
                        task = new Todo(parts[2]);
                        break;
                    case "Deadline":
                        task = new Deadline(parts[2], parts[3]);
                        break;
                    case "Event":
                        task = new Event(parts[2], parts[3], parts[4]);
                        break;
                    default:
                        ui.unknownTaskFound(type);
                        continue;
                }
                // mark task as done if it was indicated as completed in the save file
                if (parts[1].equals("Complete")) {
                    task.markDone();
                }
                // add task to the list of tasks
                tasks.add(task);
                taskCount += 1;
            }
            fileScanner.close();

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
            if (input.equals("bye")) {
                scanner.close();
                ui.endChat();
                break;
            } else if (input.equals("list")) {
                ui.listTasks(tasks);
                // check for the "mark X" command
            } else if (input.startsWith("mark ") || input.startsWith("unmark ")) {
                String[] parts = input.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        if (input.startsWith("mark ")) {
                            markTask(taskNumber);
                        } else {
                            unmarkTask(taskNumber);
                        }
                    } catch (NumberFormatException e) {
                        ui.invalidNumberFormat();
                    } catch (IndexOutOfBoundsException e) {
                        ui.invalidTaskNumber(taskCount);
                    }
                } else {
                    ui.invalidMarkCommand();
                }
            } else if (input.startsWith("delete")) {
                String[] parts = input.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        deleteTask(taskNumber);
                    } catch (NumberFormatException e) {
                        ui.invalidNumberFormat();
                    } catch (IndexOutOfBoundsException e) {
                        ui.invalidTaskNumber(taskCount);
                    }
                } else {
                    ui.invalidDeleteCommand();
                }
            } else if (input.startsWith("deadline")
                    || input.startsWith("event")
                    || input.startsWith("todo")) {
                addTask(input);
            } else {
                ui.unsupportedCommand();
            }
            ui.printLine();
        }
    }
    

    public static void main(String[] args) {
        new Performative().run();
    }
}
