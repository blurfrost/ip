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

    public static void addTask(String task) {
        tasks[taskCount] = new Task(task);
        taskCount += 1;
        echo(task);
    }

    public static void listTasks() {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
        printLine();
    }

    public static void echo(String input) {
        printLine();
        System.out.println("added: " + input);
        printLine();
    }

    public static void markTask(int taskNumber) {
        printLine();
        Task task = tasks[taskNumber - 1];
        task.markDone();
        System.out.println("Marked this task as done:\n" + task.toString());
        printLine();
    }

    public static void unmarkTask(int taskNumber) {
        printLine();
        Task task = tasks[taskNumber - 1];
        task.markUndone();
        System.out.println("Marked this task as undone:\n" + task.toString());
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
                        System.out.println("Invalid number format, added as task instead");
                        addTask(input);
                    } catch (NullPointerException e) {
                        System.out.println("Invalid number, added as task instead");
                        addTask(input);
                    }
                } else {
                    System.out.println("Invalid format, added as task instead");
                    addTask(input);
                }
            } else {
                addTask(input);
            }
        }
    }
}
