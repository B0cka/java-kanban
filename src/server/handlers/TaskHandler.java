package server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import managers.InMemoryTaskManager;
import tasks.Task;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;

public class TaskHandler extends BaseHttpHandler {
    private final InMemoryTaskManager inMemoryTaskManager;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();


    public TaskHandler(InMemoryTaskManager taskManager) {
        this.inMemoryTaskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");

            System.out.println("Received request: " + method + " " + path);

            if ("GET".equals(method) && pathParts.length == 2) {
                // Получить все задачи
                String response = gson.toJson(inMemoryTaskManager.getAllTasks());
                sendText(exchange, response, 200);
                System.out.println(inMemoryTaskManager.getHistory());
            } else if ("GET".equals(method) && pathParts.length == 3) {
                try {
                    int taskId = Integer.parseInt(pathParts[2]);
                    Task task = inMemoryTaskManager.getTask(taskId);

                    if (task != null) {
                        sendText(exchange, gson.toJson(task), 200);
                    } else {
                        sendText(exchange, "Задача с ID " + taskId + " не найдена", 404);
                    }
                } catch (NumberFormatException e) {
                    sendText(exchange, "Некорректный ID", 400);
                }

            } else if ("POST".equals(method) && pathParts.length == 2) {
                InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
                Task task = gson.fromJson(reader, Task.class);

                if (task == null || task.getTitle() == null || task.getDescription() == null) {
                    sendText(exchange, "Ошибка: некорректный формат задачи", 400);
                    return;
                }

                if (inMemoryTaskManager.getTask(task.getId()) != null) {
                    inMemoryTaskManager.updateTask(task);
                    sendText(exchange, "Задача с ID " + task.getId() + " обновлена", 200);
                } else {
                    inMemoryTaskManager.createTask(task);
                    sendText(exchange, "Задача создана с ID " + task.getId(), 201);
                }
                System.out.println(inMemoryTaskManager.getHistory());
            } else if ("DELETE".equals(method) && pathParts.length == 3) {
                try {
                    int taskId = Integer.parseInt(pathParts[2]);
                    Task task = inMemoryTaskManager.getTask(taskId);

                    if (task != null) {
                        inMemoryTaskManager.removeTask(taskId);
                        sendText(exchange, "Задача с ID " + taskId + " удалена", 200);
                    } else {
                        sendText(exchange, "Задача с ID " + taskId + " не найдена", 404);
                    }
                } catch (NumberFormatException e) {
                    sendText(exchange, "Некорректный ID", 400);
                }
                System.out.println("History after adding task(task): " + inMemoryTaskManager.getHistory());
            } else {
                sendNotFound(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendText(exchange, "Ошибка сервера: " + e.getMessage(), 500);
        }
    }
}
