package main;

import Managers.InMemoryTaskManager;
import tasks.Epic;
import tasks.Task;
import tasks.TaskStatus;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task1 = new Task("First task", TaskStatus.NEW, "Task1");
        Task task2 = new Task("It's second of tasks, I believe that it's great", TaskStatus.NEW, "Task2");
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);

        Epic epic1 = new Epic("Epic1", "Description of Epic 1", TaskStatus.NEW);
        inMemoryTaskManager.createEpic(epic1);

        Epic epic2 = new Epic("Epic2", "Description of Epic 2", TaskStatus.NEW);
        inMemoryTaskManager.createEpic(epic2);

        Epic.Subtask subtask1One = new Epic.Subtask("Subtask 1.1", TaskStatus.NEW, "Subtask Title 1.1", epic1.getId());
        Epic.Subtask subtask1Two = new Epic.Subtask("Subtask 1.2", TaskStatus.NEW, "Subtask Title 1.2", epic1.getId());



        inMemoryTaskManager.createSubtask(subtask1Two);
        inMemoryTaskManager.createSubtask(subtask1One);

        Epic.Subtask subtask2One = new Epic.Subtask("Subtask 2.1", TaskStatus.NEW, "Subtask Title 2.1", epic2.getId());
        subtask2One.setEpicId(epic2.getId());
        inMemoryTaskManager.createSubtask(subtask2One);

        System.out.println("Tasks:");
        System.out.println(task1.getTitle() + ": " + task1.getDescription() + " (Status: " + task1.getStatus() + ")");
        System.out.println(task2.getTitle() + ": " + task2.getDescription() + " (Status: " + task2.getStatus() + ")");

        System.out.println("Epics:");
        System.out.println(epic1.getTitle() + ": " + epic1.getDescription() + " (Status: " + epic1.getStatus() + ")");
        System.out.println(epic2.getTitle() + ": " + epic2.getDescription() + " (Status: " + epic2.getStatus() + ")");

        System.out.println("Subtasks:");
        System.out.println(subtask1One.getTitle() + ": " + subtask1One.getDescription() + " (Status: " + subtask1One.getStatus() + ")");
        System.out.println(subtask1Two.getTitle() + ": " + subtask1Two.getDescription() + " (Status: " + subtask1Two.getStatus() + ")");
        System.out.println(subtask2One.getTitle() + ": " + subtask2One.getDescription() + " (Status: " + subtask2One.getStatus() + ")");

        System.out.println("/-----------------------------/");
        System.out.println("Tasks:");
        for (Task task : inMemoryTaskManager.getAllTasks()) {
            System.out.println(task.getTitle() + ": " + task.getDescription() + " (Status: " + task.getStatus() + ")");
        }

        System.out.println("Epics:");
        for (Epic epic : inMemoryTaskManager.getAllEpics()) {
            System.out.println(epic.getTitle() + ": " + epic.getDescription() + " (Status: " + epic.getStatus() + ")");
        }

        System.out.println("Subtasks:");
        for (Epic.Subtask subtask : inMemoryTaskManager.getAllSubtasks()) {
            System.out.println(subtask.getTitle() + ": " + subtask.getDescription() + " (Status: " + subtask.getStatus() + ")");
        }
    }
}
