
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private int id = 1;

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    // Получение списка всех эпиков (Epic)
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    // Получение списка всех подзадач (Subtask)
    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }
    @Override
    public void createTask(Task task) {
        task.setId(id++);
        task.setStatus(TaskStatus.NEW);
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void getTask(Task task) {
        tasks.get(task.getId());
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }


    //----------------------------------------------------------------//
    @Override
    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        ArrayList<Subtask> subtasksByEpic = new ArrayList<>();

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                subtasksByEpic.add(subtask);
            }
        }

        return subtasksByEpic;
    }


    @Override
    public void createSubtask(Subtask subtask) {
        subtask.setId(id++);
        subtask.setStatus(TaskStatus.NEW);
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    @Override
    public void deleteSubtask(int id) {
        subtasks.remove(id);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
        }
    }

    @Override
    public void getSubtask(Subtask subtask) {
        subtasks.get(subtask.getId());
    }

    @Override
    public void removeSubtask(int id) {
        subtasks.remove(id);
    }

    @Override
    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return; // Эпик не найден

        ArrayList<Subtask> subtasksByEpic = getSubtasksByEpicId(epicId);

        // Проверка статусов подзадач
        boolean hasNew = false;
        boolean hasInProgress = false;
        boolean allDone = true;

        for (Subtask subtask : subtasksByEpic) {
            switch (subtask.getStatus()) {
                case NEW:
                    hasNew = true;
                    allDone = false;
                    break;
                case IN_PROGRESS:
                    hasInProgress = true;
                    allDone = false;
                    break;
                case DONE:
                    break;
            }
        }

        // Установка нового статуса эпика
        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (hasInProgress) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        } else if (hasNew) {
            epic.setStatus(TaskStatus.NEW);
        }
    }
    //------------------------------------------------------------------//

    @Override
    public void createEpic(Epic epic) {
        epic.setId(id++);
        epic.setStatus(TaskStatus.NEW);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void deleteEpic(int id) {
        epics.remove(id);
    }

    // Метод для вывода списка всех задач

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    @Override
    public void removeEpic(int id) {
        epics.remove(id);
    }

}