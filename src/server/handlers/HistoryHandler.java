package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import managers.InMemoryTaskManager;


import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler {

    private final Gson gson = new Gson();

    private final InMemoryTaskManager inMemoryTaskManager;

    public HistoryHandler(InMemoryTaskManager taskManager) {
        this.inMemoryTaskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            System.out.println("Handling history request...");

            if ("GET".equals(exchange.getRequestMethod())) {
                String response = gson.toJson(inMemoryTaskManager.getHistory());
                System.out.println("History response: " + response);
                sendText(exchange, response, 200);
            } else {
                System.out.println("Invalid method: " + exchange.getRequestMethod());
                sendNotFound(exchange);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendText(exchange, "Internal Server Error: " + e.getMessage(), 500);
        }
    }

}