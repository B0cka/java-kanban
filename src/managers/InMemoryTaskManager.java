package managers;

import tasks.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final ArrayList<Task> history = new ArrayList<>(10);
    private int id = 1;

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void createTask(Task task) {
        task.setId(id++);
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(id++);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        if (subtask == null) {
            throw new IllegalArgumentException("Ошибка: подзадача не может быть null");
        }
        subtask.setId(id++);

        Epic epic = epics.get(subtask.getEpicId());

        if (epic == null) {
            throw new IllegalArgumentException("Ошибка: эпик с ID " + subtask.getEpicId() + " не найден");
        }        subtasks.put(subtask.getId(), subtask);

        epics.get(subtask.getEpicId()).addSubtask(subtask.getId());
        updateEpicStatus(subtask.getEpicId());
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            addToHistory(task);
            System.out.println(getHistory());
        }
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            addToHistory(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            addToHistory(subtask);
        }
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            updateEpicStatus(subtask.getEpicId());
        }
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    @Override
    public void removeSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(id);
                updateEpicStatus(subtask.getEpicId());
            }
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }

    public void addToHistory(Task task) {
        history.add(task);
        if (history.size() > 10) {
            history.remove(0);
        }
        System.out.println("Added to history: " + task);
        System.out.println("Current history: " + history);
    }



    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;

        ArrayList<Subtask> subtasksByEpic = getSubtasksByEpicId(epicId);
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

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (hasInProgress) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        } else if (hasNew) {
            epic.setStatus(TaskStatus.NEW);
        }
    }

    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        ArrayList<Subtask> subtasksByEpic = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                subtasksByEpic.add(subtask);
            }
        }
        return subtasksByEpic;
    }

    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    private final TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())));

    public void addTask(Task task) {
        if (isOverlapping(task)) {
            throw new IllegalArgumentException("Task overlaps with an existing task.");
        }
        prioritizedTasks.add(task);
        tasks.put(task.getId(), task);
    }

    public boolean isOverlapping(Task task) {
        return prioritizedTasks.stream()
                .anyMatch(existingTask -> tasksOverlap(existingTask, task));
    }

    private boolean tasksOverlap(Task t1, Task t2) {
        if (t1.getStartTime() == null || t2.getStartTime() == null) return false;

        LocalDateTime t1End = t1.getEndTime();
        LocalDateTime t2End = t2.getEndTime();
        return t1End != null && t2End != null &&
                !(t1End.isBefore(t2.getStartTime()) || t2End.isBefore(t1.getStartTime()));
    }


    public boolean epicExists(int epicId) {
        return epics.containsKey(epicId);
    }

}
