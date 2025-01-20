/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TaskManagerTests;

import model.Client;
import model.Task;
import model.User;
import model.ValidationException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for the {@link User} class.
 * This class contains parameterized tests to validate the behavior of the {@code User} class methods,
 * such as adding and removing clients and tasks.
 * 
 * <p>The tests ensure that the {@link User} object behaves as expected when managing lists of clients and tasks.</p>
 * 
 * @author badim
 */
public class UserTest {

    private User user;

    /**
     * Initializes a {@link User} object before each test.
     * Ensures the {@code user} object is in a consistent state for testing.
     */
    @BeforeEach
    public void setUp() {
        user = new User();
    }

    /**
     * Tests adding a client to the user’s client list.
     * Ensures that the client list size increases as expected after adding a client.
     * 
     * @param clientName the name of the client to add
     */
    @ParameterizedTest
    @ValueSource(strings = {"Maciek", "Anna", "John"}) // Example client names
    void testAddClient(String clientName) {
        Client client = new Client(clientName, "Szymon", "123456789", "good student");
        int initialSize = user.getClientList().size();

        user.addClient(client);

        assertEquals(initialSize + 1, user.getClientList().size(), "Client list size should increase by 1");
    }

    /**
     * Tests adding a task to the user’s task list.
     * Ensures that the task list size increases as expected after adding a task.
     * 
     * @param subject the subject of the task to add
     */
    @ParameterizedTest
    @ValueSource(strings = {"Matematyka", "Biologia", "Fizyka"}) // Example task subjects
    void testAddTask(String subject) {
        Task task = new Task(subject, "123", 2, 2024, 11, 3, 11, 30);
        int initialSize = user.getTaskList().size();

        user.addTask(task);

        assertEquals(initialSize + 1, user.getTaskList().size(), "Task list size should increase by 1");
    }

    /**
     * Tests removing a client by index when the index is valid.
     * Ensures the client list size decreases as expected.
     * 
     * @param testIndex the index of the client to remove
     */
    @ParameterizedTest
    @ValueSource(ints = {1,2}) 
    void testRemoveClientValidIndex(int testIndex) {
        try {
            int initialSize = user.getClientList().size();
            
            user.removeClient(testIndex);

            assertEquals(initialSize - 1, user.getClientList().size(), "Client list size should decrease by 1");
        } catch (ValidationException e) {
            fail("ValidationException should not be thrown for a valid index");
        }
    }

    /**
     * Tests removing a client by index when the index is invalid.
     * Ensures the client list size remains unchanged and a {@link ValidationException} is thrown.
     * 
     * @param testIndex the index of the client to remove
     */
    @ParameterizedTest
    @ValueSource(ints = {100000000, -1}) // Invalid client indices
    void testRemoveClientInvalidIndex(int testIndex) {
        int initialSize = user.getClientList().size();

        try {
            user.removeClient(testIndex);
            fail("ValidationException expected for an invalid index");
        } catch (ValidationException e) {
            assertEquals(initialSize, user.getClientList().size(), "Client list size should remain unchanged");
        }
    }
}
