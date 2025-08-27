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

    public static void printLine() {
        for (int i = 0; i < 40; i++) {
            System.out.print("─");
        }
        System.out.println();
    }

    public static void addTask(String input) {
        try {
            // parse input and create task object (according to type)
            Task task = parseTask(input);
            tasks.add(task);
            taskCount += 1;

            // output to terminal
            printLine();
            System.out.println("Added: " + task);
            System.out.println("There are now " + taskCount + " tasks in the list.");
            printLine();

            // write task to save file
            FileWriter writer = new FileWriter(saveFile, true);
            writer.write(task.toSaveFormat() + "\n");
            writer.close();

        } catch (PerformativeException e) {
            printLine();
            System.out.println("Error: " + e.getMessage());
            printLine();
        } catch (IOException e) {
            System.out.println("Error writing to save file");
        }
    }


    public static Task parseTask(String input) throws PerformativeException {
        if (input.startsWith("todo")) {
            if (input.equals("todo")) {
                throw new PerformativeException("The description of a todo cannot be empty.");
            }
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new PerformativeException("The description of a todo cannot be empty.");
            }
            return new Todo(description);
        } else if (input.startsWith("deadline")) {
            if (input.equals("deadline")) {
                throw new PerformativeException("The description of a deadline cannot be empty.");
            }
            String remaining = input.substring(9).trim();
            if (remaining.isEmpty()) {
                throw new PerformativeException("The description of a deadline cannot be empty.");
            }
            int byIndex = remaining.indexOf(" /by ");
            if (byIndex != -1) {

                String description = remaining.substring(0, byIndex).trim();
                String by = remaining.substring(byIndex + 5).trim();
                if (description.isEmpty()) {
                    throw new PerformativeException("The description of a deadline cannot be empty.");
                }
                if (by.isEmpty()) {
                    throw new PerformativeException("The deadline time cannot be empty.");
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
                throw new PerformativeException("The description of an event cannot be empty.");
            }
            String remaining = input.substring(6).trim();
            if (remaining.isEmpty()) {
                throw new PerformativeException("The description of an event cannot be empty.");
            }
            int fromIndex = remaining.indexOf(" /from ");
            int toIndex = remaining.indexOf(" /to ");
            if (fromIndex != -1 && toIndex != -1 && toIndex > fromIndex) {
                String description = remaining.substring(0, fromIndex).trim();
                String from = remaining.substring(fromIndex + 7, toIndex).trim();
                String to = remaining.substring(toIndex + 5).trim();
                if (description.isEmpty()) {
                    throw new PerformativeException("The description of an event cannot be empty.");
                }
                if (from.isEmpty()) {
                    throw new PerformativeException("The start time of an event cannot be empty.");
                }
                if (to.isEmpty()) {
                    throw new PerformativeException("The end time of an event cannot be empty.");
                }
                return new Event(description, from, to);
            } else {
                throw new PerformativeException("Event format should be: event <description> /from <start> /to <end>");
            }
        }
        return new Task(input);
    }

    public static void deleteTask(int taskNumber) {
        printLine();
        Task removedTask = tasks.remove(taskNumber - 1);
        taskCount -= 1;
        System.out.println("Deleted task " + taskNumber + ": " + removedTask);
        System.out.println("There are now " + taskCount + " tasks in the list.");
        printLine();
        updateFile();
    }

    public static void listTasks() {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        printLine();
    }

    // rewrite the entire save file with the current list of tasks
    public static void updateFile() {
        try {
            FileWriter writer = new FileWriter(saveFile, false); // false = overwrite mode
            for (Task task : tasks) {
                writer.write(task.toSaveFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to save file");
        }
    }

    public static void markTask(int taskNumber) {
        printLine();
        Task task = tasks.get(taskNumber - 1);
        task.markDone();
        System.out.println("Marked this task as done:\n" + task);
        printLine();
        updateFile();
    }

    public static void unmarkTask(int taskNumber) {
        printLine();
        Task task = tasks.get(taskNumber - 1);
        task.markUndone();
        System.out.println("Marked this task as undone:\n" + task);
        printLine();
        updateFile();
    }

    public static void endChat() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    public static void initializeSave() throws IOException {
        saveFile = new File("../../../data/savefile.txt");
        if (!saveFile.exists()) {
            // if save file doesn't exist, create it
            System.out.println("Save not found, creating new save");
            saveFile.createNewFile();
            System.out.println(saveFile.exists() ? "Save created" : "Save not created");
        } else {
            // if save file exists, load tasks from it
            System.out.println("Save found");
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
                        System.out.println("Unknown task type in save file: " + type);
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
            if (taskCount > 0) {
                System.out.println("Loading tasks from save:");
                listTasks();
            } else {
                System.out.println("No tasks in save");
            }

        }
        System.out.println("Initialization complete");
    }

    public static void main(String[] args) {
        // initialize save file, or create one if it doesn't exist
        try {
            initializeSave();
        } catch (IOException e) {
            System.out.println("Error initializing save file");
        }

        printLine();
        System.out.println("Hello! I'm Performative.\nWhat can I do for you?");
        printLine();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                scanner.close();
                endChat();
                break;
            } else if (input.equals("list")) {
                listTasks();
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
                        System.out.println("Invalid number format");
                    } catch (NullPointerException e) {
                        System.out.println("Invalid number");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid task number. Input a task from 1 to " + taskCount);
                    }
                } else {
                    System.out.println("Invalid format");
                }
            // TODO: deleting should also update the save file
            } else if (input.startsWith("delete")) {
                String[] parts = input.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        deleteTask(taskNumber);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid task number. Input a task from 1 to " + taskCount);
                    }
                } else {
                    System.out.println("Invalid format. Use: delete <task number>");
                }
            } else if (input.startsWith("deadline")
                    || input.startsWith("event")
                    || input.startsWith("todo")) {
                addTask(input);
            } else {
                System.out.println("Erm actually, your keyword's not supported");
                printLine();
            }
        }
    }
}
