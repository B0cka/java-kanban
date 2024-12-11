package Tests;

import managers.InMemoryHistoryManager;
import managers.HistoryManager;
import tasks.Task;
import tasks.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTests {

    private HistoryManager historyManager;


    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void testAddTaskToHistory() {
        Task task = new Task("Task 1", TaskStatus.NEW, "Description");
        task.setId(1);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "History should contain one task.");
        assertEquals(task, history.get(0), "The added task should be in the history.");
    }

    @Test
    void testRemoveTaskFromHistory() {
        Task task1 = new Task("Task 1", TaskStatus.NEW, "Description");
        task1.setId(1);
        Task task2 = new Task("Task 2", TaskStatus.NEW, "Description");
        task2.setId(2);

        historyManager.add(task1);
        historyManager.add(task2);

        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "History should contain one task after removal.");
        assertEquals(task2, history.get(0), "The remaining task should be Task 2.");
    }

    @Test
    void testRemoveNonExistentTask() {
        Task task = new Task("Task 1", TaskStatus.NEW, "Description");
        task.setId(1);

        historyManager.add(task);
        historyManager.remove(999); // Удаляем несуществующую задачу

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "History should remain unchanged when removing a non-existent task.");
    }

    @Test
    void testHistoryOrder() {
        Task task1 = new Task("Task 1", TaskStatus.NEW, "Description");
        task1.setId(1);
        Task task2 = new Task("Task 2", TaskStatus.NEW, "Description");
        task2.setId(2);

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "History should contain two tasks.");
        assertEquals(task1, history.get(0), "Task 1 should be first in history.");
        assertEquals(task2, history.get(1), "Task 2 should be second in history.");
    }

    @Test
    void testUpdateTaskInHistory() {
        Task task = new Task("Task 1", TaskStatus.NEW, "Description");
        task.setId(1);

        historyManager.add(task);
        historyManager.add(task); // Добавляем ту же задачу второй раз

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "History should contain only one instance of the task.");
        assertEquals(task, history.get(0), "The task should be in the history only once.");
    }

}
