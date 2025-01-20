// Plik: TaskStatus.java
package model;


/**
 * Enum representing the status of a task based on its due date.
 * <p>
 * The possible task statuses are:
 * <ul>
 *     <li>{@link TaskStatus#DUE_SOON} - The task is due within 3 days.</li>
 *     <li>{@link TaskStatus#DUE_THIS_WEEK} - The task is due within the next week (but more than 3 days).</li>
 *     <li>{@link TaskStatus#LONG_TERM} - The task is due after more than a week.</li>
 * </ul>
 * @author Błażej Sztefka
 * @version 1.1
 */
public enum TaskStatus {
    DUE_SOON,        // Do wykonania w ciągu 3 dni
    DUE_THIS_WEEK,   // Do wykonania w ciągu tygodnia
    LONG_TERM        // Zadanie z dalekim terminem
}