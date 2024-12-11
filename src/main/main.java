package main;

import Managers.InMemoryTaskManager;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import Tasks.TaskStatus;

public class main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task1 = new Task("First task", TaskStatus.NEW, "Task_1");
        Task task2 =  new Task("it`s second pf tasks, i believ thats it great", TaskStatus.NEW, "Task_2" );
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);

        Epic epic1 = new Epic("Tasks.Epic 1", "Description of Tasks.Epic 1", TaskStatus.NEW);
        inMemoryTaskManager.createEpic(epic1);

        Epic epic2 = new Epic("Tasks.Epic 2", "Description of Tasks.Epic 2", TaskStatus.NEW);
        inMemoryTaskManager.createEpic(epic2);

        Subtask subtask1_1 = new Subtask("Tasks.Subtask 1.1", TaskStatus.NEW, "Tasks.Subtask Title 1.1", "Title", "Description", TaskStatus.NEW);
        subtask1_1.setEpicId(epic1.getId()); // Присваиваем эпик к подзадаче

        Subtask subtask1_2 = new Subtask("Tasks.Subtask 1.2", TaskStatus.NEW, "Tasks.Subtask Title 1.2", "Title", "Description", TaskStatus.NEW);
        subtask1_2.setEpicId(epic1.getId()); // Присваиваем эпик к подзадаче

        inMemoryTaskManager.createSubtask(subtask1_2);
        inMemoryTaskManager.createSubtask(subtask1_1);

        // Создание подзадачи для второго эпика
        Subtask subtask2_1 = new Subtask("Tasks.Subtask 2.1", TaskStatus.NEW, "Tasks.Subtask Title 2.1", "Title", "Description", TaskStatus.NEW);
        subtask2_1.setEpicId(epic2.getId()); // Присваиваем эпик к подзадаче
        inMemoryTaskManager.createSubtask(subtask2_1);

        System.out.println("Tasks:");
        System.out.println(task1.getTitle() + ": " + task1.getDescription() + " (Status: " + task1.getStatus() + ")");
        System.out.println(task2.getTitle() + ": " + task2.getDescription() + " (Status: " + task2.getStatus() + ")");

        System.out.println("Epics:");
        System.out.println(epic1.getTitle() + ": " + epic1.getDescription() + " (Status: " + epic1.getStatus() + ")");
        System.out.println(epic2.getTitle() + ": " + epic2.getDescription() + " (Status: " + epic2.getStatus() + ")");

        System.out.println("Subtasks");
        System.out.println(subtask1_1.getTitle() + ": " + subtask1_1.getDescription() + " (Status: " + subtask1_1.getStatus() + ")");
        System.out.println(subtask1_2.getTitle() + ": " + subtask1_2.getDescription() + " (Status: " + subtask1_2.getStatus() + ")");

        System.out.println("Subtasks");
        System.out.println(subtask2_1.getTitle() + ": " + subtask2_1.getDescription() + " (Status: " + subtask2_1.getStatus() + ")");
        System.out.println("/-----------------------------/");
        System.out.println("Tasks:");
        for (Task task : inMemoryTaskManager.getAllTasks()) {
            System.out.println(task.getTitle() + ": " + task.getDescription() + " (Status: " + task.getStatus() + ")");
        }

        // Вывод всех эпиков
        System.out.println("Epics:");
        for (Epic epic : inMemoryTaskManager.getAllEpics()) {
            System.out.println(epic.getTitle() + ": " + epic.getDescription() + " (Status: " + epic.getStatus() + ")");
        }

        // Вывод всех подзадач
        System.out.println("Subtasks:");
        for (Subtask subtask : inMemoryTaskManager.getAllSubtasks()) {
            System.out.println(subtask.getTitle() + ": " + subtask.getDescription() + " (Status: " + subtask.getStatus() + ")");
        }
    }

}
