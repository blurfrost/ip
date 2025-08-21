import java.util.Scanner;

public class Performative {
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    public static void printLine() {
        for (int i = 0; i < 40; i++) {
            System.out.print("─");
        }
        System.out.println();
    }

    public static void addTask(String input) {
        try {
            Task task = parseTask(input);
            tasks[taskCount] = task;
            taskCount += 1;
            printLine();
            System.out.println("Added: " + task);
            System.out.println("There are now " + taskCount + " tasks in the list.");
            printLine();
        } catch (PerformativeException e) {
            printLine();
            System.out.println("Error: " + e.getMessage());
            printLine();
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
                return new Deadline(description, by);
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

    public static void listTasks() {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
        printLine();
    }

    public static void markTask(int taskNumber) {
        printLine();
        Task task = tasks[taskNumber - 1];
        task.markDone();
        System.out.println("Marked this task as done:\n" + task);
        printLine();
    }

    public static void unmarkTask(int taskNumber) {
        printLine();
        Task task = tasks[taskNumber - 1];
        task.markUndone();
        System.out.println("Marked this task as undone:\n" + task);
        printLine();
    }

    public static void endChat() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    public static void main(String[] args) {
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
                    }
                } else {
                    System.out.println("Invalid format");
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


