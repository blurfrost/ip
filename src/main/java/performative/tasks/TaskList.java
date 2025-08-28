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
        this.tasks = tasks;
        this.taskCount = tasks.size();
    }

    /**
     * Returns the task at the specified task number.
     *
     * @param taskNumber The number of the task to retrieve (1-indexed).
     * @return The Task object at the specified position.
     */
    public Task getTask(int taskNumber) {
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
        this.tasks.add(task);
        this.taskCount += 1;
    }

    /**
     * Removes and returns the task at the specified task number.
     * Decreases the task count by one.
     *
     * @param taskNumber The number of the task to delete (1-indexed).
     * @return The removed Task object.
     */
    public Task deleteTask(int taskNumber) {
        Task removedTask = tasks.remove(taskNumber - 1);
        this.taskCount -= 1;
        return removedTask;
    }
}
