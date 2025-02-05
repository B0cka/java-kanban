package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import managers.InMemoryTaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler {
    private final InMemoryTaskManager inMemoryTaskManager;
    private final Gson gson = new Gson();
    public PrioritizedHandler(InMemoryTaskManager taskManager) {
        this.inMemoryTaskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            sendText(exchange, gson.toJson(inMemoryTaskManager.getPrioritizedTasks()), 200);
        } else {
            sendNotFound(exchange);
        }
    }
}