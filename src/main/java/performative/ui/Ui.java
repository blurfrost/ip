package performative.ui;

import java.util.ArrayList;

import performative.tasks.Task;

/**
 * Handles user interface operations for the Performative application.
 * Manages all user interaction messages for GUI display.
 */
public class Ui {
    /**
     * Constructs a new Ui instance.
     */
    public Ui() {
    }

    /**
     * Returns an error message for unsupported commands.
     *
     * @return Error message string.
     */
    public String getUnsupportedCommandMessage() {
        return "Erm actually, your keyword's not supported";
    }

    /**
     * Returns a confirmation message when a task is added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks after addition.
     * @return Confirmation message string.
     */
    public String getAddTaskMessage(Task task, int taskCount) {
        return "Added: " + task + "\nThere are now " + taskCount + " tasks in the list";
    }

    /**
     * Returns a confirmation message when a task is marked or unmarked.
     *
     * @param task The task that was marked or unmarked.
     * @return Confirmation message string.
     */
    public String getMarkTaskMessage(Task task) {
        return "Marked this task as " + (task.getStatus() ? "done:" : "undone:") + "\n" + task;
    }

    /**
     * Returns a confirmation message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param taskNumber The number of the deleted task.
     * @param taskCount The total number of tasks after deletion.
     * @return Confirmation message string.
     */
    public String getDeleteTaskMessage(Task task, int taskNumber, int taskCount) {
        return "Deleted task " + taskNumber + ": " + task + "\nThere are now " + taskCount + " tasks in the list";
    }

    /**
     * Returns all tasks in the provided list with numbering.
     *
     * @param tasks ArrayList of tasks to be displayed.
     * @return Formatted task list string.
     */
    public String getListTasksMessage(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "No tasks in your list yet!";
        }

        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i));
            if (i < tasks.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Returns the search results for a find operation.
     *
     * @param matchingTasks ArrayList of tasks that contain the search keyword.
     * @param keyword The keyword that was searched for.
     * @return Search results message string.
     */
    public String getSearchResultsMessage(ArrayList<Task> matchingTasks, String keyword) {
        if (matchingTasks.isEmpty()) {
            return "No tasks found containing the keyword: " + keyword;
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append((i + 1)).append(". ").append(matchingTasks.get(i));
                if (i < matchingTasks.size() - 1) {
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
    }

    /**
     * Returns an error message for invalid number format input.
     *
     * @return Error message string.
     */
    public String getInvalidNumberFormatMessage() {
        return "Invalid number format";
    }

    /**
     * Returns an error message for invalid task number with the valid range.
     *
     * @param taskCount The maximum valid task number.
     * @return Error message string.
     */
    public String getInvalidTaskNumberMessage(int taskCount) {
        return "Invalid task number. Input a task from 1 to " + taskCount;
    }

    /**
     * Returns an error message for invalid mark/unmark command format.
     *
     * @return Error message string.
     */
    public String getInvalidMarkCommandMessage() {
        return "Invalid format for mark/unmark command. Correct format: mark/unmark <task number>";
    }

    /**
     * Returns an error message for invalid delete command format.
     *
     * @return Error message string.
     */
    public String getInvalidDeleteCommandMessage() {
        return "Invalid format for delete command. Correct format: delete <task number>";
    }

    /**
     * Returns an error message when no keyword is provided for the find command.
     *
     * @return Error message string.
     */
    public String getEmptyFindKeywordMessage() {
        return "Please provide a keyword to search for. Correct format: find <keyword>";
    }

    /**
     * Returns a goodbye message when the user exits the application.
     *
     * @return Goodbye message string.
     */
    public String getByeMessage() {
        return "Bye. Hope to see you again soon!";
    }
}
