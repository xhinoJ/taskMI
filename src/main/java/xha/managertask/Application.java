package xha.managertask;

import xha.managertask.entity.PriorityEnum;
import xha.managertask.entity.SortingParameterEnum;
import xha.managertask.entity.Task;
import xha.managertask.implementation.TaskManagerFIFOImpl;
import xha.managertask.implementation.TaskManagerImpl;
import xha.managertask.implementation.TaskManagerPriorityImpl;
import xha.managertask.spi.TaskManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Application {

    public static void main(String[] args) {


        TaskManager taskManager = new TaskManagerImpl();
        TaskManager taskManagerFIFO = new TaskManagerFIFOImpl();
        TaskManager taskManagerPriority = new TaskManagerPriorityImpl();

        fillDifferentTaskManagersWithRandomTasks(taskManager, taskManagerFIFO, taskManagerPriority);
        callTasks(taskManager, taskManagerFIFO, taskManagerPriority);

        Logger.getAnonymousLogger().log(Level.INFO, "\nTM Default: " + taskManager.listAllTasks(SortingParameterEnum.CREATION).toString());
        Logger.getAnonymousLogger().log(Level.INFO, "\nTM FIFO: " + taskManagerFIFO.listAllTasks(SortingParameterEnum.CREATION));
        Logger.getAnonymousLogger().log(Level.INFO, "\nTM PriorityEnum: " + taskManagerPriority.listAllTasks(SortingParameterEnum.CREATION));

    }

    private static void fillDifferentTaskManagersWithRandomTasks(TaskManager taskManager, TaskManager taskManagerFIFO, TaskManager taskManagerPriority) {

        IntStream.range(0, 3).forEach(t -> {
            taskManager.addTask(PriorityEnum.values()[new Random().nextInt(PriorityEnum.values().length)]);
            taskManagerFIFO.addTask(PriorityEnum.values()[new Random().nextInt(PriorityEnum.values().length)]);
            taskManagerPriority.addTask(PriorityEnum.values()[new Random().nextInt(PriorityEnum.values().length)]);
        });

    }

    private static void callTasks(TaskManager taskManager, TaskManager taskManagerFIFO, TaskManager taskManagerPriority) {

        ExecutorService threadpool = Executors.newCachedThreadPool();

        List<Future<?>> futures = new ArrayList<>();
        List<Callable<Task>> callables = new ArrayList<>();
        callables.add(() -> taskManager.addTask(PriorityEnum.HIGH));
        callables.add(() -> taskManagerFIFO.addTask(PriorityEnum.HIGH));
        callables.add(() -> taskManagerPriority.addTask(PriorityEnum.HIGH));

        callables.stream().forEach(t -> {
            futures.add(threadpool.submit(t));
            futures.add(threadpool.submit(t));
        });

        futures.stream().forEach(t -> {
            try {
                t.get();
            } catch (InterruptedException | ExecutionException e) {
                Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());

            }
        });

        threadpool.shutdown();

    }
}
