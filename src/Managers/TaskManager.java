package Managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public interface TaskManager {
    ArrayList<tasks.Task> getAllTasks();

    // Получение списка всех эпиков (Tasks.Epic)
    ArrayList<Epic> getAllEpics();

    // Получение списка всех подзадач (Tasks.Subtask)
    ArrayList<Subtask> getAllSubtasks();

    void createTask(tasks.Task task);

    void deleteTask(int id);

    void updateTask(tasks.Task task);

    Task getTask(int id);

    void removeTask(int id);

    //----------------------------------------------------------------//
    ArrayList<Subtask> getSubtasksByEpicId(int epicId);

    void createSubtask(Subtask subtask);

    void deleteSubtask(int id);

    void updateSubtask(Subtask subtask);

    Subtask getSubtask(int id);

    void removeSubtask(int id);

    void updateEpicStatus(int epicId);

    void createEpic(Epic epic);

    void deleteEpic(int id);

    void updateEpic(Epic epic);

    Epic getEpicById(int id);

    void removeEpic(int id);

    ArrayList<tasks.Task> getHistory();

    void addToHistory(Task task);

}
