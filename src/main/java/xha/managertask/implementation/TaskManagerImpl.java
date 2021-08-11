package xha.managertask.implementation;

import xha.managertask.entity.PriorityEnum;
import xha.managertask.entity.SortingParameterEnum;
import xha.managertask.entity.Task;
import xha.managertask.spi.TaskManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TaskManagerImpl implements TaskManager {

    protected final static int CAPACITY = 3;
    protected final AtomicInteger pidGenerator = new AtomicInteger(0);
    protected BlockingQueue<Task> tasks;

    public TaskManagerImpl() {
        this.tasks = new LinkedBlockingQueue<>(CAPACITY);
    }

    //For testing purposes
    TaskManagerImpl(int capacity) {
        this.tasks = new LinkedBlockingQueue<>(capacity);
    }

    @Override
    public Task addTask(PriorityEnum priorityEnum) {
        Integer pid = pidGenerator.getAndIncrement();
        Task task = new Task(pid, priorityEnum);
        addTaskWithMaxCapacity(task);
        return task;
    }

    private void addTaskWithMaxCapacity(Task task) {
        if (!tasks.offer(task)) {
            Logger.getAnonymousLogger().log(Level.INFO, "Could not addTask new task " + task + " - maximum capacity reached");
        }
    }

    //May reflect transient state
    @Override
    public List<Task> listAllTasks(SortingParameterEnum param) {
        return getSortedList(param);
    }

    List<Task> getSortedList(SortingParameterEnum param) {
        switch (param) {
            case CREATION:
                return tasks.stream()
                        .sorted(Comparator.comparingLong(Task::getCreationTime))
                        .collect(Collectors.toList());
            case PRIORITY:
                return tasks.stream()
                        .sorted(Comparator.comparing(Task::getPriorityEnum).reversed())
                        .collect(Collectors.toList());
            case PID:
                return tasks.stream()
                        .sorted(Comparator.comparingInt(Task::getPid))
                        .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void killTaskByPid(int pid) {
        tasks.removeIf(task -> task.getPid() == pid);
    }

    @Override
    public void killTaskByPriority(PriorityEnum priorityEnum) {
        tasks.removeIf(task -> task.getPriorityEnum() == priorityEnum);
    }

    @Override
    public void killAllTasks() {
        tasks.clear();
    }

}
