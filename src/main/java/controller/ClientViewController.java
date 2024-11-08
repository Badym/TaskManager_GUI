package controller;

import com.mycompany.taskmanager_gui.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Client;
import model.User;
import model.ValidationException;

/**
 * Controller for ClientView. Manages the UI and logic for displaying,
 * adding, editing, and removing clients.
 * 
 * @version 1.1 - Enhanced validation and tooltip functionality
 * @author Błażej Sztefka
 */
public class ClientViewController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button backButton;

    @FXML
    private TableView<Client> table;
    @FXML
    private TableColumn<Client, Integer> clientId;
    @FXML
    private TableColumn<Client, String> studentName;
    @FXML
    private TableColumn<Client, String> parentName;
    @FXML
    private TableColumn<Client, String> phoneNumber;
    @FXML
    private TableColumn<Client, String> description;

    private final ObservableList<Client> data;
    private final User user;

    /**
     * Constructor for ClientViewController.
     * 
     * @param user the current user associated with the session
     */
    public ClientViewController(User user) {
        this.user = user;
        this.data = FXCollections.observableArrayList(user.getClientList());
    }

    /**
     * Initializes the controller class.
     * Sets up tooltips for buttons, table column bindings, and edit handlers.
     * 
     * @param url the location used to resolve relative paths
     * @param rb the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTooltips();
        bindTableColumns();
        setupTableEditingHandlers();
    }

    /**
     * Configures tooltips for all action buttons.
     */
    private void setupTooltips() {
        addButton.setTooltip(new Tooltip("Add a new client"));
        removeButton.setTooltip(new Tooltip("Remove the selected client"));
        backButton.setTooltip(new Tooltip("Return to the main menu"));
    }

    /**
     * Binds table columns to Client properties and initializes the table with data.
     */
    private void bindTableColumns() {
        table.setItems(data);
        clientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        parentName.setCellValueFactory(new PropertyValueFactory<>("parentName"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        // Set the table as editable and configure cell factories for in-line editing.
        table.setEditable(true);
        studentName.setCellFactory(TextFieldTableCell.forTableColumn());
        parentName.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    /**
     * Sets up event handlers for in-line editing of table columns with validation checks.
     */
    private void setupTableEditingHandlers() {
        studentName.setOnEditCommit(this::handleStudentNameEdit);
        parentName.setOnEditCommit(this::handleParentNameEdit);
        phoneNumber.setOnEditCommit(this::handlePhoneNumberEdit);
        description.setOnEditCommit(this::handleDescriptionEdit);
    }

    /**
     * Handles in-line editing of the student name with validation checks.
     * 
     * @param t the edit event
     */
    private void handleStudentNameEdit(TableColumn.CellEditEvent<Client, String> t) {
        String newValue = t.getNewValue();
        if (newValue == null || newValue.trim().isEmpty()) {
            showAlert("Validation Error", "Student name cannot be empty.");
            t.getTableView().refresh();
        } else if (!Character.isUpperCase(newValue.charAt(0))) {
            showAlert("Validation Error", "Student name must start with a capital letter.");
            t.getTableView().refresh();
        } else {
            t.getRowValue().setStudentName(newValue);
        }
    }

    /**
     * Handles in-line editing of the parent name with validation checks.
     * 
     * @param t the edit event
     */
    private void handleParentNameEdit(TableColumn.CellEditEvent<Client, String> t) {
        String newValue = t.getNewValue();
        if (!Character.isUpperCase(newValue.charAt(0))) {
            showAlert("Validation Error", "Parent name must start with a capital letter.");
            t.getTableView().refresh();
        } else {
            t.getRowValue().setParentName(newValue);
        }
    }

    /**
     * Handles in-line editing of the phone number with validation checks.
     * 
     * @param t the edit event
     */
    private void handlePhoneNumberEdit(TableColumn.CellEditEvent<Client, String> t) {
        try {
            t.getRowValue().setPhoneNumber(t.getNewValue());
        } catch (ValidationException e) {
            showAlert("Validation Error", e.getMessage());
            t.getTableView().refresh();
        }
    }

    /**
     * Handles in-line editing of the description field.
     * 
     * @param t the edit event
     */
    private void handleDescriptionEdit(TableColumn.CellEditEvent<Client, String> t) {
        t.getRowValue().setDescription(t.getNewValue());
    }

    /**
     * Displays an alert dialog with the given title and content message.
     * 
     * @param title the title of the alert dialog
     * @param content the message to display in the alert dialog
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Navigates to the AddClientView to add a new client.
     * 
     * @param event the action event triggering the addition
     * @throws IOException if an error occurs while loading the view
     */
    @FXML
    private void addClient(ActionEvent event) throws IOException {
        App.setRoot("AddClientView");
    }

    /**
     * Removes the selected client from the table and user data with error handling.
     * 
     * @param event the action event triggering the removal
     */
    @FXML
    private void removeClient(ActionEvent event) {
        Client selectedClient = table.getSelectionModel().getSelectedItem();
        int index = table.getSelectionModel().getSelectedIndex();
        if (selectedClient != null && index >= 0) {
            try {
                this.user.removeClient(index + 1);
                data.remove(index);
            } catch (ValidationException e) {
                showAlert("Error", "No student selected for removal.");
            }
        }
    }

    /**
     * Returns to the main menu view.
     * 
     * @param event the action event triggering the return
     * @throws IOException if an error occurs while switching views
     */
    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        App.setRoot("MainView");
    }
}
