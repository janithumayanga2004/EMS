package edu.home.estate.model;

import edu.home.estate.dto.EstateDto;
import edu.home.estate.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EstateModel {

    public String getNextEstataID() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Estate order by id desc limit 1");

        if (rst.next()) {
            String lastID = rst.getString(1);
            String subString = lastID.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    public ArrayList<EstateDto> getAllEstates() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Estate");

        ArrayList<EstateDto> estateDtos = new ArrayList<>();

        while (rst.next()) {
            EstateDto estateDto = new EstateDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            estateDtos.add(estateDto);
        }

        return estateDtos;
    }

    public boolean saveEstate(EstateDto estateDto) throws SQLException {
        return CrudUtil.execute(
                "insert into Estate (id,name,area,city) values(?,?,?,?)",
                estateDto.getId(),
                estateDto.getName(),
                estateDto.getArea(),
                estateDto.getCity()
        );
    }

    public boolean deleteEstate(String id) throws SQLException {
        return CrudUtil.execute("delete from Estate where id = ?", id);
    }

    public boolean updateEstate(EstateDto estateDto) throws SQLException {
        return CrudUtil.execute(
                "update Estate set name=?, area=?, city=? where id=?",
                estateDto.getName(),
                estateDto.getArea(),
                estateDto.getCity(),
                estateDto.getId()
        );
    }

    public ArrayList<String> getAllEstateIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Estate");
        ArrayList<String> ids = new ArrayList<>();

        while (rst.next()) {
            ids.add(rst.getString(1));
        }

      return ids;
    }

    public EstateDto findById(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Estate where id = ?", id);
        if (rst.next()) {
            return  new EstateDto(
              rst.getString(1),
              rst.getString(2),
              rst.getString(3),
              rst.getString(4)
            );
        }

        return null;

    }


}
