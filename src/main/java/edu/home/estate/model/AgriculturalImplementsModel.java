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

    public int getAssetsCount() throws SQLException {
        int count = 0;
        ResultSet rst = CrudUtil.execute("SELECT COUNT(id) AS count FROM AgriculturalImplements");
        if (rst.next()) {
            count = rst.getInt("count"); // Get the count from the ResultSet
        }
        return count;
    }


}
