package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public interface TaskManager {

    ArrayList<Task> getAllTasks();

    void createTask(Task task);

    Task getTask(int id);

    void updateTask(Task task);

    void removeTask(int id);


    ArrayList<Epic> getAllEpics();

    void createEpic(Epic epic);

    Epic getEpicById(int id);

    void updateEpic(Epic epic);

    void removeEpic(int id);


    ArrayList<Subtask> getAllSubtasks();

    void createSubtask(Subtask subtask);

    Subtask getSubtask(int id);

    void updateSubtask(Subtask subtask);

    void removeSubtask(int id);


    ArrayList<Subtask> getSubtasksByEpicId(int epicId);


    ArrayList<Task> getHistory();
}