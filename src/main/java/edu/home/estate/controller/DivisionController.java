package edu.home.estate.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.home.estate.dto.CategoryDetailsDto;
import edu.home.estate.dto.CategoryDto;
import edu.home.estate.dto.DivisionDto;
import edu.home.estate.dto.EstateDto;
import edu.home.estate.dto.tm.CartTM;
import edu.home.estate.model.CategoryModel;
import edu.home.estate.model.DivisionModel;
import edu.home.estate.model.EstateModel;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DivisionController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCateogryId;

    @FXML
    private JFXComboBox<String> cmbEstateId;

    @FXML
    private TableColumn<CartTM, String> colCategoryId;

    @FXML
    private TableColumn<CartTM, String> colCateoryName;

    @FXML
    private TableColumn<CartTM, String> colDescription;

    @FXML
    private Label lblCategoryName;

    @FXML
    private Label lblDivisionId;

    @FXML
    private Label lblEstateName;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private JFXTextField txtBungalows;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtDivisionArea;

    @FXML
    private JFXTextField txtDivisionName;

    @FXML
    private JFXTextField txtQty;

    private final DivisionModel divisionModel = new DivisionModel();
    private final EstateModel estateModel = new EstateModel();
    private final CategoryModel categoryModel = new CategoryModel();

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();


        try {
            refreshPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    private void setCellValues() {
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colCateoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        tblCart.setItems(cartTMS);
    }

    private void refreshPage() throws Exception {
        lblDivisionId.setText(divisionModel.getNextDivisionId());

        loadEstateId();
        loadCategoryId();


        cmbCateogryId.getSelectionModel().clearSelection();
        cmbEstateId.getSelectionModel().clearSelection();
        lblEstateName.setText("");
        lblCategoryName.setText("");
        txtQty.setText("");
        txtBungalows.setText("");
        txtDescription.setText("");
        txtDivisionArea.setText("");
        txtDivisionName.setText("");
        txtQty.setText("");

        cartTMS.clear();
        tblCart.refresh();

    }

    private void loadEstateId() throws SQLException {
        ArrayList<String> estateIds = estateModel.getAllEstateIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(estateIds);
        cmbEstateId.setItems(observableList);
    }

    private void loadCategoryId() throws Exception {
        ArrayList<String> categoryIds = categoryModel.getAllCategoryIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(categoryIds);
        cmbCateogryId.setItems(observableList);
    }

    @FXML
    void btnOnActionAddToCart(ActionEvent event) {
        String selectedCategoryId = cmbCateogryId.getValue();

        if (selectedCategoryId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select category..!").show();
            return;
        }

        String categoryName = lblCategoryName.getText();
        String description = txtDescription.getText();
        String qty = txtQty.getText();

        for(CartTM cartTM : cartTMS) {
            if (cartTM.getCategoryId() == selectedCategoryId) {
                tblCart.refresh();
                return;
            }

        }

        CartTM newCart = new CartTM(
                selectedCategoryId,
                categoryName,
                description,
                qty
        );

        cartTMS.remove(newCart);
        tblCart.refresh();
        cartTMS.add(newCart);

    }

    @FXML
    void btnOnActionPlaceOrder(ActionEvent event) throws Exception {
        if(tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add category to cart..!").show();
            return;

        }
        if(cmbEstateId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select estate for place division..!").show();
            return;
        }


        String divisionId = lblDivisionId.getText();
        String divisionName = txtDivisionName.getText();
        String area = txtDivisionArea.getText();
        String bungalows = txtBungalows.getText();
        String estateId = cmbEstateId.getValue();

        ArrayList<CategoryDetailsDto> categoryDetailsDtos = new ArrayList<>();


        for(CartTM cartTM : cartTMS) {
            CategoryDetailsDto categoryDetailsDto = new CategoryDetailsDto(
                    cartTM.getQuantity(),
                    cartTM.getCategoryId(),
                    divisionId
            );

            categoryDetailsDtos.add(categoryDetailsDto);
        }

        DivisionDto divisionDto = new DivisionDto(
                divisionId,
                divisionName,
                area,
                bungalows,
                estateId,
                categoryDetailsDtos
        );

        boolean isSaved = divisionModel.saveDivision(divisionDto);

        if(isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Division saved..!").show();
            refreshPage();

        }else{
            new Alert(Alert.AlertType.ERROR, "Division fail..!").show();
        }

    }

    @FXML
    void btnOnActionReset(ActionEvent event) throws Exception {
        refreshPage();

    }

    @FXML
    void cmbCateogryIdOnAction(ActionEvent event) throws Exception {
        String selectedCategoryId = cmbCateogryId.getSelectionModel().getSelectedItem();
        CategoryDto categoryDto = categoryModel.findById(selectedCategoryId);

        if(categoryDto != null) {
            lblCategoryName.setText(categoryDto.getName());

        }

    }

    @FXML
    void cmbEstateIdOnAction(ActionEvent event) throws SQLException {
        String selectedEstateId = cmbEstateId.getSelectionModel().getSelectedItem();
        EstateDto estateDto = estateModel.findById(selectedEstateId);

        if(estateDto != null) {
            lblEstateName.setText(estateDto.getName());

        }

    }

}
