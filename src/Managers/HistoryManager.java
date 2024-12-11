package Managers;

import java.util.List;

public interface HistoryManager {
    public void add(Tasks.Task task);
    void remove(int id);
    List<Tasks.Task> getHistory();
}