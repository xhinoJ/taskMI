package xha.managertask.implementation;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import xha.managertask.entity.PriorityEnum;
import xha.managertask.entity.SortingParameterEnum;
import xha.managertask.entity.Task;
import xha.managertask.spi.TaskManager;

import static org.hamcrest.Matchers.*;

class TaskManagerImplTest {

    @Test
    void testAddWithMaxCapacity() {
        TaskManager tm = new TaskManagerImpl();

        Task t1 = tm.addTask(PriorityEnum.LOW);
        Task t2 = tm.addTask(PriorityEnum.LOW);
        Task t3 = tm.addTask(PriorityEnum.LOW);
        Task t4 = tm.addTask(PriorityEnum.LOW);

        MatcherAssert.assertThat(
                ((TaskManagerImpl) tm).getSortedList(SortingParameterEnum.CREATION),
                containsInAnyOrder(t1, t2, t3));
    }

    @Test
    void testKills() {
        TaskManager tm = new TaskManagerImpl(5);

        Task t1 = tm.addTask(PriorityEnum.LOW);
        Task t2 = tm.addTask(PriorityEnum.MEDIUM);
        Task t3 = tm.addTask(PriorityEnum.HIGH);
        Task t4 = tm.addTask(PriorityEnum.LOW);
        Task t5 = tm.addTask(PriorityEnum.MEDIUM);

        tm.killTaskByPid(t2.getPid());
        MatcherAssert.assertThat(
                ((TaskManagerImpl) tm).getSortedList(SortingParameterEnum.CREATION),
                containsInAnyOrder(t1, t3, t4, t5));

        tm.killTaskByPriority(PriorityEnum.LOW);
        MatcherAssert.assertThat(
                ((TaskManagerImpl) tm).getSortedList(SortingParameterEnum.CREATION),
                containsInAnyOrder(t3, t5));

        tm.killAllTasks();
        MatcherAssert.assertThat(
                ((TaskManagerImpl) tm).getSortedList(SortingParameterEnum.CREATION),
                is(empty()));

    }

}
