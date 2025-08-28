package performative;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private File saveFile;

    public Storage(String filePath) {
        this.saveFile = new File(filePath);
    }


    public boolean initializeFile() {
        try {
            if (!saveFile.exists()) {
                return saveFile.createNewFile();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean fileExists() {
        return saveFile.exists();
    }

    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!saveFile.exists()) {
            return tasks;
        }

        Scanner fileScanner = new Scanner(saveFile);
        while (fileScanner.hasNextLine()) {
            String data = fileScanner.nextLine();
            String[] parts = data.split("; ");

            if (parts.length < 3) {
                continue;
            }

            String type = parts[0];
            String status = parts[1];
            String description = parts[2];

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
                case "performative.Task":
                    return new Task(description);
                case "performative.Todo":
                    return new Todo(description);
                case "performative.Deadline":
                    if (parts.length >= 4) {
                        return new Deadline(description, parts[3]);
                    }
                    break;
                case "performative.Event":
                    if (parts.length >= 5) {
                        return new Event(description, parts[3], parts[4]);
                    }
                    break;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void saveTask(Task task) throws IOException {
        FileWriter writer = new FileWriter(saveFile, true);
        writer.write(task.toSaveFormat() + "\n");
        writer.close();
    }

    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(saveFile, false);
        for (Task task : tasks) {
            writer.write(task.toSaveFormat() + "\n");
        }
        writer.close();
    }
}

