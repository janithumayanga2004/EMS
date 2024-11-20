package edu.home.estate.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashbordController implements Initializable {

    @FXML
    private AnchorPane dashbordAnchorPane;

    @FXML
    private AnchorPane content;

    @FXML
    private JFXButton estateId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/EstateForm.fxml");

    }


    @FXML
    void navigateToEstate(ActionEvent event) {
        navigateTo("/view/EstateForm.fxml");

    }


    @FXML
    void navigateDivision(ActionEvent event) {
        navigateTo("/view/DivisionForm.fxml");

    }


    @FXML
    void navegateToCategory(ActionEvent event) {
        navigateTo("/view/CategoryForm.fxml");

    }

    @FXML
    void navegateToLabour(ActionEvent event) {
        navigateTo("/view/LabourForm.fxml");

    }

    @FXML
    void navegateToAttendance(ActionEvent event) {
        navigateTo("/view/AttendanceForm.fxml");
    }

    @FXML
    void navigateToHavest(ActionEvent event) {
        navigateTo("/view/HavestForm.fxml");

    }


    @FXML
    void navigateToAgriculturalImplements(ActionEvent event) {
        navigateTo("/view/AgriculturalImplementsForm.fxml");

    }



    @FXML
    void navegateToExit(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SignINForm.fxml"));
        Parent load = fxmlLoader.load();

        dashbordAnchorPane.getChildren().clear();
        dashbordAnchorPane.getChildren().add(load);

    }



    public void navigateTo(String fxmlPath) {
        try{
            content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            content.getChildren().add(load);

        }catch (IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to load page").show();
        }
    }

}
