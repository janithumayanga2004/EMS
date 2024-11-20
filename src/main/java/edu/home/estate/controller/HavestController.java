package edu.home.estate.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.home.estate.dto.CategoryDto;
import edu.home.estate.dto.HavestDto;
import edu.home.estate.dto.LabourDto;
import edu.home.estate.dto.tm.HavestTM;
import edu.home.estate.model.CategoryModel;
import edu.home.estate.model.HavestModel;
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

public class HavestController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> cmbCategoryId;

    @FXML
    private JFXComboBox<String> cmbLabourId;

    @FXML
    private TableColumn<HavestTM, String> colCtegoryId;

    @FXML
    private TableColumn<HavestTM, Date> colDate;

    @FXML
    private TableColumn<HavestTM, Integer> colHavestId;

    @FXML
    private TableColumn<HavestTM, String> colLabourId;

    @FXML
    private TableColumn<HavestTM, Integer> colQuantity;

    @FXML
    private Label lblCategoryName;

    @FXML
    private Label lblHavestId;

    @FXML
    private Label lblLabourName;

    @FXML
    private TableView<HavestTM> tblHavest;

    @FXML
    private JFXTextField txtDate;

    @FXML
    private JFXTextField txtQuantity;

    private final ObservableList<HavestTM> havestTMS = FXCollections.observableArrayList();
    private final HavestModel havestModel = new HavestModel();
    private final LabourModel labourModel = new LabourModel();
    private final CategoryModel categoryModel = new CategoryModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }

    }

    private void setCellValues() {
        colHavestId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colLabourId.setCellValueFactory(new PropertyValueFactory<>("labour_id"));
        colCtegoryId.setCellValueFactory(new PropertyValueFactory<>("category_id"));

        tblHavest.setItems(havestTMS);

    }

    private void refreshPage() throws Exception {
        refreshTable();

        loadLabourId();
        loadCategoryId();

        cmbCategoryId.getSelectionModel().clearSelection();
        cmbLabourId.getSelectionModel().clearSelection();
        txtDate.setText("");
        txtQuantity.setText("");
        lblLabourName.setText("");
        lblCategoryName.setText("");

        havestTMS.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnReset.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<HavestDto> havestDtos = havestModel.getAllHavest();
        ObservableList<HavestTM> havestTMS = FXCollections.observableArrayList();


        for (HavestDto havestDto : havestDtos) {
            HavestTM havestTM = new HavestTM(
                    havestDto.getId(),
                    havestDto.getQuantity(),
                    havestDto.getDate(),
                    havestDto.getLabour_id(),
                    havestDto.getCategory_id()
            );
            havestTMS.add(havestTM);

        }
        tblHavest.setItems(havestTMS);
    }

    private void loadLabourId() throws SQLException {
        ArrayList<String> labourIds = labourModel.getAllLabourIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(labourIds);
        cmbLabourId.setItems(observableList);

    }

    private void loadCategoryId() throws Exception {
        ArrayList<String> categoryIds = categoryModel.getAllCategoryIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(categoryIds);
        cmbCategoryId.setItems(observableList);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
        int harvestId = Integer.parseInt(lblHavestId.getText());

        boolean isDelete = havestModel.deleteHavest(harvestId);

        if (isDelete) {
            System.out.println("delete harvest");
            refreshPage();

        }else{
            System.out.println("not delete harvest");
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws Exception {
        refreshPage();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        String quantity = txtQuantity.getText();
        String date = txtDate.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateAttendance = formatter.parse(date);
        String labourId = cmbLabourId.getSelectionModel().getSelectedItem();
        String categoryId = cmbCategoryId.getSelectionModel().getSelectedItem();

        HavestDto havestDto = new HavestDto(quantity,dateAttendance,labourId,categoryId);

        boolean isSaved = havestModel.saveHavest(havestDto);

        if (isSaved) {
            System.out.println("Harvest saved");
            refreshPage();

        }else{
            System.out.println("Harvest not saved");
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        System.out.println(lblHavestId.getText());
        int id =  Integer.parseInt(lblHavestId.getText());
        String quantity = txtQuantity.getText();
        String date = txtDate.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateAttendance = formatter.parse(date);
        String labourId = cmbLabourId.getSelectionModel().getSelectedItem();
        String categoryId = cmbCategoryId.getSelectionModel().getSelectedItem();

        HavestDto havestDto = new HavestDto(id,quantity,dateAttendance,labourId,categoryId);

        boolean isUpdated = havestModel.updateHavest(havestDto);

        if (isUpdated) {
            System.out.println("Harvest updated");
            refreshPage();

        }else{
            System.out.println("Harvest not updated");
        }

    }

    @FXML
    void cmbCategoryIdOnAction(ActionEvent event) throws Exception {
        String selectedCategoryId = cmbCategoryId.getSelectionModel().getSelectedItem();
        CategoryDto categoryDto = categoryModel.findById(selectedCategoryId);

        if(categoryDto != null) {
            lblCategoryName.setText(categoryDto.getName());

        }

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
    void tblClicked(MouseEvent event) {
        HavestTM selectedHarvestTm = tblHavest.getSelectionModel().getSelectedItem();
        if (selectedHarvestTm != null) {
            lblHavestId.setText(String.valueOf(selectedHarvestTm.getId()));
            lblLabourName.setText(String.valueOf(selectedHarvestTm.getId()));
            txtQuantity.setText(selectedHarvestTm.getQuantity());
            txtDate.setText(String.valueOf(selectedHarvestTm.getDate()));

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }


    }

}
