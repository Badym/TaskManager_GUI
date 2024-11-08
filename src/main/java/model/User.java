package model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * The User class represents a user in the system who manages a list of tasks and clients.
 * This class provides methods to add, remove, and retrieve tasks and clients.
 * It also automatically assigns unique IDs to each task and client upon addition.
 * 
 * <p>Note: The {@code removeClient} and {@code removeTask} methods update 
 * the IDs of remaining clients and tasks to ensure continuity after removal.</p>
 * 
 * @see Task
 * @see Client
 * @see ValidationException
 * 
 * @version 1.1
 * @author Błażej Sztefka
 */
@Data
@EqualsAndHashCode
@ToString
public class User {

    // List of tasks associated with the user
    private List<Task> taskList = new ArrayList<>();
    
    // List of clients associated with the user
    private List<Client> clientList = new ArrayList<>();

    /**
     * Constructs a new {@code User} instance with default clients and tasks.
     * This constructor initializes a new {@code User} object with sample clients 
     * and a single sample task for demonstration purposes.
     */
    public User() {
        Client c1 = new Client("Pati", "Monika", "432789234", "2class");
        Client c2 = new Client("Bartek", "Klaudia", "506923876", "Good");
        Client c3 = new Client("Michal", "Krzysztof", "123456780", "Bad");
        this.addClient(c1);
        this.addClient(c2);
        this.addClient(c3);
        
        Task t1 = new Task("Matematyka", "nowy lol", 2, 2024, 12, 23, 12, 30);
        this.addTask(t1);
    }

    /**
     * Adds a new task to the user's task list and assigns a unique ID.
     * 
     * @param newTask The new task to be added to the user's task list.
     */
    public void addTask(Task newTask) {
        taskList.add(newTask);
        newTask.setTaskId(this.taskList.size()); // Assign task ID based on list size
    }

    /**
     * Adds a new client to the user's client list and assigns a unique ID.
     * 
     * @param newClient The new client to be added to the user's client list.
     */
    public void addClient(Client newClient) {
        clientList.add(newClient);
        newClient.setClientId(this.clientList.size()); // Assign client ID based on list size
    }
//
//    /**
//     * Retrieves the list of tasks associated with the user.
//     * 
//     * @return A list of tasks.
//     */
//    public List<Task> getTaskList() {
//        return taskList;
//    }

//    /**
//     * Retrieves the list of clients associated with the user.
//     * 
//     * @return A list of clients.
//     */
//    public List<Client> getClientList() {
//        return clientList;
//    }

    /**
     * Updates the IDs of clients in the list after a client is removed.
     * Adjusts IDs to ensure continuity and avoid gaps.
     * 
     * @param clientId The ID of the client that was removed.
     */
    private void updateClientIds(int clientId) {
        for (int i = clientId + 1; i < clientList.size(); i++) {
            clientList.get(i).setClientId(i);
        }
    }

    /**
     * Updates the IDs of tasks in the list after a task is removed.
     * Adjusts IDs to ensure continuity and avoid gaps.
     * 
     * @param taskId The ID of the task that was removed.
     */
    private void updateTaskIds(int taskId) {
        for (int i = taskId + 1; i < taskList.size(); i++) {
            taskList.get(i).setTaskId(i); 
        }
    }

    /**
     * Removes a client from the user's client list by ID.
     * Updates client IDs to maintain order and throws an exception if the ID is invalid.
     * 
     * @param clientId The ID of the client to be removed.
     * @throws ValidationException if the client ID is out of range.
     */
    public void removeClient(int clientId) throws ValidationException {
        if (clientId < 1 || clientId > clientList.size()) {
            throw ValidationException.clientNotFound(clientId);
        }
        this.updateClientIds(clientId - 1);
        this.clientList.remove(clientId - 1);
    }

    /**
     * Removes a task from the user's task list by ID.
     * Updates task IDs to maintain order and throws an exception if the ID is invalid.
     * 
     * @param taskId The ID of the task to be removed.
     * @throws ValidationException if the task ID is out of range.
     */
    public void removeTask(int taskId) throws ValidationException {
        if (taskId < 1 || taskId > taskList.size()) {
            throw ValidationException.taskNotFound(taskId);
        }
        this.updateTaskIds(taskId - 1);
        this.taskList.remove(taskId - 1);
    }

//    /**
//     * Sets the user's task list.
//     * 
//     * @param taskList The new list of tasks for the user.
//     */
//    public void setTaskList(List<Task> taskList) {
//        this.taskList = taskList;
//    }

//    /**
//     * Sets the user's client list.
//     * 
//     * @param clientList The new list of clients for the user.
//     */
//    public void setClientList(List<Client> clientList) {
//        this.clientList = clientList;
//    }

    /**
     * Retrieves a client from the client's list by their unique ID.
     * 
     * @param client_id The unique identifier of the client to retrieve.
     * @return The client associated with the specified ID.
     * @throws ValidationException if the client ID is out of range.
     */
    public Client getClientById(int client_id) throws ValidationException {
        if (client_id < 0 || client_id >= clientList.size()) {
            throw ValidationException.clientNotFound(client_id);
        }
        return this.clientList.get(client_id);
    }
}
