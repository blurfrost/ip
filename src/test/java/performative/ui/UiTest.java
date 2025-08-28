package performative.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import performative.tasks.Todo;
import performative.tasks.Task;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class UiTest {
    private Ui ui;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        ui = new Ui();
        // capture System.out for testing console output
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        // restore original System.out
        System.setOut(originalOut);
    }

    @Test
    public void testGreetUserMessage() {
        // test: greet user method displays the correct welcome message
        ui.greetUser();

        String output = outputStream.toString();
        assertTrue(output.contains("Hello! I'm Performative."),
                  "Greeting should contain the welcome message");
        assertTrue(output.contains("What can I do for you?"),
                  "Greeting should ask what the user needs");
        assertTrue(output.contains("─"),
                  "Greeting should include a separator line");
    }

    @Test
    public void testAddTaskMessage() {
        // test: adding a task displays the correct confirmation message
        Task testTask = new Todo("test task");
        int taskCount = 5;

        ui.addTaskMessage(testTask, taskCount);

        String output = outputStream.toString();
        assertTrue(output.contains("Added: " + testTask.toString()),
                  "Should display the added task");
        assertTrue(output.contains("There are now " + taskCount + " tasks in the list"),
                  "Should display the updated task count");
        assertTrue(output.contains("─"),
                  "Should include separator line");
    }

    @Test
    public void testListTasksWithMultipleTasks() {
        // test: listing tasks displays all tasks with correct numbering
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("first task"));
        tasks.add(new Todo("second task"));
        tasks.add(new Todo("third task"));

        ui.listTasks(tasks);

        String output = outputStream.toString();
        assertTrue(output.contains("Here are the tasks in your list:"),
                  "Should display the list header");
        assertTrue(output.contains("1. " + tasks.get(0).toString()),
                  "Should display first task with number 1");
        assertTrue(output.contains("2. " + tasks.get(1).toString()),
                  "Should display second task with number 2");
        assertTrue(output.contains("3. " + tasks.get(2).toString()),
                  "Should display third task with number 3");
        assertTrue(output.contains("─"),
                  "Should include separator line");
    }
}
