import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    @BeforeEach
    public abstract void setUp();

    @Test
    public void testCreateAndGetTask() {
        Task task = new Task("Test Task", TaskStatus.NEW, "Description");
        taskManager.createTask(task);
        assertNotNull(taskManager.getTask(task.getId()), "Task should be retrievable by ID");
    }

    @Test
    public void testCreateAndGetEpic() {
        Epic epic = new Epic("Test Epic", "Description", TaskStatus.NEW);
        taskManager.createEpic(epic);
        assertNotNull(taskManager.getEpicById(epic.getId()), "Epic should be retrievable by ID");
    }

    @Test
    public void testCreateAndGetSubtask() {
        Epic epic = new Epic("Test Epic", "Description", TaskStatus.NEW);
        taskManager.createEpic(epic);

        Subtask subtask = new Subtask("Test Subtask", TaskStatus.NEW, "Description", epic.getId());
        taskManager.createSubtask(subtask);
        assertNotNull(taskManager.getSubtask(subtask.getId()), "Subtask should be retrievable by ID");
    }
}