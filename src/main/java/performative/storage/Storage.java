package performative.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import performative.tasks.Deadline;
import performative.tasks.Event;
import performative.tasks.Task;
import performative.tasks.Todo;

/**
 * Handles file storage operations for the Performative application.
 * Manages loading and saving tasks to and from the file system.
 */
public class Storage {
    private File saveFile;

    // Named constants to replace magic numbers
    private static final int MINIMUM_TASK_PARTS = 3;
    private static final int TYPE_INDEX = 0;
    private static final int STATUS_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    private static final int DEADLINE_TIME_INDEX = 3;
    private static final int EVENT_START_TIME_INDEX = 3;
    private static final int EVENT_END_TIME_INDEX = 4;
    private static final int MINIMUM_DEADLINE_PARTS = 4;
    private static final int MINIMUM_EVENT_PARTS = 5;

    /**
     * Creates a new Storage instance with the specified file path.
     *
     * @param filePath Path to the file where tasks will be stored.
     */
    public Storage(String filePath) {
        this.saveFile = new File(filePath);
    }

    /**
     * Initializes the save file by creating it if it doesn't exist.
     * Creates any necessary parent directories.
     *
     * @return True if the file was successfully created or already exists, false otherwise.
     */
    public boolean initializeFile() {
        try {
            // Create parent directories if they don't exist
            File parentDir = saveFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (!saveFile.exists()) {
                return saveFile.createNewFile();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Checks whether the save file exists.
     *
     * @return True if the save file exists, false otherwise.
     */
    public boolean fileExists() {
        return saveFile.exists();
    }

    /**
     * Loads all tasks from the save file.
     * Parses the file content and creates appropriate task objects.
     *
     * @return ArrayList of Task objects loaded from the file.
     * @throws IOException If an error occurs while reading the file.
     */
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!saveFile.exists()) {
            return tasks;
        }

        Scanner fileScanner = new Scanner(saveFile);
        while (fileScanner.hasNextLine()) {
            String data = fileScanner.nextLine();
            String[] parts = data.split("; ");

            if (parts.length < MINIMUM_TASK_PARTS) {
                continue;
            }

            String type = parts[TYPE_INDEX];
            String status = parts[STATUS_INDEX];
            String description = parts[DESCRIPTION_INDEX];

            Task task = createTaskFromData(type, description, parts);

            if (task != null) {
                // Mark task as done if it was indicated as completed in the save file
                if (status.equals("Complete")) {
                    task.markDone();
                }
                tasks.add(task);
            }
        }
        fileScanner.close();
        return tasks;
    }

    private Task createTaskFromData(String type, String description, String[] parts) {
        try {
            switch (type) {
            case "Task":
                return new Task(description);
            case "Todo":
                return new Todo(description);
            case "Deadline":
                if (parts.length >= MINIMUM_DEADLINE_PARTS) {
                    return new Deadline(description, parts[DEADLINE_TIME_INDEX]);
                }
                break;
            case "Event":
                if (parts.length >= MINIMUM_EVENT_PARTS) {
                    return new Event(description, parts[EVENT_START_TIME_INDEX], parts[EVENT_END_TIME_INDEX]);
                }
                break;
            default:
                break;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * Saves a single task to the save file by appending it.
     *
     * @param task The task to be saved to the file.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveTask(Task task) throws IOException {
        FileWriter writer = new FileWriter(saveFile, true);
        writer.write(task.toSaveFormat() + "\n");
        writer.close();
    }

    /**
     * Saves all tasks to the save file by overwriting the existing content.
     * Rewrites the entire file with the provided list of tasks.
     *
     * @param tasks ArrayList of tasks to be saved to the file.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(saveFile, false);
        for (Task task : tasks) {
            writer.write(task.toSaveFormat() + "\n");
        }
        writer.close();
    }
}
