package edu.home.estate.model;

import edu.home.estate.dto.HavestDto;
import edu.home.estate.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HavestModel {
    public ArrayList<HavestDto> getAllHavest() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Harvest");
        ArrayList<HavestDto> havestDtos = new ArrayList<>();

        while (rst.next()) {
            HavestDto havestDto = new HavestDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            havestDtos.add(havestDto);
        }
        return havestDtos;
    }

    public boolean saveHavest(HavestDto havestDto) throws SQLException {
        return CrudUtil.execute(
                "insert into Harvest(id,quantity,date,labour_id,category_id) values (?,?,?,?,?)",
                havestDto.getId(),
                havestDto.getQuantity(),
                havestDto.getDate(),
                havestDto.getLabour_id(),
                havestDto.getCategory_id()
        );
    }

    public boolean updateHavest(HavestDto havestDto) throws SQLException {
        return CrudUtil.execute(
                "update Harvest set quantity=?,date=?,labour_id=?,category_id=? where id =?",
                havestDto.getQuantity(),
                havestDto.getDate(),
                havestDto.getLabour_id(),
                havestDto.getCategory_id(),
                havestDto.getId()
        );
    }

    public boolean deleteHavest(String id) throws SQLException {
        return CrudUtil.execute(
                "delete from Harvest where id=?",id
        );
    }

    public String getNextHarvestId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Harvest order by id desc limit 1");

        if (rst.next()) {
            String lastID = rst.getString(1);
            String subString = lastID.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            return String.format("H%03d", newIdIndex);
        }
        return "H001";
    }
}
