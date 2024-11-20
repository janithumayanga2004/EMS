module edu.home.estate {
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires com.jfoenix;
    requires javafx.controls;
    requires javafx.base;
    requires java.desktop;

    opens edu.home.estate.dto.tm to javafx.base;
    opens edu.home.estate.controller to javafx.fxml;
    exports edu.home.estate;
}