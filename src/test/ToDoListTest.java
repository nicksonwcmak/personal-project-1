import model.Task;
import model.ToDoList;
import model.exceptions.AlreadyInToDoListException;
import model.exceptions.MissingPrerequisiteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ToDoListTest {
    ToDoList testToDoList;

    @BeforeEach
    public void setup() {
        testToDoList = new ToDoList();
    }

    @Test
    public void testConstructor() {
        Collection<Task> testTasks = testToDoList.orderedTasks();
        assertTrue(testTasks.isEmpty());
    }

    @Test
    public void testAddTaskNoOtherTasks() {
        Task testTask = new Task("TEST",10);
        try {
            testToDoList.addTask(testTask);
            Collection<Task> testTasks = testToDoList.orderedTasks();
            assertTrue(testTasks.contains(testTask));
            assertEquals(1,testTasks.size());
        } catch (Exception e) {
            fail("Caught exception when no exception expected");
        }
    }

    @Test
    public void testAddTaskOtherTasks() {
        int testElts = 10;
        try {
            for (int i = 1; i <= testElts; i++) {
                testToDoList.addTask(new Task("TEST" + i, i));
            }
            Collection<Task> testTasks = testToDoList.orderedTasks();
            for (int i = 1; i <= testElts; i++) {
                Task testTask = new Task("TEST" + i, i);
                assertTrue(testTasks.contains(testTask)); // works because overridden equals
            }
            assertEquals(testElts,testTasks.size());
        } catch (Exception e) {
            fail("Caught exception when no exception expected");
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
        } catch (MissingPrerequisiteException e) {
            fail("Caught MissingPrerequisiteException when AlreadyInToDoListException was expected");
        }
    }

    @Test
    public void testAddTaskSameName() {
        try {
            testToDoList.addTask(new Task("TEST",1));
            testToDoList.addTask(new Task("TEST",2));
            fail("AlreadyInToDoListException was not thrown");
        } catch (AlreadyInToDoListException e) {
            // expected
        } catch (MissingPrerequisiteException e) {
            fail("Caught MissingPrerequisiteException when AlreadyInToDoListException was expected");
        }
    }

    @Test
    public void testAddTaskMissingPrereq() {
        Task testTask1 = new Task("TEST1",1);
        Task testTask2 = new Task("TEST2",2);
        Set<Task> prereq3 = new HashSet<>();
        prereq3.add(testTask2);
        prereq3.add(testTask1);
        Task testTask3 = new Task("TEST3",3,prereq3);
        try {
            testToDoList.addTask(testTask2);
            testToDoList.addTask(testTask3);
            fail("MissingPrerequisiteException was not thrown");
        } catch (AlreadyInToDoListException e) {
            fail("Caught AlreadyInToDoListException when MissingPrerequisiteException was expected");
        } catch (MissingPrerequisiteException e) {
            // expected
        }
    }


    @Test
    public void testRemoveTaskEmpty() {
        Task testTask = new Task("TEST",1);
        testToDoList.removeTask(testTask);

        Collection<Task> testTasks = testToDoList.orderedTasks();
        assertEquals(0,testTasks.size());
    }

    @Test
    public void testRemoveTaskSingle() {
        try {
            Task testTask = new Task("TEST",1);
            testToDoList.addTask(testTask);
            testToDoList.removeTask(testTask);

            Collection<Task> testTasks = testToDoList.orderedTasks();
            assertEquals(0,testTasks.size());
        } catch (Exception e) {
            fail("Caught exception when no exception expected");
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
            Collection<Task> tasks = testToDoList.orderedTasks();
            assertTrue(tasks.contains(testTask1));
            assertTrue(tasks.contains(testTask2));
            assertTrue(tasks.contains(testTask3));
            assertFalse(tasks.contains(testTask4));
            assertTrue(tasks.contains(testTask5));
            assertFalse(tasks.contains(testTask6));
        } catch (Exception e) {
            fail("Caught exception when no exception expected");
        }

    }

    @Test
    public void testFindTaskWithNameNoException() {
        try {
            Task testTask1 = new Task("TEST",1);
            Task testTask2 = new Task("TEST1",1);
            Task testTask3 = new Task("TES",1);
            Task testTask4 = new Task("test",1);
            testToDoList.addTask(testTask1);
            testToDoList.addTask(testTask2);
            testToDoList.addTask(testTask3);
            testToDoList.addTask(testTask4);
            assertEquals(testTask1,testToDoList.findTaskWithName("TEST"));
        } catch (Exception e) {
            fail("Caught exception when no exception expected");
        }
    }

    @Test
    public void testFindTaskWithNameException() {
        try {
            Task testTask2 = new Task("TEST1",1);
            Task testTask3 = new Task("TES",1);
            Task testTask4 = new Task("test",1);
            testToDoList.addTask(testTask2);
            testToDoList.addTask(testTask3);
            testToDoList.addTask(testTask4);
            Task returned = testToDoList.findTaskWithName("TEST");
            fail("NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // expected
        } catch (AlreadyInToDoListException | MissingPrerequisiteException e) {
            fail("Caught other exception when NoSuchElementException was expected");
        }
    }

    @Test
    public void testOrderedTasksEmpty() {
        List<Task> ordered = testToDoList.orderedTasks();
        assertTrue(ordered.isEmpty());
    }

    @Test
    public void testOrderedTasksSingle() {
        Task testTask = new Task("TEST",1);
        try {
            testToDoList.addTask(testTask);

            List<Task> ordered = testToDoList.orderedTasks();
            assertTrue(ordered.contains(testTask));
            assertEquals(1,ordered.size());
        } catch (Exception e) {
            fail("Caught exception when no exception expected");
        }
    }

    @Test
    public void testOrderedTasksDisjoint() {
        Task task1 = new Task("TEST1",1);
        Task task2 = new Task("TEST2",2);
        Set<Task> prereq3 = new HashSet<>();
        prereq3.add(task2);
        Task task3 = new Task("TEST3",3,prereq3);
        try {
            testToDoList.addTask(task2);
            testToDoList.addTask(task3);
            testToDoList.addTask(task1);
            assertTrue(verifyTaskOrder(testToDoList.orderedTasks()));
        } catch (Exception e) {
            fail("Caught exception when no exception expected");
        }
    }

    @Test
    public void testOrderedTasksMany() {
        Task task1 = new Task("TEST1",1);
        Task task2 = new Task("TEST2",2);
        Set<Task> prereq3 = new HashSet<>();
        prereq3.add(task2);
        Task task3 = new Task("TEST3",3,prereq3); // prereqs 2
        Task task4 = new Task("TEST4",4);
        Task task5 = new Task("TEST5",5);
        Set<Task> prereq6 = new HashSet<>();
        prereq6.add(task1);
        prereq6.add(task4);
        prereq6.add(task5);
        Task task6 = new Task("TEST6",6,prereq6); // prereqs 1, 4, 5
        Set<Task> prereq7 = new HashSet<>();
        prereq7.add(task1);
        Task task7 = new Task("TEST7",7,prereq7); // prereqs 1
        Set<Task> prereq89 = new HashSet<>();
        prereq89.add(task6);
        prereq89.add(task7);
        Task task8 = new Task("TEST8",8,prereq89); // prereqs 6, 7
        Task task9 = new Task("TEST9",9,prereq89); // prereqs 6, 7

        try {
            testToDoList.addTask(task1);
            testToDoList.addTask(task2);
            testToDoList.addTask(task3);
            testToDoList.addTask(task4);
            testToDoList.addTask(task5);
            testToDoList.addTask(task6);
            testToDoList.addTask(task7);
            testToDoList.addTask(task8);
            testToDoList.addTask(task9);
            assertTrue(verifyTaskOrder(testToDoList.orderedTasks()));
        } catch (Exception e) {
            fail("Caught exception when no exception expected");
        }
    }

    private boolean verifyTaskOrder(List<Task> ordered) {
        Set<Task> checked = new HashSet<>();
        for (Task task : ordered) {
            if (!checked.containsAll(task.getPrereqs())){
                return false;
            }
            checked.add(task);
        }
        return true;
    }
}
