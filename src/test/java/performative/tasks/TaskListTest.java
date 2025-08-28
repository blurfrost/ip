package performative.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class TaskListTest {
    private TaskList taskList;
    private Task sampleTask1;
    private Task sampleTask2;
    private Task sampleTask3;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        sampleTask1 = new Todo("buy groceries");
        sampleTask2 = new Todo("read book");
        sampleTask3 = new Todo("exercise");
    }

    @Test
    public void testAddTaskAndTaskCount() {
        // test: adding tasks correctly increases the task count
        assertEquals(0, taskList.getTaskCount(), "Initial task count should be 0");

        taskList.addTask(sampleTask1);
        assertEquals(1, taskList.getTaskCount(), "Task count should be 1 after adding one task");

        taskList.addTask(sampleTask2);
        assertEquals(2, taskList.getTaskCount(), "Task count should be 2 after adding two tasks");

        // verify tasks are actually in the list
        assertEquals(sampleTask1, taskList.getTask(1), "First task should match the added task");
        assertEquals(sampleTask2, taskList.getTask(2), "Second task should match the added task");
    }

    @Test
    public void testDeleteTask() {
        // test: deleting tasks works correctly and updates task count
        taskList.addTask(sampleTask1);
        taskList.addTask(sampleTask2);
        taskList.addTask(sampleTask3);
        assertEquals(3, taskList.getTaskCount(), "Should have 3 tasks initially");

        // delete task 2
        Task deletedTask = taskList.deleteTask(2);
        assertEquals(sampleTask2, deletedTask, "Deleted task should be the second task");
        assertEquals(2, taskList.getTaskCount(), "Task count should be 2 after deletion");

        // verify remaining tasks are correct
        assertEquals(sampleTask1, taskList.getTask(1), "First task should still be in position 1");
        assertEquals(sampleTask3, taskList.getTask(2), "Third task should now be in position 2");
    }

    @Test
    public void testConstructorWithExistingTasks() {
        // test: TaskList can be initialized with an existing ArrayList of tasks
        ArrayList<Task> existingTasks = new ArrayList<>();
        existingTasks.add(sampleTask1);
        existingTasks.add(sampleTask2);

        TaskList taskListWithExistingTasks = new TaskList(existingTasks);

        assertEquals(2, taskListWithExistingTasks.getTaskCount(),
                    "Task count should match the size of the existing tasks list");
        assertEquals(sampleTask1, taskListWithExistingTasks.getTask(1),
                    "First task should match the first task in existing list");
        assertEquals(sampleTask2, taskListWithExistingTasks.getTask(2),
                    "Second task should match the second task in existing list");

        // verify getTasks() returns the correct list
        ArrayList<Task> retrievedTasks = taskListWithExistingTasks.getTasks();
        assertEquals(existingTasks, retrievedTasks,
                    "Retrieved tasks should match the original existing tasks");
    }
}
