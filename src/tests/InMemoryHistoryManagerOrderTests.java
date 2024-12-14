import managers.InMemoryHistoryManager;
import managers.HistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerOrderTests {

    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
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
}
