package edu.home.estate.controller;


import edu.home.estate.dto.UserDto;
import edu.home.estate.model.UserModel;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class SignUpController  {

    UserModel userModel = new UserModel();

    @FXML
    private AnchorPane SignUpAnchorpane;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblDirtOfBirth;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUserName;

    @FXML
    private ComboBox<String> cmbUser;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtDateOfBirth;


    @FXML
    void btnOnActionSignUp(ActionEvent event) throws IOException {
        try {
            saveUsers();
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public void redirectToLogine() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SignINForm.fxml"));
        Parent load = fxmlLoader.load();

        SignUpAnchorpane.getChildren().clear();
        SignUpAnchorpane.getChildren().add(load);


    }
    public void saveUsers() throws SQLException, ParseException, IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String userName = txtUserName.getText();
        String address = txtAddress.getText();
        String dateOfBirthStr = txtDateOfBirth.getText();


       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       Date dateOfBirth = formatter.parse(dateOfBirthStr);

       String password = txtPassword.getText();

        UserDto userDto = new UserDto(firstName, lastName, email, userName, address, dateOfBirth, password);

        boolean isSaved = userModel.saveUsers(userDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "User saved successfully!").show();
            redirectToLogine();

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save user!").show();
        }
    }








    }

