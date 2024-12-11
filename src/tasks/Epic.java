package tasks;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds; // Список идентификаторов подзадач для эпика

    // Конструктор для создания эпика
    public Epic(String title, String description, TaskStatus status) {
        super(description, status, title);
        this.subtaskIds = new ArrayList<>(); // Инициализация списка подзадач
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

    // Метод для добавления подзадачи в эпик
    public void addSubtask(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    // Метод для удаления подзадачи из эпика
    public void removeSubtask(int subtaskId) {
        subtaskIds.remove((Integer) subtaskId); // Приведение к Integer для корректного удаления
    }

    // Метод для получения списка идентификаторов подзадач
    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
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

    public static class Subtask extends Task {

        private int epicId;

        public Subtask(String description, TaskStatus status, String title, int epicId) {
            super(description, status, title);
            this.epicId = epicId;
        }

        public int getEpicId() {
            return epicId;
        }

        public void setEpicId(int epicId) {
            this.epicId = epicId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Subtask subtask = (Subtask) o;
            return epicId == subtask.epicId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), epicId);
        }

        @Override
        public String toString() {
            return "Subtask{" +
                    "id=" + getId() +
                    ", title='" + getTitle() + '\'' +
                    ", description='" + getDescription() + '\'' +
                    ", status=" + getStatus() +
                    ", epicId=" + epicId +
                    '}';
        }
    }
}