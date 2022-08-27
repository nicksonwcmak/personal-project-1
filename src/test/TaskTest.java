import model.Task;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

// method tests for Task
class TaskTest {
    Task testTask;

    @Test
    public void testConstructorNoPrereqs() {
        testTask = new Task("TEST",10);
        assertEquals("TEST", testTask.getName());
        assertEquals(10, testTask.getDuration());
    }

//    @Test
//    public void testConstructorInvalidPrereq() {
//        try {
//            testTask = new Task("TEST",10, new ArrayList<>(Collections.singleton(testTask)));
//            fail("Didn't catch InvalidPrereqsException when expected");
//        } catch (InvalidPrereqsException e) {
//            // expected
//        } // test not necessary: can't define a task to have itself as a prereq
//    } // may need this again if we allow modifying prereqs

    @Test
    public void testConstructorPrereqs() {
        Set<Task> prereqs = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            prereqs.add(new Task("TEST" + i, i));
        }
        testTask = new Task("TEST",10,prereqs);
        assertEquals(prereqs, testTask.getPrereqs());
    }
}
