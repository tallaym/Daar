module com.daar.ui {

    requires com.daar.core;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.dlsc.formsfx;

    opens com.daar.controller to javafx.fxml;
    opens com.daar.controller.boot to javafx.fxml;
    opens com.daar.controller.dashboard to javafx.fxml;
    opens com.daar.controller.dashboard.staff to javafx.fxml;
    opens com.daar.controller.dashboard.estate to javafx.fxml;
    opens com.daar.controller.dashboard.staff.role to javafx.fxml;
    opens com.daar.controller.dashboard.staff.employee to javafx.fxml;
    opens com.daar.controller.dashboard.staff.permission to javafx.fxml;
}