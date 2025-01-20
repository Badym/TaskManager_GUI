package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The Client class represents a client in the system, including details such as
 * the client's name, parent's name, phone number, and an optional description.
 * Each client also has a unique client ID.
 * 
 * <p>This class includes validation for the phone number format and allows for
 * setting and retrieving client details.</p>
 * 
 * @author Błażej Sztefka
 * @version 1.1
 */
@Data
@EqualsAndHashCode
@ToString
public class Client {
    private int clientId; // Unique identifier for the client
    String studentName; // The name of the student (client)
    String parentName;  // The name of the parent
    String phoneNumber; // The phone number of the client
    String description; // Additional description about the client (optional)
    
    /**
     * Constructs a new Client with the provided student name, parent name, phone number,
     * and optional description.
     * 
     * @param studentName  The name of the client (student).
     * @param parentName   The name of the client's parent.
     * @param phoneNumber  The phone number of the client.
     * @param description  Additional description about the client.
     */
    public Client(String studentName, String parentName, String phoneNumber, String description) {
        this.studentName = studentName;
        this.parentName = parentName;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }
    
    /**
    * Sets the parent name for the object.
    * Validates that the provided name is not null, not empty, and starts with an uppercase letter.
    *
    * @param newName the new name to set for the parent
    * @throws ValidationException if the name is null, empty, or does not start with an uppercase letter
    */
   public void setParentName(String newName) throws ValidationException {
       if (newName == null || newName.trim().isEmpty()) {
           throw new ValidationException("cant be null");
       }
       if (!Character.isUpperCase(newName.charAt(0))) {
           throw new ValidationException("Parent name upper case");
       } else {
           parentName = newName;
       }
   }

   /**
    * Sets the student name for the object.
    * Validates that the provided name is not null, not empty, and starts with an uppercase letter.
    *
    * @param newName the new name to set for the student
    * @throws ValidationException if the name is null, empty, or does not start with an uppercase letter
    */
   public void setStudentName(String newName) throws ValidationException {
       if (newName == null || newName.isEmpty()) {
           throw new ValidationException("Parent name cannot be null or empty");
       }
       if (!Character.isUpperCase(newName.charAt(0))) {
           throw new ValidationException("Parent name must start with an uppercase letter");
       }
       studentName = newName;
   }



    /**
     * Sets the phone number of the client, validating that it contains exactly 9 digits.
     *
     * @param phoneNumber The phone number to set for the client.
     * @throws ValidationException if the provided phone number is invalid.
     * 
     * <p>This method verifies that the provided phone number matches the required
     * format, consisting of exactly 9 digits. If the phone number is not valid,
     * a {@link ValidationException} is thrown with an appropriate error message.</p>
     */
    public void setPhoneNumber(String phoneNumber) throws ValidationException {
        checkPhoneNumber(phoneNumber); // Validate phone number
        this.phoneNumber = phoneNumber; // Set phone number if valid
    }

    /**
     * Checks if the provided phone number is exactly 9 digits long.
     * 
     * @param phoneNumber The phone number to validate.
     * @throws ValidationException if the phone number does not match the 9-digit requirement.
     */
    public void checkPhoneNumber(String phoneNumber) throws ValidationException {
        if (phoneNumber != null && !phoneNumber.matches("\\d{9}")) {
            throw ValidationException.invalidPhoneNumber(phoneNumber);
        }
    }
    
}
