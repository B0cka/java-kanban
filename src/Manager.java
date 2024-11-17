public final class Manager {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }


}
