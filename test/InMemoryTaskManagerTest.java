import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import java.time.LocalDateTime;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;
    private Task task1;
    private Task task2;
    private Task task3;
    private Epic epic1;
    private Subtask subtask1;
    private Subtask subtask2;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();

        task1 = new Task("Task 1", TaskStatus.NEW, "Test Task 1");
        task2 = new Task("Task 2", TaskStatus.NEW, "Test Task 2");
        task3 = new Task("Task 3", TaskStatus.NEW, "Test Task 3");


        epic1 = new Epic("Epic 1", "Epic description", TaskStatus.NEW);


        subtask1 = new Subtask("Subtask 1", TaskStatus.NEW, "Subtask 1", epic1.getId());
        subtask2 = new Subtask("Subtask 2", TaskStatus.NEW, "Subtask 2", epic1.getId());
    }


    @Test
    void testCreateAndGetTask() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        Task retrievedTask = taskManager.getTask(task1.getId());
        assertNotNull(retrievedTask);
        assertEquals(task1.getTitle(), retrievedTask.getTitle());
    }


    @Test
    void testUpdateTask() {
        taskManager.createTask(task1);
        task1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task1);

        Task updatedTask = taskManager.getTask(task1.getId());
        assertEquals(TaskStatus.IN_PROGRESS, updatedTask.getStatus());
    }


    @Test
    void testRemoveTask() {
        taskManager.createTask(task2);
        taskManager.removeTask(task2.getId());

        Task deletedTask = taskManager.getTask(task2.getId());
        assertNull(deletedTask);
    }


    @Test
    void testCreateEpic() {
        taskManager.createEpic(epic1);
        Epic retrievedEpic = taskManager.getEpicById(epic1.getId());
        assertNotNull(retrievedEpic);
        assertEquals(epic1.getTitle(), retrievedEpic.getTitle());
    }

    @Test
    void testTaskTimeCalculation() {
        task1.setStartTime(LocalDateTime.of(2025, 1, 12, 10, 0));
        task1.setDuration(Duration.ofHours(2));

        taskManager.createTask(task1);

        assertEquals(task1.getStartTime().plus(task1.getDuration()), task1.getEndTime());
    }


    @Test
    void testHistory() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        taskManager.getTask(task3.getId());

        assertEquals(3, taskManager.getHistory().size());
    }

}
