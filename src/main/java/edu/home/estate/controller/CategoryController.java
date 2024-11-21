package edu.home.estate.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.home.estate.dto.CategoryDto;
import edu.home.estate.dto.tm.CategoryTM;
import edu.home.estate.model.CategoryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    CategoryModel categoryModel = new CategoryModel();

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnReport;

    @FXML
    private TableColumn<CategoryTM, String> colCategoryId;

    @FXML
    private TableColumn<CategoryTM, String> colCategoryName;

    @FXML
    private TableColumn<CategoryTM, String> colDescription;

    @FXML
    private Label lblCategoryId;

    @FXML
    private TableView<CategoryTM> tblCategory;

    @FXML
    private JFXTextField txtCategoryName;

    @FXML
    private JFXTextField txtDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        try{
            refreshPage();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void refreshPage() throws Exception {
        refreshTable();

        String nextCategoryId = categoryModel.getNextCategoryId();
        lblCategoryId.setText(nextCategoryId);

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnReset.setDisable(true);

    }

    private void refreshTable() throws Exception {
        ArrayList<CategoryDto> categoryDtos = categoryModel.gettAllCategories();
        ObservableList<CategoryTM> categoryTMS = FXCollections.observableArrayList();

        for (CategoryDto categoryDto : categoryDtos) {
            CategoryTM categoryTM = new CategoryTM(
                    categoryDto.getId(),
                    categoryDto.getName(),
                    categoryDto.getDescription()
            );
            categoryTMS.add(categoryTM);

        }
        tblCategory.setItems(categoryTMS);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws Exception {
        String id = lblCategoryId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this Category?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            boolean isDelete = categoryModel.deleteCategory(id);

            if(isDelete) {
                new Alert(Alert.AlertType.INFORMATION,"Category deleted...!").show();
                refreshPage();

            }else{
                new Alert(Alert.AlertType.ERROR,"Fail to delete Category...!").show();
            }

        }

    }

    @FXML
    void resetOnAction(ActionEvent event) throws Exception {
        refreshPage();

    }

    @FXML
    void saveOnAction(ActionEvent event) throws Exception {
        String id = lblCategoryId.getText();
        String name = txtCategoryName.getText();
        String description = txtDescription.getText();

        CategoryDto categoryDto = new CategoryDto(id,name,description);

        String namePattern = "^[A-Za-z ]+$";
        String descriptionPattern = "^[A-Za-z ]+$";

        boolean isValiedName = name.matches(namePattern);
        boolean isValiedDescription = description.matches(descriptionPattern);

        txtCategoryName.setStyle(txtCategoryName.getStyle()+ ";-fx-border-color: #7367F0;");
        txtDescription.setStyle(txtDescription.getStyle()+ ";-fx-border-color: #7367F0");

        if(!isValiedName){
            txtCategoryName.setStyle(txtCategoryName.getStyle() + ";;-fx-border-color: red;");

        }

        if(!isValiedDescription){
            txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: red;");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to save this Category?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            if (isValiedName && isValiedDescription) {
                boolean isSaved = categoryModel.saveCategory(categoryDto);
                if (isSaved) {
                    refreshPage();
                    //new Alert(Alert.AlertType.INFORMATION, "Estate Saved...!").show();
                } else {

                    new Alert(Alert.AlertType.ERROR, "Failed to save Category...!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please ensure all fields are valid.").show();
            }
        }


    }

    @FXML
    void tblClicked(MouseEvent event) {
        CategoryTM selectedCategory = tblCategory.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            lblCategoryId.setText(String.valueOf(selectedCategory.getId()));
            txtCategoryName.setText(selectedCategory.getName());
            txtDescription.setText(selectedCategory.getDescription());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnReset.setDisable(false);
        }

    }

    @FXML
    void updateOnAction(ActionEvent event) throws Exception {
        String id = lblCategoryId.getText();
        String name = txtCategoryName.getText();
        String description = txtDescription.getText();

        CategoryDto categoryDto = new CategoryDto(id,name,description);

        String namePattern = "^[A-Za-z ]+$";
        String descriptionPattern = "^[A-Za-z ]+$";

        boolean isValiedName = name.matches(namePattern);
        boolean isValiedDescription = description.matches(descriptionPattern);

        txtCategoryName.setStyle(txtCategoryName.getStyle()+ ";-fx-border-color: #7367F0;");
        txtDescription.setStyle(txtDescription.getStyle()+ ";-fx-border-color: #7367F0");

        if(!isValiedName){
            txtCategoryName.setStyle(txtCategoryName.getStyle() + ";;-fx-border-color: red;");

        }

        if(!isValiedDescription){
            txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: red;");
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to update this Category?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            if (isValiedName && isValiedDescription) {
                boolean isUpdate = categoryModel.updateCategory(categoryDto);
                if (isUpdate) {
                    refreshPage();
                    //new Alert(Alert.AlertType.INFORMATION, "Estate Saved...!").show();
                } else {

                    new Alert(Alert.AlertType.ERROR, "Failed to update Category...!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please ensure all fields are valid.").show();
            }
        }


    }

    @FXML
    void clearDesctiption(MouseEvent event) {
        txtDescription.clear();

    }

    @FXML
    void clearName(MouseEvent event) {
        txtCategoryName.clear();

    }

    @FXML
    void btnGenerateReport(MouseEvent event) {

    }


}
