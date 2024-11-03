public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("First task", TaskStatus.NEW, "Task_1");
        Task task2 =  new Task("it`s second pf tasks, i believ thats it great", TaskStatus.NEW, "Task_2" );
        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic1 = new Epic("Epic 1", "Description of Epic 1", TaskStatus.NEW);
        manager.createEpic(epic1);

        Epic epic2 = new Epic("Epic 2", "Description of Epic 2", TaskStatus.NEW);
        manager.createEpic(epic2);

        Subtask subtask1_1 = new Subtask("Subtask 1.1", TaskStatus.NEW, "Subtask Title 1.1", "Title", "Description", TaskStatus.NEW);
        subtask1_1.setEpicId(epic1.getId()); // Присваиваем эпик к подзадаче

        Subtask subtask1_2 = new Subtask("Subtask 1.2", TaskStatus.NEW, "Subtask Title 1.2", "Title", "Description", TaskStatus.NEW);
        subtask1_2.setEpicId(epic1.getId()); // Присваиваем эпик к подзадаче

        manager.createSubtask(subtask1_2);
        manager.createSubtask(subtask1_1);

        // Создание подзадачи для второго эпика
        Subtask subtask2_1 = new Subtask("Subtask 2.1", TaskStatus.NEW, "Subtask Title 2.1", "Title", "Description", TaskStatus.NEW);
        subtask2_1.setEpicId(epic2.getId()); // Присваиваем эпик к подзадаче
        manager.createSubtask(subtask2_1);

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
        manager.printAllTasks();
        manager.printAllEpics();
        manager.printAllSubtasks();
    }

}
