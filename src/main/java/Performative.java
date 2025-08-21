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
        Task task = parseTask(input);
        tasks[taskCount] = task;
        taskCount += 1;
        printLine();
        System.out.println("added: " + task.toString());
        printLine();
    }

    public static Task parseTask(String input) {
        if (input.startsWith("todo ")) {
            // make a Todo task after the todo keyword
            String description = input.substring(5);
            return new Todo(description);
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
            } else if (input.startsWith("deadline ")
                    || input.startsWith("event ")
                    || input.startsWith("todo ")) {
                addTask(input);
            } else {
                printLine();
            }
        }
    }
}
