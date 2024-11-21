module edu.home.estate {
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires com.jfoenix;
    requires javafx.controls;
    requires java.desktop;
    requires javafx.media;
    requires java.mail;
    requires net.sf.jasperreports.core;

    opens edu.home.estate.dto.tm to javafx.base;
    opens edu.home.estate.controller to javafx.fxml;
    exports edu.home.estate;
}