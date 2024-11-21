package edu.home.estate.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashbordController implements Initializable {

    @FXML
    private AnchorPane dashbordAnchorPane;


//    @FXML
//    private Label lblTbaleName;

    @FXML
    private AnchorPane content;

    @FXML
    private JFXButton estateId;

    @FXML
    private Label lblDateTime;

    @FXML
    private JFXButton btnAssets;

    @FXML
    private JFXButton btnAttendance;

    @FXML
    private JFXButton btnCategory;

    @FXML
    private JFXButton btnDivision;

    @FXML
    private JFXButton btnEstate;

    @FXML
    private JFXButton btnHarvest;

    @FXML
    private JFXButton btnLabour;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/dashbordForm.fxml");
//        lblTbaleName.setText("Estate");
        initializeDateTime();
    }


    private void initializeDateTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd | HH:mm:ss");
            lblDateTime.setText(LocalDateTime.now().format(formatter));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }



    @FXML
    void navigateToEstate(ActionEvent event) {
        this.setButtonDefaultColor();
        btnEstate.setStyle("-fx-background-color: #001a09; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        navigateTo("/view/EstateForm.fxml");
    }

    @FXML
    void navigateDivision(ActionEvent event) {
        this.setButtonDefaultColor();
        btnDivision.setStyle("-fx-background-color: #001a09; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        navigateTo("/view/DivisionForm.fxml");
    }

    @FXML
    void navegateToCategory(ActionEvent event) {
        this.setButtonDefaultColor();
        btnCategory.setStyle("-fx-background-color: #001a09; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        navigateTo("/view/CategoryForm.fxml");
    }

    @FXML
    void navegateToLabour(ActionEvent event) {
        this.setButtonDefaultColor();
        btnLabour.setStyle("-fx-background-color: #001a09; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        navigateTo("/view/LabourForm.fxml");
    }

    @FXML
    void navegateToAttendance(ActionEvent event) {
        this.setButtonDefaultColor();
        btnAttendance.setStyle("-fx-background-color: #001a09; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        navigateTo("/view/AttendanceForm.fxml");
    }

    @FXML
    void navigateToHavest(ActionEvent event) {
        this.setButtonDefaultColor();
        btnHarvest.setStyle("-fx-background-color: #001a09; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        navigateTo("/view/HavestForm.fxml");
    }

    @FXML
    void navigateToAgriculturalImplements(ActionEvent event) {
        this.setButtonDefaultColor();
        btnAssets.setStyle("-fx-background-color: #001a09; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        navigateTo("/view/AgriculturalImplementsForm.fxml");
    }

    @FXML
    void navigateToDashbord(ActionEvent event) {
        this.setButtonDefaultColor();
        navigateTo("/view/dashbordForm.fxml");

    }

    @FXML
    void navegateToExit(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to Exit this System?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SignINForm.fxml"));
            Parent load = fxmlLoader.load();

            dashbordAnchorPane.getChildren().clear();
            dashbordAnchorPane.getChildren().add(load);
        }
    }

    public void navigateTo(String fxmlPath) {
        try {
            content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            content.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page").show();
        }
    }

    private void setButtonDefaultColor() {
        btnEstate.setStyle("-fx-background-color: #004d1a; -fx-text-fill: white; -fx-border-color: white;-fx-border-radius: 5px");
        btnDivision.setStyle("-fx-background-color: #004d1a; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        btnCategory.setStyle("-fx-background-color: #004d1a; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        btnLabour.setStyle("-fx-background-color: #004d1a; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        btnAttendance.setStyle("-fx-background-color: #004d1a; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        btnHarvest.setStyle("-fx-background-color: #004d1a; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
        btnAssets.setStyle("-fx-background-color: #004d1a; -fx-text-fill: white;-fx-border-color: white;-fx-border-radius: 5px");
    }


}


