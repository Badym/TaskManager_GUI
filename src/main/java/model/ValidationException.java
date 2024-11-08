package model;

/**
 * Custom exception class for validation-related errors in the application.
 * It extends the Exception class and provides specific static methods 
 * to throw meaningful validation exceptions in various contexts.
 * 
 * @author Błażej Sztefka
 * @version 1.1
 */
public class ValidationException extends Exception {

    /**
     * Constructs a new ValidationException with the specified detail message.
     * 
     * @param message The detail message of the exception.
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Returns a ValidationException when a client with a specified ID is not found.
     * 
     * @param clientId The ID of the client that was not found.
     * @return A ValidationException indicating the client was not found.
     */
    public static ValidationException clientNotFound(int clientId) {
        return new ValidationException("Client with ID " + clientId + " does not exist.");
    }

    /**
     * Returns a ValidationException when a task with a specified ID is not found.
     * 
     * @param taskId The ID of the task that was not found.
     * @return A ValidationException indicating the task was not found.
     */
    public static ValidationException taskNotFound(int taskId) {
        return new ValidationException("Task with ID " + taskId + " does not exist.");
    }

    /**
     * Returns a ValidationException when a phone number is invalid.
     * The phone number must contain exactly 9 digits.
     * 
     * @param phoneNumber The invalid phone number.
     * @return A ValidationException indicating the phone number is invalid.
     */
    public static ValidationException invalidPhoneNumber(String phoneNumber) {
        return new ValidationException("Phone number " + phoneNumber + " is invalid. It should contain exactly 9 digits.");
    }
}
