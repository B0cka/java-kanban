import managers.InMemoryHistoryManager;
import managers.HistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerUpdateTests {

    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
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
