import java.util.Scanner;

public class Performative {
    private static Task[] tasks = new Task[100];

    public static void printLine() {
        for (int i = 0; i < 40; i++) {
            System.out.print("─");
        }
        System.out.println();
    }

    public static void addTask(String task) {
        tasks[Task.getCurrentTasks()] = new Task(task);
        echo(task);
    }

    public static void listTasks() {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < Task.getCurrentTasks(); i++) {
            System.out.println(tasks[i]);
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
        System.out.println("Marked task " + taskNumber + " as done: " + task.markDone());
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
            } else if (input.startsWith("mark ")) {
                String[] parts = input.split(" ");
                if (parts.length == 2) {
                    try {
                        int taskNumber = Integer.parseInt(parts[1]);
                        markTask(taskNumber);
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
            }
            else {
                addTask(input);
            }
        }
    }
}
