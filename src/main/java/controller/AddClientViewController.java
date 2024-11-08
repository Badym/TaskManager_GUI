/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import com.mycompany.taskmanager_gui.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import model.Client;
import model.User;
import model.ValidationException;

/**
 * Controller class for the Add Client View.
 * Handles client creation and form field validation.
 * Allows navigation back to the client table view.
 * 
 * @version 1.0
 * @author Błażej Sztefka
 */
public class AddClientViewController implements Initializable {

    @FXML
    private TextField newParentName;
    @FXML
    private TextArea newDescription;
    @FXML
    private TextField newClientName;
    @FXML
    private TextField newPhoneNumber;
    @FXML
    private Button addNewClientButton;
    @FXML
    private Button backToClientTableButton;

    private final User user;

    /**
     * Constructor to initialize the controller with a specific user instance.
     * 
     * @param user the user to associate new clients with
     */
    public AddClientViewController(User user) {
        this.user = user;
    }

    /**
     * Initializes the controller class and sets up tooltips and keyboard navigation.
     * 
     * @param url the URL used to resolve paths for the FXML file
     * @param rb the resource bundle for localization support
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Tooltip setup for input fields and buttons
        newClientName.setTooltip(new Tooltip("Enter client name"));
        newParentName.setTooltip(new Tooltip("Enter parent's name"));
        newPhoneNumber.setTooltip(new Tooltip("Enter phone number (9 digits)"));
        newDescription.setTooltip(new Tooltip("Enter client description"));
        addNewClientButton.setTooltip(new Tooltip("Add client"));
        backToClientTableButton.setTooltip(new Tooltip("Return to client table"));

        // Setup keyboard navigation
        setUpKeyboardNavigation();
    }

    /**
     * Sets up custom keyboard navigation to handle TAB and ENTER key events.
     * Manages focus between fields and triggers client addition with ENTER key.
     */
    private void setUpKeyboardNavigation() {
        newClientName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                newParentName.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                addNewClientButton.fire();
            }
        });

        newParentName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                newPhoneNumber.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                addNewClientButton.fire();
            }
        });

        newPhoneNumber.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                newDescription.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                addNewClientButton.fire();
            }
        });

        newDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                addNewClientButton.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                addNewClientButton.fire();
            }
        });

        addNewClientButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                backToClientTableButton.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                addNewClientButton.fire();
            }
        });

        backToClientTableButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                newClientName.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                backToClientTableButton.fire();
            }
        });
    }

    /**
     * Handles the action of adding a new client.
     * Validates form fields and creates a new client instance if all inputs are valid.
     * 
     * @param event the ActionEvent triggered by pressing the "Add Client" button
     * @throws IOException if an error occurs when loading the next view
     */
    @FXML
    private void addNewClient(ActionEvent event) throws IOException {
        // Retrieve and validate client name
        String clientName = newClientName.getText();
        if (clientName == null || clientName.trim().isEmpty()) {
            showAlert("Validation Error", "Client name cannot be empty.");
            newClientName.clear();
            return;
        } else if (!Character.isUpperCase(clientName.charAt(0))) {
            showAlert("Validation Error", "Client name must start with a capital letter.");
            newClientName.clear();
            return;
        }

        // Retrieve and validate parent name
        String parentName = newParentName.getText();
        if (parentName == null || parentName.trim().isEmpty()) {
            showAlert("Validation Error", "Parent name cannot be empty.");
            newParentName.clear();
            return;
        } else if (!Character.isUpperCase(parentName.charAt(0))) {
            showAlert("Validation Error", "Parent name must start with a capital letter.");
            newParentName.clear();
            return;
        }

        // Retrieve and validate phone number
        String phoneNumber = newPhoneNumber.getText();
        if (phoneNumber != null && !phoneNumber.matches("\\d{9}")) {
            showAlert("Validation Error", "Phone number must be 9 digits.");
            newPhoneNumber.clear();
            return;
        }

        // Retrieve description
        String description = newDescription.getText();

        // Create and add new client
        Client newClient = new Client(clientName, parentName, phoneNumber, description);
        user.addClient(newClient);

        // Navigate back to Client View
        App.setRoot("ClientView");
    }

    /**
     * Displays an alert dialog with a specified title and message content.
     * 
     * @param title the title of the alert dialog
     * @param content the message content of the alert dialog
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Handles navigation back to the client table view.
     * 
     * @param event the ActionEvent triggered by pressing the "Back" button
     * @throws IOException if an error occurs when loading the client table view
     */
    @FXML
    private void backToClientTable(ActionEvent event) throws IOException {
        App.setRoot("ClientView");
    }
}
