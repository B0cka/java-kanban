import java.util.ArrayList;

public interface TaskManager {
    ArrayList<Task> getAllTasks();

    // Получение списка всех эпиков (Epic)
    ArrayList<Epic> getAllEpics();

    // Получение списка всех подзадач (Subtask)
    ArrayList<Subtask> getAllSubtasks();

    void createTask(Task task);

    void deleteTask(int id);

    void updateTask(Task task);

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

    ArrayList<Task> getHistory();

    void addToHistory(Task task);

}
