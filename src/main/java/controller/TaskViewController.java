package controller;

import com.mycompany.taskmanager_gui.App;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import model.Task;
import model.TaskStatus;
import model.User;
import model.ValidationException;

/**
 * TaskViewController class manages the user interface for viewing, editing,
 * adding, and removing tasks.
 * It connects with the Task model and displays the task details in a TableView.
 * 
 * @version 1.1
 * @author Błażej Sztefka
 */
public class TaskViewController implements Initializable {

    @FXML
    private Button addTaskButton;

    @FXML
    private Button removeTaskButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<Task> table;
    @FXML
    private TableColumn<Task, Integer> taskId;
    @FXML
    private TableColumn<Task, String> subject;
    @FXML
    private TableColumn<Task, String> description;
    @FXML
    private TableColumn<Task, Integer> clientId;
    @FXML
    private TableColumn<Task, String> date;
    @FXML
    private TableColumn<Task, String> time;
    
    @FXML
    private Button showDueSoonButton;
    @FXML
    private Button showDueThisWeekButton;
    @FXML
    private Button showLongTermButton;

    private final ObservableList<Task> data;
    private final User user;

    /**
     * Constructor initializes the controller with the given user.
     * @param user The user whose task list will be displayed and managed.
     */
    public TaskViewController(User user) {
        this.user = user;
        this.data = FXCollections.observableArrayList(user.getTaskList());
    }

    /**
     * Initializes the controller, setting up tooltips, table columns, and
     * enabling inline editing for task properties.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        showDueSoonButton.setOnAction(event -> displayFilteredTasks(TaskStatus.DUE_SOON));
        showDueThisWeekButton.setOnAction(event -> displayFilteredTasks(TaskStatus.DUE_THIS_WEEK));
        showLongTermButton.setOnAction(event -> displayFilteredTasks(TaskStatus.LONG_TERM));

        // Set tooltips for buttons
        addTaskButton.setTooltip(new Tooltip("Add new task"));
        removeTaskButton.setTooltip(new Tooltip("Remove selected task"));
        backButton.setTooltip(new Tooltip("Back to menu"));

        // Set up the TableView with the data from the user's task list
        table.setItems(data);

        // Define how to populate each column with data
        taskId.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        clientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateS"));
        time.setCellValueFactory(new PropertyValueFactory<>("timeS"));

        // Enable inline editing for each column
        table.setEditable(true);
        subject.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        clientId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        date.setCellFactory(TextFieldTableCell.forTableColumn());
        time.setCellFactory(TextFieldTableCell.forTableColumn());

        // Editing behavior for Subject column with validation
        subject.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            if (newValue == null || newValue.trim().isEmpty()) {
                showAlert("Validation Error", "Subject name cannot be empty.");
                event.getTableView().refresh(); // Reset to previous value if invalid
            } else {
                event.getRowValue().setSubject(newValue); // Update model with new value
            }
        });

        // Editing behavior for Description column with validation
        description.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            if (newValue == null || newValue.trim().isEmpty()) {
                showAlert("Validation Error", "Description cannot be empty.");
                event.getTableView().refresh();
            } else {
                event.getRowValue().setDescription(newValue);
            }
        });

        // Editing behavior for Client ID column with validation
        clientId.setOnEditCommit(event -> {
            int newValue = event.getNewValue();
            if (newValue > user.getTaskList().size() || newValue < 0) {
                showAlert("Validation Error", "Invalid client ID.");
                event.getTableView().refresh();
            } else {
                event.getRowValue().setClientId(newValue);
            }
        });

        // Editing behavior for Date column with validation
        date.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            try {
                DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(newValue); // Validate date format
                event.getRowValue().setDateS(newValue);
            } catch (DateTimeParseException e) {
                showAlert("Validation Error", "Invalid date format. Correct format is YYYY-MM-DD.");
                event.getTableView().refresh();
            }
        });

        // Editing behavior for Time column with validation
        time.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            try {
                DateTimeFormatter.ofPattern("HH:mm").parse(newValue); // Validate time format
                event.getRowValue().setTimeS(newValue);
            } catch (DateTimeParseException e) {
                showAlert("Validation Error", "Invalid time format. Correct format is HH:mm.");
                event.getTableView().refresh();
            }
        });
    }
    
    /**
    * Filtrowanie i wyświetlanie zadań o określonym statusie w ListView.
    * @param status Status zadań, które mają być wyświetlone
    */
   private void displayFilteredTasks(TaskStatus status) {
       // Filtrowanie zadań przy użyciu strumienia
       List<Task> filteredTasks = user.getTaskList().stream()
               .filter(task -> task.getStatus() == status) // filtrujemy według statusu
               .collect(Collectors.toList()); // Zbieramy zadania do listy Tasków

       // Wyczyszczenie ListView i dodanie przefiltrowanych zadań
       data.clear(); // Czyszczenie ListView przed dodaniem nowych zadań
       data.addAll(filteredTasks); // Dodanie przefiltrowanych zadań do ListView
   }

    /**
     * Shows an alert dialog with the specified title and content.
     * @param title The title of the alert dialog.
     * @param content The content message of the alert dialog.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Opens the AddTaskView for creating a new task.
     * @param event The event triggered by clicking the Add Task button.
     */
    @FXML
    private void addTask(ActionEvent event) throws IOException {
        App.setRoot("AddTaskView");
    }

    /**
     * Removes the selected task from the user's task list, if a task is selected.
     * Displays an alert if no task is selected or removal fails.
     * @param event The event triggered by clicking the Remove Task button.
     */
    @FXML
    private void removeTask(ActionEvent event) {
        Task selectedTask = table.getSelectionModel().getSelectedItem();
        int index = table.getSelectionModel().getSelectedIndex();

        if (selectedTask != null && index >= 0) {
            try {
                user.removeTask(index + 1); // Remove task from model (1-based index)
                data.remove(index);         // Remove task from view (0-based index)
            } catch (ValidationException e) {
                showAlert("Validation Error", "Failed to remove task: " + e.getMessage());
            }
        } else {
            showAlert("Error", "No task selected for removal.");
        }
    }

    /**
     * Returns the user to the main menu view.
     * @param event The event triggered by clicking the Back button.
     * @throws IOException if the main view fails to load.
     */
    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        App.setRoot("MainView");
    }
}
