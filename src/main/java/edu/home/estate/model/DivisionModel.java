package edu.home.estate.model;

import edu.home.estate.db.DBConnection;
import edu.home.estate.dto.CategoryDto;
import edu.home.estate.dto.DivisionDto;
import edu.home.estate.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DivisionModel {
    private final CategoryDetailsModel categoryDetailsModel = new CategoryDetailsModel();

    public boolean saveDivision(DivisionDto  divisionDto) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        try{
            connection.setAutoCommit(false);

            boolean isDivisionSaved = CrudUtil.execute(
                    "insert into Division values(?,?,?,?,?)",
                    divisionDto.getId(),
                    divisionDto.getName(),
                    divisionDto.getArea(),
                    divisionDto.getBungalows(),
                    divisionDto.getEstate_id()

            );


            if(isDivisionSaved){
                boolean isCategoryDetailsSaved = categoryDetailsModel.saveCategoryDetailsList(divisionDto.getCategoryDetailsDtos());
                if(isCategoryDetailsSaved){
                    connection.commit();
                    return true;
                }
            }

            connection.rollback();
            return false;
        }catch(Exception e){
            connection.rollback();
            return false;

        }finally{
            connection.setAutoCommit(true);
        }
    }

    public String getNextDivisionId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Division order by id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("D%03d", newIdIndex);
        }

        return "D001";
    }

    public ArrayList<String> getAllDivisionId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Division ");
        ArrayList<String> divisionIds = new ArrayList<>();

        while (rst.next()) {
            divisionIds.add(rst.getString(1));
        }
        return divisionIds;
    }

    public DivisionDto getDivisionById(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Division where id = ?", id);

        if (rst.next()) {
            return new DivisionDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5));

        }
        return null;
    }


}
