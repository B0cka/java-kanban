import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class TaskManagerTests {

    @Test
    void testTaskEquality() {
        Task task1 = new Task("Task 1", TaskStatus.NEW, "Title 1");
        task1.setId(1);  // Задаем id вручную для проверки
        Task task2 = new Task("Task 1", TaskStatus.NEW, "Title 1");
        task2.setId(1);  // id тоже равен 1

        assertEquals(task1, task2, "Tasks should be equal when their ids are the same.");
    }

    @Test
    void testEpicEquality() {
        Epic epic1 = new Epic("Epic 1", "Description", TaskStatus.NEW);
        epic1.setId(1);
        Epic epic2 = new Epic("Epic 1", "Description", TaskStatus.NEW);
        epic2.setId(1);

        assertEquals(epic1, epic2, "Epics should be equal when their ids are the same.");
    }

    @Test
    void testSubtaskEquality() {
        Subtask subtask1 = new Subtask("Subtask 1", TaskStatus.NEW, "Subtask", "Epic Title", "Epic Description", TaskStatus.NEW);
        subtask1.setId(1);
        Subtask subtask2 = new Subtask("Subtask 1", TaskStatus.NEW, "Subtask", "Epic Title", "Epic Description", TaskStatus.NEW);
        subtask2.setId(1);

        assertEquals(subtask1, subtask2, "Subtasks should be equal when their ids are the same.");
    }

    @Test
    void testManagersReturnInitializedManagers() {
        TaskManager taskManager = Manager.getDefault();
        assertNotNull(taskManager, "The manager returned by Managers.getDefault should be initialized and ready to use.");
    }

    @Test
    void testHistoryManagerReturnInitialized() {
        HistoryManager historyManager = Manager.getDefaultHistory();
        assertNotNull(historyManager, "The HistoryManager returned by Managers.getDefaultHistory should be initialized.");
    }

    @Test
    void testAddAndFindTasks() {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        Task task = new Task("Test Task", TaskStatus.NEW, "Title");
        manager.createTask(task);

        Epic epic = new Epic("Test Epic", "Description", TaskStatus.NEW);
        manager.createEpic(epic);

        Subtask subtask = new Subtask("Test Subtask", TaskStatus.NEW, "Subtask Title", "Epic Title", "Epic Description", TaskStatus.NEW);
        subtask.setEpicId(epic.getId());
        manager.createSubtask(subtask);

        assertNotNull(manager.getTask(task.getId()), "Task should be found by id.");
        assertNotNull(manager.getEpicById(epic.getId()), "Epic should be found by id.");
        assertNotNull(manager.getSubtask(subtask.getId()), "Subtask should be found by id.");
    }

    @Test
    void testTaskIdConflict() {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        Task task1 = new Task("Test Task 1", TaskStatus.NEW, "Title");
        task1.setId(1);
        manager.createTask(task1);

        Task task2 = new Task("Test Task 2", TaskStatus.NEW, "Title");
        task2.setId(2); // Не конфликтует с task1

        manager.createTask(task2);

        assertEquals(task1.getId(), manager.getTask(task1.getId()).getId(), "Task 1 should be correctly retrieved.");
        assertEquals(task2.getId(), manager.getTask(task2.getId()).getId(), "Task 2 should be correctly retrieved.");
    }

    @Test
    void testTaskImmutabilityWhenAdded() {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        Task task = new Task("Test Task", TaskStatus.NEW, "Title");
        task.setId(1);

        manager.createTask(task);

        Task retrievedTask = manager.getTask(task.getId());
        assertEquals(task.getTitle(), retrievedTask.getTitle(), "Task title should remain unchanged.");
        assertEquals(task.getDescription(), retrievedTask.getDescription(), "Task description should remain unchanged.");
        assertEquals(task.getStatus(), retrievedTask.getStatus(), "Task status should remain unchanged.");
    }

}