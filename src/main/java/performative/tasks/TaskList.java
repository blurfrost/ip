package performative.tasks;

import java.util.ArrayList;

/**
 * Manages a collection of tasks for the Performative application.
 * Provides operations to add, delete, retrieve, and manage tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private int taskCount;

    /**
     * Constructs a new empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        this.taskCount = 0;
    }

    /**
     * Constructs a new TaskList with the provided list of tasks.
     *
     * @param tasks ArrayList of existing tasks to initialize the TaskList with.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Task list cannot be null";
        this.tasks = tasks;
        this.taskCount = tasks.size();
        assert this.taskCount >= 0 : "Task count must be non-negative";
    }

    /**
     * Returns the task at the specified task number.
     *
     * @param taskNumber The number of the task to retrieve (1-indexed).
     * @return The Task object at the specified position.
     */
    public Task getTask(int taskNumber) {
        assert taskNumber >= 1 && taskNumber <= taskCount : "Task number must be between 1 and " + taskCount;
        assert tasks.size() == taskCount : "Internal state inconsistent: tasks.size() != taskCount";
        return this.tasks.get(taskNumber - 1);
    }

    /**
     * Returns the current number of tasks in the list.
     *
     * @return The total count of tasks.
     */
    public int getTaskCount() {
        return this.taskCount;
    }

    /**
     * Returns the ArrayList containing all tasks.
     *
     * @return ArrayList of all tasks.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Adds a new task to the list.
     * Increases the task count by one.
     *
     * @param task The task to be added to the list.
     */
    public void addTask(Task task) {
        assert task != null : "Cannot add null task";
        int oldCount = this.taskCount;
        this.tasks.add(task);
        this.taskCount += 1;
        assert this.taskCount == oldCount + 1 : "Task count should increase by exactly 1";
        assert tasks.size() == taskCount : "Internal state inconsistent after adding task";
    }

    /**
     * Removes and returns the task at the specified task number.
     * Decreases the task count by one.
     *
     * @param taskNumber The number of the task to delete (1-indexed).
     * @return The removed Task object.
     */
    public Task deleteTask(int taskNumber) {
        assert taskNumber >= 1 && taskNumber <= taskCount : "Task number must be between 1 and " + taskCount;
        assert taskCount > 0 : "Cannot delete from empty task list";
        int oldCount = this.taskCount;
        Task removedTask = tasks.remove(taskNumber - 1);
        this.taskCount -= 1;
        assert this.taskCount == oldCount - 1 : "Task count should decrease by exactly 1";
        assert tasks.size() == taskCount : "Internal state inconsistent after deleting task";
        assert removedTask != null : "Removed task should not be null";
        return removedTask;
    }
}
