package managers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import tasks.Task;
import tasks.Epic;
import tasks.Subtask;
import tasks.TaskStatus;
import tasks.*;

import java.io.*;
import java.nio.file.Files;

public class FileBackedTaskManager extends InMemoryTaskManager {


    public void save()  {

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
            throw new RuntimeException("Ошибка сохранения данных в файл.", e);
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

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeSubtask(int id) {
        super.removeSubtask(id);
        save();
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager();

        try {
            // Чтение всего содержимого файла как одной строки
            String content = Files.readString(file.toPath());

            // Разделяем содержимое по строкам
            String[] lines = content.split("\n");

            for (int i = 1; i < lines.length; i++) { // Пропускаем заголовок
                String line = lines[i].trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String type = parts[1];
                    String title = parts[2];
                    TaskStatus status = TaskStatus.valueOf(parts[3]);
                    String description = parts[4];
                    int epicId = parts.length > 5 ? Integer.parseInt(parts[5]) : -1;

                    switch (type) {
                        case "TASK":
                            Task task = new Task(title, status, description);
                            manager.tasks.put(id, task);
                            break;
                        case "EPIC":
                            Epic epic = new Epic(title, description, status);
                            manager.epics.put(id, epic);
                            break;
                        case "SUBTASK":
                            Subtask subtask = new Subtask(title, status, description, epicId);
                            manager.subtasks.put(id, subtask);
                            if (manager.epics.containsKey(epicId)) {
                                manager.epics.get(epicId).addSubtask(id);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки данных из файла", e);
        }

        return manager;
    }
}