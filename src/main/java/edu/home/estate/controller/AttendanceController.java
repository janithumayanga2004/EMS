package edu.home.estate.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.home.estate.dto.AttendanceDto;
import edu.home.estate.dto.LabourDto;
import edu.home.estate.dto.tm.AttendanceTM;
import edu.home.estate.model.AttendanceModel;
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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class AttendanceController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpDate;

    @FXML
    private JFXComboBox<String> cmbLabourId;

    @FXML
    private TableColumn<AttendanceTM, String> colAttendance;

    @FXML
    private TableColumn<AttendanceTM, Date> colAttendanceDate;

    @FXML
    private TableColumn<AttendanceTM, String> colAttendanceId;

    @FXML
    private TableColumn<AttendanceTM, Time> colAttendanceTime;


    @FXML
    private TableColumn<AttendanceTM, String> colLabourId;

    @FXML
    private JFXTextField txtAttendance;

    @FXML
    private Label lblAttendanceId;

    @FXML
    private Label lblLabourName;

    @FXML
    private TableView<AttendanceTM> tblAttendance;

    @FXML
    private JFXTextField txtAttendanceDate;

    @FXML
    private JFXTextField txtAttendanceTime;

    private final AttendanceModel attendanceModel = new AttendanceModel();
    private final ObservableList<AttendanceTM> attendanceTMS = FXCollections.observableArrayList();
    private final LabourModel labourModel = new LabourModel();
    

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
        colAttendanceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));
        colAttendanceDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAttendanceTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colLabourId.setCellValueFactory(new PropertyValueFactory<>("labour_id"));

        tblAttendance.setItems(attendanceTMS);
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        loadLabourId();
        String attendanceId = attendanceModel.getNextAttendanceId();
        lblAttendanceId.setText(attendanceId);


        cmbLabourId.getSelectionModel().clearSelection();
        txtAttendance.setText("");
        txtAttendanceDate.setText("");
        txtAttendanceTime.setText("");
        lblLabourName.setText("");

        attendanceTMS.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnReset.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<AttendanceDto> attendanceDtos = attendanceModel.getallAttendance();
        ObservableList<AttendanceTM> attendanceTMS = FXCollections.observableArrayList();

        for (AttendanceDto attendanceDto : attendanceDtos) {
            AttendanceTM attendanceTM = new AttendanceTM(
                    attendanceDto.getId(),
                    attendanceDto.getAttendance(),
                    attendanceDto.getDate(),
                    attendanceDto.getTime(),
                    attendanceDto.getLabour_id()
            );
            attendanceTMS.add(attendanceTM);
        }
        tblAttendance.setItems(attendanceTMS);
    }

    private void loadLabourId() throws SQLException {
        ArrayList<String> labourIds = labourModel.getAllLabourIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(labourIds);
        cmbLabourId.setItems(observableList);
    }

    @FXML
    void cmbLabourIdOnAction(ActionEvent event) throws SQLException {
        String selectedLabourId = cmbLabourId.getSelectionModel().getSelectedItem();
        LabourDto labourDto = labourModel.getLabour(selectedLabourId);

        if (labourDto != null) {
            lblLabourName.setText(labourDto.getName());
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        int attendanceId = Integer.parseInt(lblAttendanceId.getText());

        boolean isDelete = attendanceModel.deleteAttendance(attendanceId);

        if (isDelete) {
            System.out.println("delete success");
            refreshPage();

        }else{
            System.out.println("delete fail");
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws ParseException, SQLException {
        // Extract input values from UI components
        String id = lblAttendanceId.getText();
        String attendance = txtAttendance.getText();
        String date = txtAttendanceDate.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateAttendance = formatter.parse(date);
        String time = txtAttendanceTime.getText();
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
        Date dateTimeAttendance = formatterTime.parse(time);
        String labourId = cmbLabourId.getSelectionModel().getSelectedItem();

        Time sqlTime = new Time(dateTimeAttendance.getTime());


        AttendanceDto attendanceDto = new AttendanceDto(id, attendance, dateAttendance, sqlTime, labourId);

        AttendanceModel attendanceModel = new AttendanceModel();
        boolean isSaved = attendanceModel.saveAttendance(attendanceDto);


        if (isSaved) {
            System.out.println("Attendance saved");
            refreshPage();
        } else {
            System.out.println("Attendance not saved");
        }
    }


    @FXML
    public void btnUpDateOnAction(ActionEvent event) throws ParseException, SQLException {
        // Extract inputs
        String id = lblAttendanceId.getText();
        String attendance = txtAttendance.getText();
        String date = txtAttendanceDate.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateAttendance = formatter.parse(date);
        String time = txtAttendanceTime.getText();
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
        Date timeAttendance = formatterTime.parse(time);
        String labourId = cmbLabourId.getSelectionModel().getSelectedItem();
        Time sqlTime = new Time(timeAttendance.getTime());


        AttendanceDto attendanceDto = new AttendanceDto(id, attendance, dateAttendance, sqlTime, labourId);


        AttendanceModel attendanceModel = new AttendanceModel();
        boolean isUpdate = attendanceModel.updateAttendance(attendanceDto);


        if (isUpdate) {
            System.out.println("Update success");
            refreshPage();
        } else {
            System.out.println("Update failed");
        }
    }

    @FXML
    void tblClicked(MouseEvent event) {
        AttendanceTM selectedAttendanceTM = tblAttendance.getSelectionModel().getSelectedItem();
        if (selectedAttendanceTM != null) {
            lblAttendanceId.setText(String.valueOf(selectedAttendanceTM.getId()));
            txtAttendance.setText(selectedAttendanceTM.getAttendance());
            txtAttendanceDate.setText(String.valueOf(selectedAttendanceTM.getDate()));
            txtAttendanceTime.setText(String.valueOf(selectedAttendanceTM.getTime()));
            cmbLabourId.getSelectionModel().clearSelection();
            cmbLabourId.getSelectionModel().select(selectedAttendanceTM.getLabour_id().toString());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpDate.setDisable(false);
        }

    }

}
