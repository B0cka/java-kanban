package managers;

import tasks.Task;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {


    @Override
    public void remove(int id) {
        Node node = nodes.get(id); // Ищем узел в HashMap
        if (node != null) {
            removeNode(node); // Удаляем узел из связного списка
        }
    }
    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node current = head;
        while (current != null) {
            history.add(current.task); // Добавляем задачу из узла
            current = current.next;   // Переходим к следующему узлу
        }
        return history;
    }


    private final HashMap<Integer, Node> nodes = new HashMap<>();
    private Node head;
    private Node tail;

    private void linkLast(Task task) {
        Node newNode = new Node(tail, task, null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        nodes.put(task.getId(), newNode);
    }

    private void removeNode(Node node) {
        if (node == null) return;

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next; // Если удаляем первый узел
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev; // Если удаляем последний узел
        }

        nodes.remove(node.task.getId()); // Удаляем из HashMap
    }
    @Override
    public void add(Task task) {
        if (nodes.containsKey(task.getId())) {
            removeNode(nodes.get(task.getId()));
        }
        linkLast(task);
    }
}