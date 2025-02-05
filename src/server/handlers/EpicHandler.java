package server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import managers.InMemoryTaskManager;
import tasks.Epic;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;

public class EpicHandler extends BaseHttpHandler {
    private final InMemoryTaskManager inMemoryTaskManager;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter()) // Для Duration
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Для LocalDateTime
            .create();


    public EpicHandler(InMemoryTaskManager taskManager) {
        this.inMemoryTaskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");

            if ("GET".equals(method) && pathParts.length == 2) {
                sendText(exchange, gson.toJson(inMemoryTaskManager.getAllEpics()), 200);
                System.out.println(inMemoryTaskManager.getHistory());
                System.out.println("History after adding task(epic): " + inMemoryTaskManager.getHistory());
            } else if ("GET".equals(method) && pathParts.length == 3) {
                try {
                    int epicId = Integer.parseInt(pathParts[2]);
                    Epic epic = inMemoryTaskManager.getEpicById(epicId);

                    if (epic != null) {
                        sendText(exchange, gson.toJson(epic), 200);
                    } else {
                        sendText(exchange, "Эпик с ID " + epicId + " не найден", 404);
                    }
                } catch (NumberFormatException e) {
                    sendText(exchange, "Некорректный ID", 400);
                }
                System.out.println(inMemoryTaskManager.getHistory());
                System.out.println("History after adding task(epic): " + inMemoryTaskManager.getHistory());
            } else if ("POST".equals(method) && pathParts.length == 2) {
                InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
                Epic epic = gson.fromJson(reader, Epic.class);

                if (epic == null || epic.getTitle() == null || epic.getDescription() == null) {
                    sendText(exchange, "Ошибка: некорректный формат эпика", 400);
                    return;
                }

                if (inMemoryTaskManager.getEpicById(epic.getId()) != null) {
                    inMemoryTaskManager.updateEpic(epic);
                    sendText(exchange, "Эпик с ID " + epic.getId() + " обновлен", 200);
                } else {
                    inMemoryTaskManager.createEpic(epic);
                    sendText(exchange, "Эпик создан с ID " + epic.getId(), 201);
                }
            } else {
                sendNotFound(exchange);
            }
            System.out.println("History after adding task(epic): " + inMemoryTaskManager.getHistory());
        } catch (Exception e) {
            e.printStackTrace();
            sendText(exchange, "Ошибка сервера: " + e.getMessage(), 500);
        }
    }
}
