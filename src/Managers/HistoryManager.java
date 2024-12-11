package Managers;

import java.util.List;

public interface HistoryManager {
    public void add(tasks.Task task);
    void remove(int id);
    List<tasks.Task> getHistory();
}