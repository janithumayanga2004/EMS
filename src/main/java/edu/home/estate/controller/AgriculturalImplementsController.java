package edu.home.estate.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.home.estate.dto.AgriculturalImplementsDto;
import edu.home.estate.dto.tm.AgriculturalImplementsTM;
import edu.home.estate.model.AgriculturalImplementsModel;
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

public class AgriculturalImplementsController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpDate;

    @FXML
    private TableColumn<AgriculturalImplementsTM, Date> colDate;

    @FXML
    private TableColumn<AgriculturalImplementsTM, String> colId;

    @FXML
    private TableColumn<AgriculturalImplementsTM,String> colName;

    @FXML
    private TableColumn<AgriculturalImplementsTM, Integer> colQuantity;

    @FXML
    private Label lblId;

    @FXML
    private TableView<AgriculturalImplementsTM> tblAgriculturalImplements;

    @FXML
    private JFXTextField txtDate;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtQuantity;

    private final ObservableList<AgriculturalImplementsTM> agriculturalImplementsTMS = FXCollections.observableArrayList();
    private final AgriculturalImplementsModel agriculturalImplementsModel = new AgriculturalImplementsModel();

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
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tblAgriculturalImplements.setItems(agriculturalImplementsTMS);
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextId = agriculturalImplementsModel.getNextId();
        lblId.setText(nextId);

        txtName.setText("");
        txtQuantity.setText("");
        txtDate.setText("");

        agriculturalImplementsTMS.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnReset.setDisable(true);

    }

    private void refreshTable() throws SQLException {
        ArrayList<AgriculturalImplementsDto> agriculturalImplementsDtos = agriculturalImplementsModel.getAll();
        ObservableList<AgriculturalImplementsTM> observableList = FXCollections.observableArrayList();

        for(AgriculturalImplementsDto agriculturalImplementsDto : agriculturalImplementsDtos) {

            AgriculturalImplementsTM agriculturalImplementsTM = new AgriculturalImplementsTM(
                    agriculturalImplementsDto.getId(),
                    agriculturalImplementsDto.getName(),
                    agriculturalImplementsDto.getQuantity(),
                    agriculturalImplementsDto.getDate()
            );
            observableList.add(agriculturalImplementsTM);

        }

        tblAgriculturalImplements.setItems(observableList);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String id = lblId.getText();

        boolean isDelete = agriculturalImplementsModel.deleteImplements(id);

        if(isDelete) {
            System.out.println("delete");
            refreshTable();

        }else{
            System.out.println("not delete");
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ParseException, SQLException {
        String id = lblId.getText();
        String name = txtName.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        String date = txtDate.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = formatter.parse(date);

        AgriculturalImplementsDto agriculturalImplementsDto = new AgriculturalImplementsDto(id, name, quantity, dates);

        boolean isSaved = agriculturalImplementsModel.saveImplements(agriculturalImplementsDto);


        if(isSaved) {
            System.out.println("save success");
            refreshPage();

        }else{
            System.out.println("save fail");
        }

    }

    @FXML
    void btnUpDateOnAction(ActionEvent event) throws ParseException, SQLException {
        String id = lblId.getText();
        String name = txtName.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        String date = txtDate.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = formatter.parse(date);

        AgriculturalImplementsDto agriculturalImplementsDto = new AgriculturalImplementsDto(id, name, quantity, dates);

        boolean isUpdate = agriculturalImplementsModel.updateImplements(agriculturalImplementsDto);

        if(isUpdate) {
            System.out.println("update success");
            refreshPage();

        }else {
            System.out.println("update fail");
        }

    }

    @FXML
    void tblClicked(MouseEvent event) {
        AgriculturalImplementsTM selected = tblAgriculturalImplements.getSelectionModel().getSelectedItem();
        if(selected != null) {
            lblId.setText(selected.getId());
            txtName.setText(selected.getName());
            txtQuantity.setText(String.valueOf(selected.getQuantity()));
            txtDate.setText(selected.getDate().toString());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpDate.setDisable(false);
        }


    }

}
