package com.mycompany.taskmanager_gui;

import controller.AddClientViewController;
import controller.AddTaskViewController;
import controller.ClientViewController;
import controller.TaskViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

/**
 * Main application class for the Task Manager GUI.
 * This class is responsible for initializing and launching the JavaFX application,
 * setting up the main scene, and managing view transitions.
 * 
 * <p>Uses {@link User} as the primary data model shared across views.</p>
 * 
 * <p>Controllers managed in this application:</p>
 * <ul>
 *   <li>{@link ClientViewController} - Manages the client list view.</li>
 *   <li>{@link TaskViewController} - Manages the task list view.</li>
 *   <li>{@link AddClientViewController} - Manages adding a new client.</li>
 *   <li>{@link AddTaskViewController} - Manages adding a new task.</li>
 * </ul>
 * 
 * @version 1.0
 * @author Błażej Sztefka
 */
public class App extends Application {

    private static Scene scene;
    private static User user;

    /**
     * Starts the JavaFX application, setting the main view to "MainView.fxml".
     * 
     * @param stage the primary stage for this application
     * @throws IOException if loading the main FXML file fails
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainView"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Changes the root view of the application scene.
     * 
     * @param fxml the name of the FXML file (without extension) to load
     * @throws IOException if loading the specified FXML file fails
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads an FXML file and sets the appropriate controller.
     * The controller is selected based on the class type, allowing
     * the {@link User} model to be passed to controllers that require it.
     * 
     * @param fxml the name of the FXML file (without extension) to load
     * @return the root node of the loaded FXML
     * @throws IOException if loading the FXML file fails
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> {
            if (controllerClass == ClientViewController.class) {
                return new ClientViewController(user);
            } else if (controllerClass == TaskViewController.class) {
                return new TaskViewController(user);
            } else if (controllerClass == AddClientViewController.class) {
                return new AddClientViewController(user);
            } else if (controllerClass == AddTaskViewController.class) {
                return new AddTaskViewController(user);
            } else {
                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return fxmlLoader.load();
    }

    /**
     * The main entry point for launching the application.
     * Initializes the {@link User} model before starting the application.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        user = new User(); // Initialize the User model
        launch();
    }
}
