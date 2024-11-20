package edu.home.estate.controller;

import edu.home.estate.dto.EstateDto;
import edu.home.estate.dto.tm.EstateTM;
import edu.home.estate.model.EstateModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EstateController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<EstateTM,String > colArea;

    @FXML
    private TableColumn<EstateTM, String> colCity;

    @FXML
    private TableColumn<EstateTM, Integer> colEstateId;

    @FXML
    private TableColumn<EstateTM, String> colName;

    @FXML
    private Label lblEstateId;

    @FXML
    private TableView<EstateTM> tblEstate;

    @FXML
    private TextField txtEstateArea;

    @FXML
    private TextField txtEstateCity;

    @FXML
    private TextField txtEstateName;

    EstateModel estateModel = new EstateModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEstateId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));

        try{
            refreshPage();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void refreshPage() throws Exception {
        refreshTable();

        String nextEstateID = estateModel.getNextEstataID();
        lblEstateId.setText(nextEstateID);

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnReset.setDisable(true);

    }

    private void refreshTable() throws Exception {
        ArrayList<EstateDto> estateDtos = estateModel.getAllEstates();
        ObservableList<EstateTM> estateTMS = FXCollections.observableArrayList();

        for(EstateDto estateDto : estateDtos) {
            EstateTM estateTM = new EstateTM(
                    estateDto.getId(),
                    estateDto.getName(),
                    estateDto.getArea(),
                    estateDto.getCity()
            );

            estateTMS.add(estateTM);

        }
        tblEstate.setItems(estateTMS);


    }

    @FXML
    void btnOnActionDelete(ActionEvent event) throws Exception {

        String id = lblEstateId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this Estate?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            boolean isDelete = estateModel.deleteEstate(id);

            if(isDelete) {
                new Alert(Alert.AlertType.INFORMATION,"Estate deleted...!").show();
                refreshPage();

            }else{
                new Alert(Alert.AlertType.ERROR,"Fail to delete Estate...!").show();
            }


        }

    }

    @FXML
    void btnOnActionReset(ActionEvent event) throws Exception {
        refreshPage();

    }

    @FXML
    void btnOnActionSave(ActionEvent event) throws Exception {
        String id = lblEstateId.getText();
        String name = txtEstateName.getText();
        String area = txtEstateArea.getText();
        String city = txtEstateCity.getText();

        EstateDto estateDto = new EstateDto(id,name, area, city);

        String namePattern = "^[A-Za-z ]+$";
        String areaPattern = "^[A-Za-z0-9 ]+$";
        String cityPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidArea = area.matches(areaPattern);
        boolean isValidCity = city.matches(cityPattern);

        txtEstateName.setStyle(txtEstateName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEstateArea.setStyle(txtEstateArea.getStyle() + ";-fx-border-color: #7367F0;");
        txtEstateCity.setStyle(txtEstateCity.getStyle() + ";-fx-border-color: #7367F0;");

        if(!isValidName) {
            txtEstateName.setStyle(txtEstateName.getStyle() + ";;-fx-border-color: red;");
        }

        if(!isValidArea) {
            txtEstateArea.setStyle(txtEstateArea.getStyle() + ";;-fx-border-color: red;");
        }

        if(!isValidCity) {
            txtEstateCity.setStyle(txtEstateCity.getStyle() + ";;-fx-border-color: red;");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to save this Estate?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            if (isValidName && isValidArea && isValidCity) {
                boolean isSaved = estateModel.saveEstate(estateDto);
                if (isSaved) {
                    refreshPage();
                    //new Alert(Alert.AlertType.INFORMATION, "Estate Saved...!").show();
                } else {

                    new Alert(Alert.AlertType.ERROR, "Failed to save Estate...!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please ensure all fields are valid.").show();
            }
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) throws Exception {
        String id = lblEstateId.getText();
        String name = txtEstateName.getText();
        String area = txtEstateArea.getText();
        String city = txtEstateCity.getText();


        EstateDto estateDto = new EstateDto(id,name, area, city);

        String namePattern = "^[A-Za-z ]+$";
        String areaPattern = "^[A-Za-z0-9 ]+$";
        String cityPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidArea = area.matches(areaPattern);
        boolean isValidCity = city.matches(cityPattern);

        txtEstateName.setStyle(txtEstateName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEstateArea.setStyle(txtEstateArea.getStyle() + ";-fx-border-color: #7367F0;");
        txtEstateCity.setStyle(txtEstateCity.getStyle() + ";-fx-border-color: #7367F0;");

        if(!isValidName) {
            txtEstateName.setStyle(txtEstateName.getStyle() + ";;-fx-border-color: red;");
        }

        if(!isValidArea) {
            txtEstateArea.setStyle(txtEstateArea.getStyle() + ";;-fx-border-color: red;");
        }

        if(!isValidCity) {
            txtEstateCity.setStyle(txtEstateCity.getStyle() + ";;-fx-border-color: red;");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to update this Estate?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            if (isValidName && isValidArea && isValidCity) {
                boolean isUpdate = estateModel.updateEstate(estateDto);
                if (isUpdate) {
                    refreshPage();
                    //new Alert(Alert.AlertType.INFORMATION, "Estate Saved...!").show();
                } else {

                    new Alert(Alert.AlertType.ERROR, "Failed to save Estate...!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please ensure all fields are valid.").show();
            }
        }



    }

    public void onClickTable(javafx.scene.input.MouseEvent mouseEvent) {
        EstateTM selectedEstate = tblEstate.getSelectionModel().getSelectedItem();

        if(selectedEstate != null) {
            lblEstateId.setText(selectedEstate.getId());
            txtEstateName.setText(selectedEstate.getName());
            txtEstateArea.setText(selectedEstate.getArea());
            txtEstateCity.setText(selectedEstate.getCity());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnReset.setDisable(false);
        }

        }


    public void clearName(javafx.scene.input.MouseEvent mouseEvent) {
        txtEstateName.clear();
    }

    public void clearArea(javafx.scene.input.MouseEvent mouseEvent) {
        txtEstateArea.clear();
    }

    public void clearCity(javafx.scene.input.MouseEvent mouseEvent) {
        txtEstateCity.clear();
    }
}
