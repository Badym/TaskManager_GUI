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
        this.addClient(c1,c2,c3);

        
        Task t1 = new Task("Przedmiot 1", "Opis zadania 1", 3, 2024, 11, 20, 20, 10); // Termin za 2 dni
        Task t2 = new Task("Przedmiot 2", "Opis zadania 2", 2, 2024, 11, 21, 14, 58); // Termin za 3 dni
        Task t3 = new Task("Przedmiot 6", "Opis zadania 6", 1, 2024, 11, 19, 12, 0);  // Termin za 1 dzień
        Task t4 = new Task("Przedmiot 3", "Opis zadania 3", 1, 2024, 11, 24, 10, 15); // Termin za 6 dni
        Task t5 = new Task("Przedmiot 4", "Opis zadania 4", 2, 2024, 11, 22, 9, 36); // Termin za 4 dni
        Task t6 = new Task("Przedmiot 7", "Opis zadania 7", 3, 2024, 11, 23, 18, 30); // Termin za 5 dni
        Task t7 = new Task("Przedmiot 5", "Opis zadania 5", 1, 2024, 11, 26, 14, 35); // Termin za 8 dni
        Task t8 = new Task("Przedmiot 8", "Opis zadania 8", 3, 2024, 12, 5, 17, 45);  // Termin za 17 dni
        Task t9 = new Task("Przedmiot 9", "Opis zadania 9", 2, 2024, 12, 10, 8, 10);  // Termin za 22 dni




        this.addTask(t1,t2,t3,t4,t5,t6,t7,t8,t9);
    }

    /**
    * Adds one or more tasks to the user's task list and assigns unique task IDs.
    * The ID is based on the current size of the task list.
    * 
    * @param tasks One or more tasks to be added to the user's task list.
    */
   public void addTask(Task... tasks) {
       for (Task task : tasks) {
           taskList.add(task);
           task.setTaskId(this.taskList.size()); // Assign task ID based on list size
       }
   }

   /**
    * Adds one or more clients to the user's client list and assigns unique client IDs.
    * The ID is based on the current size of the client list.
    * 
    * @param clients One or more clients to be added to the user's client list.
    */
   public void addClient(Client... clients) {
       for (Client newClient : clients) {
           clientList.add(newClient);
           newClient.setClientId(this.clientList.size()); // Assign client ID based on list size
       }
   }


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
    * Also removes all tasks associated with the removed client from the task list.
    * 
    * @param clientId The ID of the client to be removed.
    * @throws ValidationException if the client ID is out of range.
    */
   public void removeClient(int clientId) throws ValidationException {
       if (clientId < 1 || clientId > clientList.size()) {
           throw ValidationException.clientNotFound(clientId);
       }

       // Remove all tasks associated with this client
       taskList.removeIf(task -> task.getClientId() == clientId);
       
       refreshTaskClientIds(clientId);

       // Remove the client from the list
       this.updateClientIds(clientId - 1);
       this.clientList.remove(clientId - 1);
   }
   
   /**
    * Updates the client IDs in tasks after a client is removed.
    * Decreases client ID by 1 for all tasks where the client ID is greater than the removed client ID.
    * 
    * @param removedClientId The ID of the client that was removed.
    */
   private void refreshTaskClientIds(int removedClientId) {
       for (Task task : taskList) {
           if (task.getClientId() > removedClientId) {
               task.setClientId(task.getClientId() - 1);
           }
       }
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
