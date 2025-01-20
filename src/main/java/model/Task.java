package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The Task class represents a task assigned to a specific client. 
 * Each task includes details such as subject, description, associated client, 
 * and date and time information.
 * 
 * <p>Tasks are formatted to specific date and time patterns and validated 
 * upon setting these values.</p>
 * 
 * <p>Attributes are provided to identify the task by a unique task ID.</p>
 * 
 * @author Błażej Sztefka
 * @version 1.1
 */
@Data
@EqualsAndHashCode
@ToString
public class Task {
    
    private int taskId; // Unique identifier for the task
    private String subject;     // Subject of the task
    private String description; // Description of the task
    private int clientId;       // Client associated with the task
    private LocalDate date;     // Date associated with the task
    private String dateS;       // String representation of the date
    private LocalTime time;     // Time associated with the task
    private String timeS;       // String representation of the time
    private TaskStatus status;

    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructs a new Task with the specified subject, description, client, 
     * and date and time details.
     * 
     * @param subject    The subject of the task.
     * @param description The description of the task.
     * @param client     The client ID associated with the task.
     * @param year       The year of the task's due date.
     * @param month      The month of the task's due date.
     * @param day        The day of the task's due date.
     * @param hour       The hour of the task's due time.
     * @param minute     The minute of the task's due time.
     */
    public Task(String subject, String description, int client, int year, int month, int day, int hour, int minute) {
        this.subject = subject;
        this.description = description;
        this.clientId = client;
        this.date = LocalDate.of(year, month, day);
        this.time = LocalTime.of(hour, minute, 0);
        this.dateS = date.format(DATE_FORMATTER);
        this.timeS = time.format(TIME_FORMATTER);
        this.status = calculateStatus();
    }
    
    public void setSubject(String newSubject) throws ValidationException {
        if(newSubject == null || newSubject.trim().isEmpty()){
            throw new ValidationException("invalid subject");
        }else{
            subject = newSubject;
        }
    }
    
    /**
    * Calculates the task's status based on days until the due date.
    * 
    * @return the {@link TaskStatus} indicating the task's due status.
    */
    private TaskStatus calculateStatus() {
        long daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), date);

        if (daysUntilDue <= 3) {
            return TaskStatus.DUE_SOON;
        } else if (daysUntilDue <= 7) {
            return TaskStatus.DUE_THIS_WEEK;
        } else {
            return TaskStatus.LONG_TERM;
        }
    }

    /**
     * Constructs a new Task with the specified subject, description, client, 
     * date, and time.
     * 
     * @param subject    The subject of the task.
     * @param description The description of the task.
     * @param client     The client ID associated with the task.
     * @param date       The date of the task.
     * @param time       The time of the task.
     */
    public Task(String subject, String description, int client, LocalDate date, LocalTime time) {
        this.subject = subject;
        this.description = description;
        this.clientId = client;
        this.date = date;
        this.time = time;
        this.dateS = date.format(DATE_FORMATTER);
        this.timeS = time.format(TIME_FORMATTER);
    }


    /**
     * Sets the date of the task and updates the date string representation.
     * 
     * @param date The new date for the task.
     */
    public void setDate(LocalDate date) {
        this.date = date;
        this.dateS = date.format(DATE_FORMATTER);
        this.status = calculateStatus();
    }

    /**
     * Sets the time of the task and updates the time string representation.
     * 
     * @param time The new time for the task.
     */
    public void setTime(LocalTime time) {
        this.time = time;
        this.timeS = time.format(TIME_FORMATTER);
    }

    /**
     * Sets the date string and parses it to set the task date.
     * 
     * @param dateS The date string to set, formatted as yyyy-MM-dd.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    public void setDateS(String dateS) throws IllegalArgumentException {
        try {
            this.date = LocalDate.parse(dateS, DATE_FORMATTER);
            this.dateS = dateS;
            this.status = calculateStatus();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateS);
        }
    }

    /**
     * Sets the time string and parses it to set the task time.
     * 
     * @param timeS The time string to set, formatted as HH:mm.
     * @throws IllegalArgumentException if the time format is invalid.
     */
    public void setTimeS(String timeS) throws IllegalArgumentException {
        try {
            this.timeS = timeS;
            this.time = LocalTime.parse(this.timeS, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format: " + timeS);
        }
    }
}
