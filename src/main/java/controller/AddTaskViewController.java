/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import com.mycompany.taskmanager_gui.App;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import model.Client;
import model.Task;
import model.User;

/**
 * Controller class for the Add Task View.
 * Manages the creation of tasks, form validation, and navigation back to the task table view.
 * 
 * @version 1.0
 * @author Błażej Sztefka
 */
public class AddTaskViewController implements Initializable {

    @FXML
    private TextArea newDescription;
    @FXML
    private TextField newSubject;
    @FXML
    private TextField newTime;
    @FXML
    private Button addNewTaskButton;
    @FXML
    private Button backToTaskTableButton;
    @FXML
    private DatePicker newDate;
    @FXML
    private ComboBox<Client> newIdClient;

    private final User user;

    /**
     * Constructs the controller with a specific user instance.
     * 
     * @param user the user to associate new tasks with
     */
    public AddTaskViewController(User user) {
        this.user = user;
    }

    /**
     * Initializes the controller class and sets up keyboard navigation and client combo box.
     * 
     * @param url the URL used to resolve paths for the FXML file
     * @param rb the resource bundle for localization support
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpKeyboardNavigation();
        populateClientComboBox();
    }

    /**
     * Sets up custom keyboard navigation to handle TAB and ENTER key events.
     * Manages focus between fields and triggers task addition with ENTER key.
     */
    private void setUpKeyboardNavigation() {
        newSubject.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                newDate.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                addNewTaskButton.fire();
            }
        });

        newDate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                newIdClient.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                addNewTaskButton.fire();
            }
        });

        newTime.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                newDescription.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                addNewTaskButton.fire();
            }
        });

        newDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                addNewTaskButton.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                addNewTaskButton.fire();
            }
        });

        newIdClient.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                newTime.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                addNewTaskButton.fire();
            }
        });

        addNewTaskButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                backToTaskTableButton.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                addNewTaskButton.fire();
            }
        });

        backToTaskTableButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                newSubject.requestFocus();
            } else if (event.getCode() == KeyCode.ENTER) {
                backToTaskTableButton.fire();
            }
        });
    }

    /**
     * Populates the client ComboBox with clients from the user object.
     * Formats the display of client ID and name.
     */
    private void populateClientComboBox() {
        List<Client> clients = user.getClientList(); 

        for (Client client : clients) {
            newIdClient.getItems().add(client);
        }

        newIdClient.setCellFactory(param -> new ListCell<Client>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getClientId() + ". " + item.getStudentName());
                }
            }
        });

        newIdClient.setConverter(new StringConverter<Client>() {
            @Override
            public String toString(Client client) {
                return (client != null) ? client.getClientId() + ". " + client.getStudentName() : "";
            }

            @Override
            public Client fromString(String string) {
                return null; // Not required for ComboBox usage
            }
        });
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
     * Handles the action of adding a new task.
     * Validates form fields and creates a new task instance if all inputs are valid.
     * 
     * @param event the ActionEvent triggered by pressing the "Add Task" button
     * @throws IOException if an error occurs when loading the next view
     */
    @FXML
    private void addNewTask(ActionEvent event) throws IOException {
        String taskSubject = newSubject.getText();
        if (taskSubject == null || taskSubject.trim().isEmpty()) {
            showAlert("Validation Error", "Subject cannot be empty.");
            newSubject.clear();
            return;
        }

        String description = newDescription.getText();
        LocalDate selectedDate = newDate.getValue();
        if (selectedDate == null) {
            showAlert("Validation Error", "Date cannot be empty.");
            return;
        }

        String timeString = newTime.getText();
        LocalTime time;
        try {
            time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            showAlert("Validation Error", "Time must be in HH:mm format.");
            newTime.clear();
            return;
        }

        Client selectedClient = newIdClient.getValue();
        if (selectedClient == null) {
            showAlert("Validation Error", "Please select a client.");
            return;
        }

        Task newTask = new Task(taskSubject, description, selectedClient.getClientId(), selectedDate, time);
        user.addTask(newTask);

        App.setRoot("TaskView");
    }

    /**
     * Handles navigation back to the task table view.
     * 
     * @param event the ActionEvent triggered by pressing the "Back" button
     * @throws IOException if an error occurs when loading the task table view
     */
    @FXML
    private void backToTaskTable(ActionEvent event) throws IOException {
        App.setRoot("TaskView");
    }
}
