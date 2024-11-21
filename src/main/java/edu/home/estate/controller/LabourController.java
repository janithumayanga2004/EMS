package edu.home.estate.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.home.estate.dto.DivisionDto;
import edu.home.estate.dto.LabourDto;
import edu.home.estate.dto.tm.LabourTM;
import edu.home.estate.model.DivisionModel;
import edu.home.estate.model.LabourModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class LabourController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> cmbDivisionId;

    @FXML
    private TableColumn<LabourTM, Date> colDob;

    @FXML
    private TableColumn<LabourTM, String> colDivisionId;

    @FXML
    private TableColumn<LabourTM, String> colLabourAddress;

    @FXML
    private TableColumn<LabourTM, String> colLabourId;

    @FXML
    private TableColumn<LabourTM, String> colLabourName;

    @FXML
    private Label lblDivisionName;

    @FXML
    private Label lblLabourId;

    @FXML
    private TableView<LabourTM> tblLabour;

    @FXML
    private JFXTextField txtDob;

    @FXML
    private JFXTextField txtLabourAddress;

    @FXML
    private JFXTextField txtLabourName;

    private final ObservableList<LabourTM> labourTMS = FXCollections.observableArrayList();
    private final LabourModel labourModel = new LabourModel();
    private final DivisionModel divisionModel = new DivisionModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }

    }

    private void setCellValues() {
        colLabourId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLabourName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLabourAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colDivisionId.setCellValueFactory(new PropertyValueFactory<>("division_id"));

        tblLabour.setItems(labourTMS);
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        String nextLabourId = labourModel.getNextLabourId();
        lblLabourId.setText(nextLabourId);

        loadDivisionId();

        cmbDivisionId.getSelectionModel().clearSelection();


        labourTMS.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnReset.setDisable(true);

    }

    private void refreshTable() throws SQLException {
        ArrayList<LabourDto> labourDtos = labourModel.getAllLabours();
        ObservableList<LabourTM> labourTMS = FXCollections.observableArrayList();

        for (LabourDto labourDto : labourDtos) {
            LabourTM labourTM = new LabourTM(
                    labourDto.getId(),
                    labourDto.getName(),
                    labourDto.getAddress(),
                    labourDto.getDob(),
                    labourDto.getDivision_id()
            );
            labourTMS.add(labourTM);
        }
        tblLabour.setItems(labourTMS);
    }

    private void loadDivisionId() throws SQLException {
        ArrayList<String> divisionIds = divisionModel.getAllDivisionId();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(divisionIds);
        cmbDivisionId.setItems(observableList);
    }

    @FXML
    void SaveOnAction(ActionEvent event) throws ParseException, SQLException {
        String id = lblLabourId.getText();
        String name = txtLabourName.getText();
        String address = txtLabourAddress.getText();
        String dob = txtDob.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = formatter.parse(dob);

        String divisionId = cmbDivisionId.getSelectionModel().getSelectedItem();

        LabourDto labourDto = new LabourDto(id, name, address, dateOfBirth, divisionId);

        String namePattern =  namePattern = "^[A-Za-z ]+$";
        String addressPattern = addressPattern = "^[A-Za-z ]+$";

        boolean isValide = name.matches(namePattern) ;
        boolean isValide2 = address.matches(addressPattern);

        txtLabourName.setStyle(txtLabourAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtLabourAddress.setStyle(txtLabourAddress.getStyle() + ";-fx-border-color: #7367F0;");

        if(!isValide) {
            txtLabourName.setStyle(txtLabourAddress.getStyle() + ";-fx-border-color: red;");

        }

        if(!isValide2) {
            txtLabourAddress.setStyle(txtLabourAddress.getStyle() + ";-fx-border-color: red;");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to save this Labour?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            if (isValide && isValide2) {
                boolean isSaved = labourModel.saveLabour(labourDto);
                if (isSaved) {
                    refreshPage();
                    //new Alert(Alert.AlertType.INFORMATION, "Estate Saved...!").show();
                } else {

                    new Alert(Alert.AlertType.ERROR, "Failed to save Labour...!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please ensure all fields are valid.").show();
            }
        }

    }

    @FXML
    void cmbDivisionIdOnAction(ActionEvent event) throws SQLException {
        String selectedDivisionId = cmbDivisionId.getSelectionModel().getSelectedItem();
        DivisionDto divisionDto = divisionModel.getDivisionById(selectedDivisionId);

        if(divisionDto !=null) {
            lblDivisionName.setText(divisionDto.getName());

        }


    }

    @FXML
    void deleteOnAction(ActionEvent event) throws SQLException {
        String labourId = lblLabourId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this Labour?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            boolean isDelete = labourModel.deleteLabour(labourId);

            if(isDelete) {
                new Alert(Alert.AlertType.INFORMATION,"Labour deleted...!").show();
                refreshPage();

            }else{
                new Alert(Alert.AlertType.ERROR,"Fail to delete Labour...!").show();
            }

        }

    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void tblClicked(MouseEvent event) {
        LabourTM selectedLabourTM = tblLabour.getSelectionModel().getSelectedItem();
        if (selectedLabourTM != null) {
            lblLabourId.setText(selectedLabourTM.getId());
            txtLabourName.setText(selectedLabourTM.getName());
            txtLabourAddress.setText(selectedLabourTM.getAddress());
            txtDob.setText(selectedLabourTM.getDob().toString());
           // cmbDivisionId.setItems(selectedLabourTM.getDivision_id());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }

    }

    @FXML
    void updateOnAction(ActionEvent event) throws ParseException, SQLException {

        String id = lblLabourId.getText();
        String name = txtLabourName.getText();
        String address = txtLabourAddress.getText();
        String dob = txtDob.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = formatter.parse(dob);
        String divisionId = cmbDivisionId.getSelectionModel().getSelectedItem();

        LabourDto labourDto = new LabourDto(id, name, address, dateOfBirth, divisionId);

        String namePattern =  namePattern = "^[A-Za-z ]+$";
        String addressPattern = addressPattern = "^[A-Za-z ]+$";

        boolean isValide = name.matches(namePattern) ;
        boolean isValide2 = address.matches(addressPattern);

        txtLabourName.setStyle(txtLabourAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtLabourAddress.setStyle(txtLabourAddress.getStyle() + ";-fx-border-color: #7367F0;");

        if(!isValide) {
            txtLabourName.setStyle(txtLabourAddress.getStyle() + ";-fx-border-color: red;");

        }

        if(!isValide2) {
            txtLabourAddress.setStyle(txtLabourAddress.getStyle() + ";-fx-border-color: red;");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to update this Labour?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            if (isValide && isValide2) {
                boolean isUpdate = labourModel.updateLabour(labourDto);
                if (isUpdate) {
                    refreshPage();
                    //new Alert(Alert.AlertType.INFORMATION, "Estate Saved...!").show();
                } else {

                    new Alert(Alert.AlertType.ERROR, "Failed to update Labour...!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please ensure all fields are valid.").show();
            }
        }

    }

    @FXML
    void clearAddress(MouseEvent event) {
        txtLabourAddress.clear();

    }

    @FXML
    void clearDob(MouseEvent event) {
        txtDob.clear();

    }

    @FXML
    void clearName(MouseEvent event) {
        txtLabourName.clear();

    }

}
