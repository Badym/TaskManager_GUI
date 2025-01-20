/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TaskManagerTests;

import model.Task;
import model.ValidationException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for the {@link Task} class.
 * This class contains parameterized tests to validate the behavior of the {@code Task} class methods,
 * such as date and time format validation, as well as subject validation.
 * 
 * <p>The tests use the JUnit 5 framework with {@link ParameterizedTest} to test multiple
 * input values for both valid and invalid scenarios.</p>
 * 
 * @author badim
 */
public class TaskTest {
    private Task task;

    /**
     * Initializes a {@link Task} object with valid default values before each test.
     * This ensures that the {@code task} object is in a consistent state for testing.
     */
    @BeforeEach
    public void setUp() {
        task = new Task("Matematyka", "123", 2, 2024, 11, 3, 11, 30);
    }
    
    /**
     * Tests the {@code setDateS} method with valid ISO_LOCAL_DATE strings.
     * These values should not throw an {@link IllegalArgumentException}.
     * 
     * @param dateS a valid date string in ISO_LOCAL_DATE format
     */
    @ParameterizedTest
    @ValueSource(strings = {"2023-01-01", "2024-12-31", "2000-02-29"}) // poprawne daty w formacie ISO_LOCAL_DATE
    void validDateSTest(String dateS) {
        try {
            task.setDateS(dateS);
            // Test passes if no exception is thrown
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown for valid date format");
        }
    }

    /**
     * Tests the {@code setDateS} method with invalid date strings.
     * These values should throw an {@link IllegalArgumentException}.
     * 
     * @param dateS an invalid date string
     */
    @ParameterizedTest
    @ValueSource(strings = {" ", "2023-13-01", "invalid-date", "01/01/2023", "2023-02-32"}) // niepoprawne formaty dat
    void invalidDateSTest(String dateS) {
        try {
            task.setDateS(dateS);
            fail("IllegalArgumentException expected for invalid date format");
        } catch (IllegalArgumentException e) {
            // Test passes if exception is thrown
        }
    }

    /**
     * Tests the {@code setTimeS} method with invalid time strings.
     * These values should throw an {@link IllegalArgumentException}.
     * 
     * @param timeS an invalid time string
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "25:61", "invalid-time", "12:60", "1234", "12-00"}) // niepoprawne formaty czasu
    void invalidTimeSTest(String timeS) {
        try {
            task.setTimeS(timeS);
            fail("IllegalArgumentException expected for invalid time format");
        } catch (IllegalArgumentException e) {
            // Test passes if exception is thrown
        }
    }

    /**
     * Tests the {@code setTimeS} method with valid ISO_LOCAL_TIME strings.
     * These values should not throw an {@link IllegalArgumentException}.
     * 
     * @param timeS a valid time string in ISO_LOCAL_TIME format
     */
    @ParameterizedTest
    @ValueSource(strings = {"12:00", "00:00", "23:59", "08:30"}) // poprawne czasy w formacie ISO_LOCAL_TIME
    void validTimeSTest(String timeS) {
        try {
            task.setTimeS(timeS);
            // Test passes if no exception is thrown
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown for valid time format");
        }
    }

    /**
     * Tests the {@code setSubject} method with valid subject names.
     * These values should not throw a {@link ValidationException}.
     * 
     * @param text a valid subject name
     */
    @ParameterizedTest
    @ValueSource(strings = {"Math", "Science", "History", "Art"}) // poprawne przedmioty
    void validSubjectTest(String text) {
        try {
            task.setSubject(text);
            // Test passes if no exception is thrown
        } catch (ValidationException e) {
            fail("ValidationException should not be thrown for valid subject");
        }
    }

    /**
     * Tests the {@code setSubject} method with invalid subject names.
     * These values should throw a {@link ValidationException}.
     * 
     * @param text an invalid subject name (null, empty, or containing only whitespace)
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {" ", "\t", "\n"}) // ciągi zawierające tylko białe znaki
    void invalidSubjectTest(String text) {
        try {
            task.setSubject(text);
            fail("ValidationException expected for invalid subject");
        } catch (ValidationException e) {
            // Test passes if exception is thrown
        }
    }
}
