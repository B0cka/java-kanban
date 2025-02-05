package testsForHandels;


import managers.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tasks.TaskStatus.NEW;

public class InMemoryTasksHandlersTests {

}

class InMemoryTaskManagerTest {
    private InMemoryTaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void testCreateAndGetTask() {
        Task task = new Task("Test Task", NEW, "Tests aks");
        taskManager.createTask(task);

        Task retrievedTask = taskManager.getTask(task.getId());
        assertNotNull(retrievedTask);
        assertEquals(task.getTitle(), retrievedTask.getTitle());
    }

    @Test
    void testCreateAndGetEpic() {
        Epic epic = new Epic("Test Epic", "Epic Description", NEW);
        taskManager.createEpic(epic);

        Epic retrievedEpic = taskManager.getEpicById(epic.getId());
        assertNotNull(retrievedEpic);
        assertEquals(epic.getTitle(), retrievedEpic.getTitle());
    }

    @Test
    void testCreateAndGetSubtask() {
        Epic epic = new Epic("Test Epic", "Epic Description",NEW);
        taskManager.createEpic(epic);

        Subtask subtask = new Subtask("Subtask 1", NEW,"Subteat", epic.getId());
        taskManager.createSubtask(subtask);

        Subtask retrievedSubtask = taskManager.getSubtask(subtask.getId());
        assertNotNull(retrievedSubtask);
        assertEquals(subtask.getTitle(), retrievedSubtask.getTitle());
    }

    @Test
    void testUpdateTask() {
        Task task = new Task("Old Task", NEW, "old decripriun");
        taskManager.createTask(task);

        task.setTitle("Updated Task");
        taskManager.updateTask(task);

        Task updatedTask = taskManager.getTask(task.getId());
        assertEquals("Updated Task", updatedTask.getTitle());
    }

    @Test
    void testUpdateEpic() {
        Epic epic = new Epic("Old Epic", "Old Description", NEW);
        taskManager.createEpic(epic);

        epic.setTitle("Updated Epic");
        taskManager.updateEpic(epic);

        Epic updatedEpic = taskManager.getEpicById(epic.getId());
        assertEquals("Updated Epic", updatedEpic.getTitle());
    }

    @Test
    void testUpdateSubtask() {
        Epic epic = new Epic("Test Epic", "Epic Description", NEW);
        taskManager.createEpic(epic);

        Subtask subtask = new Subtask("Subtask 1", NEW,"Epic Description", epic.getId());
        taskManager.createSubtask(subtask);

        subtask.setTitle("Updated Subtask");
        taskManager.updateSubtask(subtask);

        Subtask updatedSubtask = taskManager.getSubtask(subtask.getId());
        assertEquals("Updated Subtask", updatedSubtask.getTitle());
    }


}