package tasks;

import managers.InMemoryTaskManager;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;




public class Epic extends Task {
    private Duration epicDuration;
    private LocalDateTime epicStartTime;
    private LocalDateTime epicEndTime;
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    private ArrayList<Integer> subtaskIds;

    public Epic(String title, String description, TaskStatus status) {
        super(description, status, title);
        this.subtaskIds = new ArrayList<>();
    }

    public void addSubtask(int subtaskId) {
        if (subtaskIds == null) {
            subtaskIds = new ArrayList<>();
        }
        subtaskIds.add(subtaskId); // Добавляем подзадачу в список
    }

    public void removeSubtask(int subtaskId) {
        subtaskIds.remove((Integer) subtaskId);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskIds, epic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds);
    }

    @Override
    public String toString() {
        return "Tasks.Epic{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }

    public void updateTimeFields() {
        List<Subtask> subtasks = inMemoryTaskManager.getAllSubtasks();
        epicDuration = subtasks.stream()
                .filter(task -> task.getDuration() != null)
                .map(Task::getDuration)
                .reduce(Duration.ZERO, Duration::plus);

        epicStartTime = subtasks.stream()
                .filter(task -> task.getStartTime() != null)
                .map(Task::getStartTime)
                .min(LocalDateTime::compareTo)
                .orElse(null);

        epicEndTime = subtasks.stream()
                .filter(task -> task.getEndTime() != null)
                .map(Task::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }
}