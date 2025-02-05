package server;

import com.sun.net.httpserver.HttpServer;
import managers.InMemoryTaskManager;
import server.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private final HttpServer server;
    private final InMemoryTaskManager taskManager = new InMemoryTaskManager();

    public HttpTaskServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);


        server.createContext("/tasks", new TaskHandler(taskManager));
        server.createContext("/subtasks", new SubtaskHandler(taskManager));
        server.createContext("/epics", new EpicHandler(taskManager));
        server.createContext("/history", new HistoryHandler(taskManager));
        server.createContext("/prioritized", new PrioritizedHandler(taskManager));

        server.start();
        System.out.println("Сервер запущен на порту " + PORT);
    }

    public static void main(String[] args) throws IOException {
        new HttpTaskServer();
    }
}
