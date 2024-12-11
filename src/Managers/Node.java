package Managers;

import Tasks.Task;

public class Node {
    Task task;
    Node next;
    Node prev;

    Node(Node prev, Task task, Node next) {
        this.prev = prev;
        this.task = task;
        this.next = next;
    }
}
