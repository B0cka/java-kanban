package server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import managers.InMemoryTaskManager;
import tasks.Subtask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class SubtaskHandler extends BaseHttpHandler {
    private final InMemoryTaskManager inMemoryTaskManager;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter()) // Для Duration
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Для LocalDateTime
            .create();


    public SubtaskHandler(InMemoryTaskManager taskManager) {
        this.inMemoryTaskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");

            if ("GET".equals(method) && pathParts.length == 2) {
                sendText(exchange, gson.toJson(inMemoryTaskManager.getAllSubtasks()), 200);

                System.out.println("History after adding task(sub): " + inMemoryTaskManager.getHistory());
            } else if ("GET".equals(method) && pathParts.length == 3) {
                try {
                    int subtaskId = Integer.parseInt(pathParts[2]);
                    Subtask subtask = inMemoryTaskManager.getSubtask(subtaskId);

                    if (subtask != null) {
                        sendText(exchange, gson.toJson(subtask), 200);
                    } else {
                        sendText(exchange, "Подзадача с ID " + subtaskId + " не найдена", 404);
                    }
                } catch (NumberFormatException e) {
                    sendText(exchange, "Некорректный ID", 400);
                }
                System.out.println("History after adding task(sub): " + inMemoryTaskManager.getHistory());
            } else if ("POST".equals(method) && pathParts.length == 2) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestBody = reader.lines().collect(Collectors.joining());

                System.out.println("Полученный JSON: " + requestBody);
                Subtask subtask = gson.fromJson(requestBody, Subtask.class);

                int epicId = subtask.getEpicId();
                System.out.println("Переданный epicId: " + epicId);
                System.out.println("Список эпиков: " + inMemoryTaskManager.getAllEpics());

                if (subtask.getId() > 0 && inMemoryTaskManager.getSubtask(subtask.getId()) != null) {
                    inMemoryTaskManager.updateSubtask(subtask);

                    sendText(exchange, "Подзадача с ID " + subtask.getId() + " обновлена", 200);
                } else {
                    inMemoryTaskManager.createSubtask(subtask);
                    sendText(exchange, "Подзадача создана с ID " + subtask.getId(), 201);
                }

            } else {
                sendNotFound(exchange);
            }
            System.out.println("History after adding task(sub): " + inMemoryTaskManager.getHistory());
        } catch (Exception e) {
            e.printStackTrace();
            sendText(exchange, "Ошибка сервера: " + e.getMessage(), 500);
        }
    }
}
