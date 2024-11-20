package edu.home.estate.model;

import edu.home.estate.dto.AgriculturalImplementsDto;
import edu.home.estate.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AgriculturalImplementsModel {
    public String getNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from AgriculturalImplements order by id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1; // 3
            return String.format("A%03d", newIdIndex);
        }
        return "A001";
    }

    public ArrayList<AgriculturalImplementsDto> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from AgriculturalImplements");
        ArrayList<AgriculturalImplementsDto> agriculturalImplementsList = new ArrayList<>();

        while (rst.next()) {
            AgriculturalImplementsDto agriculturalImplementsDto = new AgriculturalImplementsDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDate(4)

            );
            agriculturalImplementsList.add(agriculturalImplementsDto);
        }

        return agriculturalImplementsList;

    }

    public boolean saveImplements(AgriculturalImplementsDto agriculturalImplementsDto) throws SQLException {
        return CrudUtil.execute(
                "insert into AgriculturalImplements values(?,?,?,?)",
                agriculturalImplementsDto.getId(),
                agriculturalImplementsDto.getName(),
                agriculturalImplementsDto.getQuantity(),
                agriculturalImplementsDto.getDate()
        );
    }

    public boolean updateImplements(AgriculturalImplementsDto agriculturalImplementsDto) throws SQLException {
        return CrudUtil.execute(
                "update AgriculturalImplements set name=?,quantity=?,date=? where id=?",
                agriculturalImplementsDto.getName(),
                agriculturalImplementsDto.getQuantity(),
                agriculturalImplementsDto.getDate(),
                agriculturalImplementsDto.getId()
        );

    }

    public boolean deleteImplements(String id) throws SQLException {
        return CrudUtil.execute(
                "delete from AgriculturalImplements where id=?",id
        );
    }
//    public ArrayList<String> getAllAssets() throws SQLException {
//        ResultSet rst =CrudUtil.execute("select id from AgriculturalImplements");
//        ArrayList<String> assetsIds = new ArrayList<>();
//
//        while (rst.next()) {
//            assetsIds.add(rst.getString(1));
//        }
//        return assetsIds;
//    }

//    public AgriculturalImplementsDto findById(String selectedId) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select * from AgriculturalImplements where id=?", selectedId);
//
//        if (rst.next()) {
//            return new AgriculturalImplementsDto(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getInt(3),
//                    rst.getDate(4)
//            );
//        }
//        return null;
//    }

//    public boolean saveAssetsImplementsList(ArrayList<AgriculturalImplementsDto> agriculturalImplementsDtos) throws SQLException {
//        for (AgriculturalImplementsDto agriculturalImplementsDto : agriculturalImplementsDtos) {
//            boolean isSavedAssets = saveImplements(agriculturalImplementsDto);
//            if (!isSavedAssets) {
//                // If saving any asset fails, return false immediately
//                return false;
//            }
//        }
//        // If all assets are saved successfully, return true
//        return true;
//    }

}
