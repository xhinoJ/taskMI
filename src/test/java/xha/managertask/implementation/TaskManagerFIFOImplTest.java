package xha.managertask.implementation;

import org.junit.jupiter.api.Test;
import xha.managertask.entity.PriorityEnum;
import xha.managertask.entity.SortingParameterEnum;
import xha.managertask.entity.Task;
import xha.managertask.spi.TaskManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

class TaskManagerFIFOImplTest {

    @Test
    void testAddWithFIFO() {
        TaskManager tmFifo = new TaskManagerFIFOImpl();

        Task t1 = tmFifo.addTask(PriorityEnum.LOW);
        Task t2 = tmFifo.addTask(PriorityEnum.LOW);
        Task t3 = tmFifo.addTask(PriorityEnum.LOW);
        Task t4 = tmFifo.addTask(PriorityEnum.LOW);

        assertThat(
                ((TaskManagerFIFOImpl) tmFifo).getSortedList(SortingParameterEnum.CREATION),
                containsInAnyOrder( t2, t3, t4));
    }

}
