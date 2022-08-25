import model.Task;
import model.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskListTest {
    TaskList testTaskList;

    @BeforeEach
    public void setup() {
        testTaskList = new TaskList();
    }

    @Test
    public void testConstructor() {
        List<Task> tasks = testTaskList.getTasks();
        assertEquals(0, tasks.size());
    }
}
