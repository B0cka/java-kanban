package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private int id;
    private String title;
    private String description;
    private TaskStatus status;
    private Duration duration;
    private LocalDateTime startTime;

    public Task() {

    }

    public Task(String description, TaskStatus status, String title) {
        this.description = description;
        this.status = status;
        this.title = title;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    // Добавлены методы для установки значений
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tasks.Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

}