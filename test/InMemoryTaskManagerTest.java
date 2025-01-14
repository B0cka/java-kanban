import managers.InMemoryHistoryManager;
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

    @Test
    void shouldDetectOverlappingIntervals() {

        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Description 1", TaskStatus.NEW, "Task 1");
        task1.setStartTime(LocalDateTime.of(2023, 1, 1, 10, 0));
        task1.setDuration(Duration.ofHours(2));
        taskManager.addTask(task1);


        Task task2 = new Task("Description 2", TaskStatus.NEW, "Task 2");
        task2.setStartTime(LocalDateTime.of(2023, 1, 1, 13, 0));
        task2.setDuration(Duration.ofHours(2));
        assertDoesNotThrow(() -> taskManager.addTask(task2));


        Task task3 = new Task("Description 3", TaskStatus.NEW, "Task 3");
        task3.setStartTime(LocalDateTime.of(2023, 1, 1, 11, 0));
        task3.setDuration(Duration.ofHours(2));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskManager.addTask(task3));
        assertEquals("Task overlaps with an existing task.", exception.getMessage());
    }

    @Test
    void shouldNotOverlapForNullStartTime() {

        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Description 1", TaskStatus.NEW, "Task 1");
        task1.setStartTime(LocalDateTime.of(2023, 1, 1, 10, 0));
        task1.setDuration(Duration.ofHours(2));
        taskManager.addTask(task1);

        // Создаём задачу без времени начала
        Task task2 = new Task("Description 2", TaskStatus.NEW, "Task 2");
        task2.setStartTime(null);
        task2.setDuration(null);

        assertDoesNotThrow(() -> taskManager.addTask(task2));
    }

    @Test
    void shouldAllowAddingTasksWhenNoOverlap() {

        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Description 1", TaskStatus.NEW, "Task 1");
        task1.setStartTime(LocalDateTime.of(2023, 1, 1, 10, 0));
        task1.setDuration(Duration.ofHours(2));

        Task task2 = new Task("Description 2", TaskStatus.NEW, "Task 2");
        task2.setStartTime(LocalDateTime.of(2023, 1, 1, 12, 0));
        task2.setDuration(Duration.ofHours(2));

        assertDoesNotThrow(() -> taskManager.addTask(task1));

    }
}
