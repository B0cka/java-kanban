
import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private int id = 1;


    public void printAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        } else {
            System.out.println("Список всех задач (Task):");
            for (Task task : tasks.values()) {
                System.out.println(task);
            }
        }
    }

    // Метод для вывода всех Epic
    public void printAllEpics() {
        if (epics.isEmpty()) {
            System.out.println("Список эпиков пуст.");
        } else {
            System.out.println("Список всех эпиков (Epic):");
            for (Epic epic : epics.values()) {
                System.out.println(epic);
            }
        }
    }

    // Метод для вывода всех Subtask
    public void printAllSubtasks() {
        if (subtasks.isEmpty()) {
            System.out.println("Список подзадач пуст.");
        } else {
            System.out.println("Список всех подзадач (Subtask):");
            for (Subtask subtask : subtasks.values()) {
                System.out.println(subtask);
            }
        }
    }
    public void createTask(Task task) {
        task.setId(id++);
        task.setStatus(TaskStatus.NEW);
        tasks.put(task.getId(), task);
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void getTask(Task task) {
        tasks.get(task.getId());
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }


    //----------------------------------------------------------------//
    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        ArrayList<Subtask> subtasksByEpic = new ArrayList<>();

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                subtasksByEpic.add(subtask);
            }
        }

        return subtasksByEpic;
    }


    public void createSubtask(Subtask subtask) {
        subtask.setId(id++);
        subtask.setStatus(TaskStatus.NEW);
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    public void deleteSubtask(int id) {
        subtasks.remove(id);
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
        }
    }

    public void getSubtask(Subtask subtask) {
        subtasks.get(subtask.getId());
    }

    public void removeSubtask(int id) {
        subtasks.remove(id);
    }

    private void updateEpicStatus(int epicId) {
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




    public void createEpic(Epic epic) {
        epic.setId(id++);
        epic.setStatus(TaskStatus.NEW);
        epics.put(epic.getId(), epic);
    }

    public void deleteEpic(int id) {
        epics.remove(id);
    }

    // Метод для вывода списка всех задач

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void removeEpic(int id) {
        epics.remove(id);
    }

}