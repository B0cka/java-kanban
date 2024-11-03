public class Subtask extends Task{


    private int epicId;
    public Subtask(String description, TaskStatus status, String title, String title1, String description1, TaskStatus status1) {
        super(description, status, title);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }


    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '}';
    }
}