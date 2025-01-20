/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TaskManagerTests;

import model.Client;
import model.ValidationException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for the {@link Client} class.
 * This class contains parameterized tests to verify validation logic for various client attributes,
 * such as phone number, student name, and parent name.
 * 
 * Each test ensures that the {@code Client} class behaves as expected when provided with
 * valid or invalid input values.
 * 
 * <p>Tests utilize the JUnit 5 testing framework with {@link ParameterizedTest}
 * for reusable and scalable test cases.</p>
 * 
 * @author badim
 */
public class ClientTest {
    
    private Client client;

    /**
     * Sets up a {@link Client} instance before each test.
     * The {@code client} is initialized with valid default values to ensure consistent test state.
     */
    @BeforeEach
    public void setUp() {
        client = new Client("Maciek", "Szymon", "123456789", "good student");
    }
    
    /**
     * Tests the {@code setPhoneNumber} method to ensure it accepts valid phone numbers
     * without throwing a {@link ValidationException}.
     * 
     * @param phoneNumber a valid phone number
     */
    @ParameterizedTest
    @ValueSource(strings = {"123123123", "987654321", "555666777"}) 
    void validPhoneNumberTest(String phoneNumber) {
        try {
            client.setPhoneNumber(phoneNumber);
        } catch (ValidationException e) {
            fail("ValidationException should not be thrown for valid phone number: " + phoneNumber);
        }
    }

    /**
     * Tests the {@code setPhoneNumber} method to ensure it throws a {@link ValidationException}
     * when the provided phone number is invalid.
     * 
     * @param phoneNumber an invalid phone number
     */
    @ParameterizedTest
    @ValueSource(strings = {"1234sdfxzasd", "abcd1234", "", " ", "123-456-789", "123456"}) 
    void invalidPhoneNumberTest(String phoneNumber) {
        try {
            client.setPhoneNumber(phoneNumber);
            fail("ValidationException expected for invalid phone number: " + phoneNumber);
        } catch (ValidationException e) {
        }
    }
    
    /**
     * Tests the {@code setStudentName} method to ensure it throws a {@link ValidationException}
     * when the provided name is null, empty, or invalid.
     * 
     * @param text the student name to test
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"asd", "ds"}) 
    void nullEmptyClientNameTest(String text) {
        try {
            client.setStudentName(text);
            fail("ValidationException expected for invalid client name");
        } catch (ValidationException e) {
        }
    }
    
    /**
     * Tests the {@code setParentName} method to ensure it throws a {@link ValidationException}
     * when the provided name is null, empty, contains invalid characters, or starts with a lowercase letter.
     * 
     * @param text the parent name to test
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"lowercase", " leadingSpace", "\t", "\n", "1Invalid", "name"}) 
    void invalidParentNameTest(String text) {
        try {
            client.setParentName(text);
            fail("ValidationException expected for invalid parent name");
        } catch (ValidationException e) {
        }
    }

    /**
     * Tests the {@code setParentName} method to ensure it accepts valid parent names
     * and does not throw a {@link ValidationException}.
     * 
     * @param text a valid parent name
     */
    @ParameterizedTest
    @ValueSource(strings = {"ValidName", "AnotherValidName", "Correct"}) 
    void validParentNameTest(String text) {
        try {
            client.setParentName(text);
        } catch (ValidationException e) {
            fail("ValidationException should not be thrown for valid parent name");
        }
    }
}
