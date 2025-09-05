package performative.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import performative.tasks.Task;
import performative.tasks.Todo;

public class UiTest {
    private Ui ui;

    @BeforeEach
    public void setUp() {
        ui = new Ui();
    }

    @Test
    public void testUnsupportedCommandMessage() {
        // test: unsupported command returns correct error message
        String result = ui.getUnsupportedCommandMessage();

        assertEquals("Erm actually, your keyword's not supported", result,
                    "Should return the correct unsupported command message");
    }

    @Test
    public void testAddTaskMessage() {
        // test: adding a task returns the correct confirmation message
        Task testTask = new Todo("test task");
        int taskCount = 5;

        String result = ui.getAddTaskMessage(testTask, taskCount);

        assertTrue(result.contains("Added: " + testTask.toString()),
                  "Should contain the added task");
        assertTrue(result.contains("There are now " + taskCount + " tasks in the list"),
                  "Should contain the updated task count");
    }

    @Test
    public void testListTasksWithMultipleTasks() {
        // test: listing tasks returns all tasks with correct numbering
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("first task"));
        tasks.add(new Todo("second task"));
        tasks.add(new Todo("third task"));

        String result = ui.getListTasksMessage(tasks);

        assertTrue(result.contains("Here are the tasks in your list:"),
                  "Should contain the list header");
        assertTrue(result.contains("1. " + tasks.get(0).toString()),
                  "Should contain first task with number 1");
        assertTrue(result.contains("2. " + tasks.get(1).toString()),
                  "Should contain second task with number 2");
        assertTrue(result.contains("3. " + tasks.get(2).toString()),
                  "Should contain third task with number 3");
    }

    @Test
    public void testListTasksWithEmptyList() {
        // test: listing empty task list returns appropriate message
        ArrayList<Task> emptyTasks = new ArrayList<>();

        String result = ui.getListTasksMessage(emptyTasks);

        assertEquals("No tasks in your list yet!", result,
                    "Should return empty list message");
    }

    @Test
    public void testSearchResultsWithMatches() {
        // test: search results with matching tasks returns formatted results
        ArrayList<Task> matchingTasks = new ArrayList<>();
        matchingTasks.add(new Todo("book reading"));
        matchingTasks.add(new Todo("buy books"));
        String keyword = "book";

        String result = ui.getSearchResultsMessage(matchingTasks, keyword);

        assertTrue(result.contains("Here are the matching tasks in your list:"),
                  "Should contain search results header");
        assertTrue(result.contains("1. " + matchingTasks.get(0).toString()),
                  "Should contain first matching task");
        assertTrue(result.contains("2. " + matchingTasks.get(1).toString()),
                  "Should contain second matching task");
    }

    @Test
    public void testSearchResultsWithNoMatches() {
        // test: search results with no matching tasks returns no results message
        ArrayList<Task> emptyMatches = new ArrayList<>();
        String keyword = "nonexistent";

        String result = ui.getSearchResultsMessage(emptyMatches, keyword);

        assertEquals("No tasks found containing the keyword: " + keyword, result,
                    "Should return no matches message with keyword");
    }
}
