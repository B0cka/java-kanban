package Managers;

import java.util.List;

public interface HistoryManager {
    public void add(Task.Task task);
    void remove(int id);
    List<Task.Task> getHistory();
}