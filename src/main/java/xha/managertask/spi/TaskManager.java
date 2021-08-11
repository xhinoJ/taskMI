package xha.managertask.spi;

import xha.managertask.entity.PriorityEnum;
import xha.managertask.entity.SortingParameterEnum;
import xha.managertask.entity.Task;

import java.util.List;

public interface TaskManager {

    Task addTask(PriorityEnum priorityEnum);

    List<Task> listAllTasks(SortingParameterEnum param);

    void killTaskByPid(int pid);

    void killTaskByPriority(PriorityEnum priorityEnum);

    void killAllTasks();

}
