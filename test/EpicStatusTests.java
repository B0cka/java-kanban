import managers.InMemoryTaskManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

public class EpicStatusTests {

    @Test
    public void testEpicStatusWhenAllSubtasksAreNew() {
        TaskManager taskManager = new InMemoryTaskManager();

        Epic epic = new Epic("Epic 1", "Description", TaskStatus.NEW);
        taskManager.createEpic(epic);

        Subtask subtask1 = new Subtask("Subtask 1", TaskStatus.NEW, "Subtask 1", epic.getId());
        Subtask subtask2 = new Subtask("Subtask 2", TaskStatus.NEW, "Subtask 2", epic.getId());

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        assertEquals(TaskStatus.NEW, epic.getStatus(), "Epic status should be NEW when all subtasks are NEW");
    }

//    @Test
//    public void testEpicStatusWhenAllSubtasksAreDone() {
//        TaskManager taskManager = new InMemoryTaskManager();
//
//        Epic epic = new Epic("Epic 1", "Description", TaskStatus.NEW);
//        taskManager.createEpic(epic);
//
//        Subtask subtask1 = new Subtask("Subtask 1", TaskStatus.DONE, "Subtask 1", epic.getId());
//        Subtask subtask2 = new Subtask("Subtask 2", TaskStatus.DONE, "Subtask 2", epic.getId());
//
//        taskManager.createSubtask(subtask1);
//        taskManager.createSubtask(subtask2);
//
//        assertEquals(TaskStatus.DONE, epic.getStatus(), "Epic status should be DONE when all subtasks are DONE");
//    }
//
//    @Test
//    public void testEpicStatusWhenSubtasksAreInProgress() {
//        TaskManager taskManager = new InMemoryTaskManager();
//
//        Epic epic = new Epic("Epic 1", "Description", TaskStatus.NEW);
//        taskManager.createEpic(epic);
//
//        Subtask subtask1 = new Subtask("Subtask 1", TaskStatus.IN_PROGRESS, "Subtask 1", epic.getId());
//        Subtask subtask2 = new Subtask("Subtask 2", TaskStatus.DONE, "Subtask 2", epic.getId());
//
//        taskManager.createSubtask(subtask1);
//        taskManager.createSubtask(subtask2);
//
//        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Epic status should be IN_PROGRESS when any subtask is IN_PROGRESS");
//    }


}