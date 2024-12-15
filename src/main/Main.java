package main;

import managers.InMemoryTaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();


        Task task1 = new Task("First task", TaskStatus.NEW, "Description for Task 1");
        Task task2 = new Task("Second task", TaskStatus.NEW, "Description for Task 2");

        taskManager.createTask(task1);
        taskManager.createTask(task2);


        Epic epic1 = new Epic("Epic 1", "Description of Epic 1", TaskStatus.NEW);
        Epic epic2 = new Epic("Epic 2", "Description of Epic 2", TaskStatus.NEW);

        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);


        Subtask subtask1 = new Subtask("Subtask 1.1", TaskStatus.NEW, "Description of Subtask 1.1", epic1.getId());
        Subtask subtask2 = new Subtask("Subtask 1.2", TaskStatus.NEW, "Description of Subtask 1.2", epic1.getId());
        Subtask subtask3 = new Subtask("Subtask 2.1", TaskStatus.NEW, "Description of Subtask 2.1", epic2.getId());

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);


        System.out.println("Tasks:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task.getTitle() + ": " + task.getDescription() + " (Status: " + task.getStatus() + ")");
        }


        System.out.println("\nEpics:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic.getTitle() + ": " + epic.getDescription() + " (Status: " + epic.getStatus() + ")");
        }


        System.out.println("\nSubtasks:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask.getTitle() + ": " + subtask.getDescription() + " (Status: " + subtask.getStatus() + ")");
        }


        System.out.println("\nSubtasks for Epic 1:");
        for (Subtask subtask : taskManager.getSubtasksByEpicId(epic1.getId())) {
            System.out.println(subtask.getTitle() + ": " + subtask.getDescription() + " (Status: " + subtask.getStatus() + ")");
        }
    }
}