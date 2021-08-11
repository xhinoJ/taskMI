package xha.managertask.implementation;

import org.junit.jupiter.api.Test;
import xha.managertask.entity.PriorityEnum;
import xha.managertask.entity.SortingParameterEnum;
import xha.managertask.entity.Task;
import xha.managertask.spi.TaskManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

class TaskManagerPriorityImplTest {

    @Test
    void testAddWithPriority() {
        TaskManager taskManagerPriority = new TaskManagerPriorityImpl();

        Task t1 = taskManagerPriority.addTask(PriorityEnum.HIGH);
        Task t2 = taskManagerPriority.addTask(PriorityEnum.MEDIUM);
        Task t3 = taskManagerPriority.addTask(PriorityEnum.HIGH);

        assertThat(
                ((TaskManagerPriorityImpl) taskManagerPriority).getSortedList(SortingParameterEnum.CREATION),
                containsInAnyOrder(t1, t2, t3));
    }

}
