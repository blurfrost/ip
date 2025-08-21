import java.util.Scanner;

public class Performative {
    private static String[] tasks = new String[100];
    private static int taskCount = 0;

    public static void printLine() {
        for (int i = 0; i < 40; i++) {
            System.out.print("─");
        }
        System.out.println();
    }

    public static void addTask(String task) {
        tasks[taskCount] = (taskCount + 1) + ". " + task;
        taskCount += 1;
    }

    public static void echo(String input) {
        printLine();
        System.out.println("added: " + input);
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
            } else {
                addTask(input);
                echo(input);
            }
        }
    }
}
