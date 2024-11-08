/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import com.mycompany.taskmanager_gui.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

/**
 * MainViewController handles navigation to client and task tables, 
 * and provides an option to exit the application.
 * 
 * @version 1.1
 * @author Błażej Sztefka
 */
public class MainViewController {

    @FXML
    private Button clientButton;

    @FXML
    private Button taskButton;

    @FXML
    private Button exitButton;

    /**
     * Initializes the main view controller, setting up tooltips for each button.
     */
    @FXML
    public void initialize() {
        setupTooltips();
    }

    /**
     * Configures tooltips for navigation and action buttons.
     */
    private void setupTooltips() {
        Tooltip clientToolTip = new Tooltip("Show client table");
        clientButton.setTooltip(clientToolTip);
        
        Tooltip taskToolTip = new Tooltip("Show task table");
        taskButton.setTooltip(taskToolTip);
        
        Tooltip exitToolTip = new Tooltip("Exit the application");
        exitButton.setTooltip(exitToolTip);
    }

    /**
     * Switches to the ClientView when clientButton is clicked.
     * 
     * @param event the action event triggering the switch
     * @throws IOException if the ClientView cannot be loaded
     */
    @FXML
    private void swichToClientList(ActionEvent event) throws IOException {
        App.setRoot("ClientView");
    }

    /**
     * Switches to the TaskView when taskButton is clicked.
     * 
     * @param event the action event triggering the switch
     * @throws IOException if the TaskView cannot be loaded
     */
    @FXML
    private void swichToTaskList(ActionEvent event) throws IOException {
        App.setRoot("TaskView");
    }

    /**
     * Exits the application when exitButton is clicked.
     * 
     * @param event the action event triggering the exit
     */
    @FXML
    private void Exit(ActionEvent event) {
        System.exit(0);
    }
}
