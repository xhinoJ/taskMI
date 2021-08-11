package xha.managertask.entity;

public class Task {

    private final int pid;
    private final PriorityEnum priorityEnum;
    private final long creationTime = System.currentTimeMillis();

    public Task(int pid, PriorityEnum priorityEnum) {
        this.pid = pid;
        this.priorityEnum = priorityEnum;
    }

    public int getPid() {
        return pid;
    }

    public PriorityEnum getPriorityEnum() {
        return priorityEnum;
    }

    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "pid=" + pid +
                ", priorityEnum=" + priorityEnum +
                "}";
    }
}
