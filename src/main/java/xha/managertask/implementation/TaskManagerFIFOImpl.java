package xha.managertask.implementation;

import xha.managertask.entity.PriorityEnum;
import xha.managertask.entity.Task;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskManagerFIFOImpl extends TaskManagerImpl {

    @Override
    public Task addTask(PriorityEnum priorityEnum) {
        Integer pid = pidGenerator.getAndIncrement();
        Task task = new Task(pid, priorityEnum);
        addTaskFIFO(task);
        return task;
    }

    private synchronized void addTaskFIFO(Task task) {
        if (!tasks.offer(task)) {
            Logger.getAnonymousLogger().log(Level.INFO, "Removing first task, maximum reached");
            tasks.remove();
            tasks.offer(task);
        }
    }

}
