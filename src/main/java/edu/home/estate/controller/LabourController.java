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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        txtLabourName.setText("");
        txtLabourAddress.setText("");
        txtDob.setText("");
        lblDivisionName.setText("");

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

        boolean isSaved = labourModel.saveLabour(labourDto);

        if (isSaved) {
            System.out.println("Labour saved");
            refreshPage();

        }else{
            System.out.println("Labour not saved");
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

        boolean isDelete = labourModel.deleteLabour(labourId);

        if (isDelete) {
            System.out.println("Labour deleted");
            refreshPage();

        }else{
            System.out.println("Labour not deleted");
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

        boolean isUpdate = labourModel.updateLabour(labourDto);

        if (isUpdate) {
            System.out.println("Labour updated");
            refreshPage();

        }else{
            System.out.println("Labour not updated");
        }

    }

}
