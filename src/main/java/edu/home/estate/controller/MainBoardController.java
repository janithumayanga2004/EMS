package edu.home.estate.controller;

import edu.home.estate.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainBoardController implements Initializable {

    @FXML
    private Label assets_count;

    @FXML
    private Label category_count;

    @FXML
    private Label daily_attendace;

    @FXML
    private Label division_count;

    @FXML
    private Label estate_count;

    @FXML
    private Label labour_count;

    private final AgriculturalImplementsModel agriculturalImplementsModel = new AgriculturalImplementsModel();
    private final EstateModel estateModel = new EstateModel();
    private final DivisionModel divisionModel = new DivisionModel();
    private final CategoryModel categoryModel = new CategoryModel();
    private final LabourModel labourModel = new LabourModel();
    private final AttendanceModel attendanceModel = new AttendanceModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int estateCount = estateModel.getEstateCount();
            estate_count.setText(String.valueOf(estateCount));

            int divisionCount = divisionModel.getDivisionCount();
            division_count.setText(String.valueOf(divisionCount));

            int categoryCount = categoryModel.getCategoryCount();
            category_count.setText(String.valueOf(categoryCount));

            int labourCount = labourModel.getLabourCount();
            labour_count.setText(String.valueOf(labourCount));

            int assetsCount = agriculturalImplementsModel.getAssetsCount();
            assets_count.setText(String.valueOf(assetsCount));

            int dailyAttendanceCount = attendanceModel.getDailyAttendanceCount();
            daily_attendace.setText(String.valueOf(dailyAttendanceCount));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
