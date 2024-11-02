import java.util.Objects;
import java.util.HashMap;
public class Task {
    private int id;
    private String title;
    private String description;
    private TaskStatus status;

    public Task() {
    }

    public Task(String description, TaskStatus status, String title) {
        this.description = description;
        this.status = status;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}