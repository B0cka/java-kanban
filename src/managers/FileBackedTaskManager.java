package managers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import tasks.Task;
import tasks.Epic;
import tasks.Subtask;

public class FileBackedTaskManager extends InMemoryTaskManager {


    public void save() throws ManagerSaveException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {

            writer.write("id,type,name,status,description,epicId");
            writer.newLine();


            for (Task task : tasks.values()) {
                writer.write(task.toString());
                writer.newLine();
            }


            for (Epic epic : epics.values()) {
                writer.write(epic.toString());
                writer.newLine();
            }


            for (Subtask subtask : subtasks.values()) {
                writer.write(subtask.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения данных в файл.", e);
        }
    }

    private String toString(Task task) {

        String epicId = "";
        if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            epicId = String.valueOf(subtask.getEpicId());
        }

        return task.getId() + "," +
                task.getClass().getSimpleName().toUpperCase() + "," +
                task.getTitle() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                epicId;
    }
}
