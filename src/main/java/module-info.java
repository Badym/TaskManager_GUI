module com.mycompany.taskmanager_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires lombok;

    opens com.mycompany.taskmanager_gui to javafx.fxml;
    opens controller to javafx.fxml;
    opens model to javafx.base;
    exports com.mycompany.taskmanager_gui;
    exports controller;
    exports model;
}
