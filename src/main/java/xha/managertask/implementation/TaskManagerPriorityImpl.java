package xha.managertask.implementation;

import xha.managertask.entity.PriorityEnum;
import xha.managertask.entity.Task;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskManagerPriorityImpl extends TaskManagerImpl {

    @Override
    public Task addTask(PriorityEnum priorityEnum) {
        Integer pid = pidGenerator.getAndIncrement();
        Task task = new Task(pid, priorityEnum);
        addTaskByPriority(task);
        return task;
    }

    private synchronized void addTaskByPriority(Task task) {
        if (!tasks.offer(task)) {
            Logger.getAnonymousLogger().log(Level.INFO, "Maximum capacity reached - removing lowest priority task if present");
            tasks.stream()
                    .filter(t -> t.getPriorityEnum().compareTo(task.getPriorityEnum()) < 0)
                    .min(Comparator.comparing(Task::getCreationTime))
                    .ifPresent(e -> tasks.remove(e));
            tasks.offer(task);
        }
    }

}
