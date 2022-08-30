import model.Task;
import model.ToDoList;
import model.exceptions.AlreadyInToDoListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ToDoListTest {
    ToDoList testToDoList;

    @BeforeEach
    public void setup() {
        testToDoList = new ToDoList();
    }

    @Test
    public void testConstructor() {
        Collection<Task> testTasks = testToDoList.getTasks();
        assertTrue(testTasks.isEmpty());
    }

    @Test
    public void testAddTaskNoOtherTasks() {
        Task testTask = new Task("TEST",10);
        try {
            testToDoList.addTask(testTask);
            Collection<Task> testTasks = testToDoList.getTasks();
            assertTrue(testTasks.contains(testTask));
            assertEquals(1,testTasks.size());
        } catch (AlreadyInToDoListException e) {
            fail("Caught AlreadyInToDoListException when no exception expected");
        }
    }

    @Test
    public void testAddTaskOtherTasks() {
        int testElts = 10;
        try {
            for (int i = 1; i <= testElts; i++) {
                testToDoList.addTask(new Task("TEST" + i, i));
            }
            Collection<Task> testTasks = testToDoList.getTasks();
            for (int i = 1; i <= testElts; i++) {
                Task testTask = new Task("TEST" + i, i);
                assertTrue(testTasks.contains(testTask)); // works because overridden equals
            }
            assertEquals(testElts,testTasks.size());
        } catch (AlreadyInToDoListException e) {
            fail("Caught AlreadyInToDoListException when no exception expected");
        }
    }

    @Test
    public void testAddTaskAlreadyAdded() {
        try {
            Task testTask1 = new Task("TEST1",1);
            testToDoList.addTask(testTask1);
            Task testTask2 = new Task("TEST2",2);
            testToDoList.addTask(testTask2);
            Set<Task> prereq = new HashSet<>();
            prereq.add(testTask1);
            prereq.add(testTask2);

            for (int i = 1; i <= 10; i++) {
                testToDoList.addTask(new Task("TEST" + i, i, prereq));
            }
            testToDoList.addTask(new Task("TEST10",10, prereq));
            fail("AlreadyInToDoListException was not thrown");
        } catch (AlreadyInToDoListException e) {
            // expected
        }
    }

    @Test
    public void testRemoveTaskEmpty() {
        Task testTask = new Task("TEST",1);
        testToDoList.removeTask(testTask);

        Collection<Task> testTasks = testToDoList.getTasks();
        assertEquals(0,testTasks.size());
    }

    @Test
    public void testRemoveTaskSingle() {
        try {
            Task testTask = new Task("TEST",1);
            testToDoList.addTask(testTask);
            testToDoList.removeTask(testTask);

            Collection<Task> testTasks = testToDoList.getTasks();
            assertEquals(0,testTasks.size());
        } catch (AlreadyInToDoListException e) {
            fail("Caught AlreadyInToDoListException when no exception expected");
        }
    }

    @Test
    public void testRemoveTaskWithPrereqs() {
        Task testTask1 = new Task("TEST1",1);
        Task testTask2 = new Task("TEST2",2);
        Task testTask3 = new Task("TEST3",3);
        Set<Task> prereqs4 = new HashSet<>();
        prereqs4.add(testTask1);
        prereqs4.add(testTask2);
        Task testTask4 = new Task("TEST4",4,prereqs4);
        Set<Task> prereqs5 = new HashSet<>();
        prereqs5.add(testTask3);
        Task testTask5 = new Task("TEST5",5,prereqs5);
        Set<Task> prereqs6 = new HashSet<>();
        prereqs6.add(testTask4);
        prereqs6.add(testTask5);
        Task testTask6 = new Task("TEST6",6,prereqs6);

        try {
            testToDoList.addTask(testTask1);
            testToDoList.addTask(testTask2);
            testToDoList.addTask(testTask3);
            testToDoList.addTask(testTask4);
            testToDoList.addTask(testTask5);
            testToDoList.addTask(testTask6);

            testToDoList.removeTask(testTask4);
            Collection<Task> tasks = testToDoList.getTasks();
            assertTrue(tasks.contains(testTask1));
            assertTrue(tasks.contains(testTask2));
            assertTrue(tasks.contains(testTask3));
            assertFalse(tasks.contains(testTask4));
            assertTrue(tasks.contains(testTask5));
            assertFalse(tasks.contains(testTask6));
        } catch (AlreadyInToDoListException e) {
            fail("Caught AlreadyInToDoListException when no exception expected");
        }

    }

    @Test
    public void testHasTaskWithName() {
        try {
            Task testTask1 = new Task("TEST",1);
            testToDoList.addTask(testTask1);

            assertTrue(testToDoList.hasTaskWithName("TEST"));
            assertFalse(testToDoList.hasTaskWithName("test"));
        } catch (AlreadyInToDoListException e) {
            fail("Caught AlreadyInToDoListException when no exception expected");
        }
    }

}
