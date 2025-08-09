module com.daar.ui {

    requires com.daar.core;
    requires javafx.controls;
    requires javafx.fxml;
    opens com.daar.controller to javafx.fxml;
    opens com.daar.controller.boot to javafx.fxml;
    opens com.daar.controller.dashboard to javafx.fxml;
}