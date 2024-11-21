package edu.home.estate.controller;
import edu.home.estate.dto.UserDto;
import edu.home.estate.model.UserModel;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class SignInController {


    @FXML
    private AnchorPane SignInAnchorPane;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    void btnOnActionSignIn(ActionEvent event) throws SQLException, IOException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        List<UserDto> authenticatedUsers = UserModel.checkUsers(email, password);

        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isvaliedEmail = email.matches(emailPattern);
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");

        if(!isvaliedEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if(!authenticatedUsers.isEmpty()) {
            System.out.println("true");
           try{
               loadDashboard();

           } catch (IOException e) {
               throw new RuntimeException(e);
           }


        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email or password.");
            alert.showAndWait();

           txtEmail.clear();
           txtPassword.clear();

        }


    }

   public void loadDashboard( ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
        Parent load = fxmlLoader.load();

        SignInAnchorPane.getChildren().clear();
        SignInAnchorPane.getChildren().add(load);

   }

    @FXML
    void btnOnActionSignUp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SignUPForm.fxml"));
        Parent load = fxmlLoader.load();

        SignInAnchorPane.getChildren().clear();
        SignInAnchorPane.getChildren().add(load);

    }

    @FXML
    void btnOnActionForgot(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ForgetPasswordForm.fxml"));
        Parent load = fxmlLoader.load();

        SignInAnchorPane.getChildren().clear();
        SignInAnchorPane.getChildren().add(load);

    }


}
