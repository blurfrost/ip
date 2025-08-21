public class Performative {
    public static void printLine() {
        for (int i = 0; i < 40; i++) {
            System.out.print("─");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        printLine();
        System.out.println("Hello! I'm Performative.\nWhat can I do for you?");
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();

    }
}
