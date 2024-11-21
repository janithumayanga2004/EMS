package edu.home.estate.model;

import edu.home.estate.dto.LabourDto;
import edu.home.estate.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LabourModel {
    public String getNextLabourId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Labour order by id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1; // 3
            return String.format("L%03d", newIdIndex);
        }
        return "L001";
    }

    public boolean saveLabour(LabourDto labourDto) throws SQLException {
        return CrudUtil.execute(
                "insert into Labour values(?,?,?,?,?)",
                labourDto.getId(),
                labourDto.getName(),
                labourDto.getAddress(),
                labourDto.getDob(),
                labourDto.getDivision_id()
        );
    }

    public ArrayList<LabourDto> getAllLabours() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Labour");

        ArrayList<LabourDto> labourDtos = new ArrayList<>();

        while (rst.next()) {
            LabourDto labourDto = new LabourDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5)
            );
            labourDtos.add(labourDto);
        }
        return labourDtos;
    }

    public boolean deleteLabour(String id) throws SQLException {
        return CrudUtil.execute(
                "delete from Labour where id=?",id);
    }

    public boolean updateLabour(LabourDto labourDto) throws SQLException {
        return CrudUtil.execute(
                "update Labour set name=?,address=?,dob=? where id=?",
                labourDto.getName(),
                labourDto.getAddress(),
                labourDto.getDob(),
                labourDto.getId()
        );
    }

    public ArrayList<String> getAllLabourIds() throws SQLException{
        ResultSet rst = CrudUtil.execute("select id from Labour");
        ArrayList<String> labourIds = new ArrayList<>();

        while (rst.next()) {
            labourIds.add(rst.getString(1));
        }
        return labourIds;
    }

    public LabourDto getLabour(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Labour where id=?", id);

        if (rst.next()) {
            return new LabourDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public int getLabourCount() throws SQLException {
        int count = 0;
        ResultSet rst = CrudUtil.execute("SELECT COUNT(id) AS count FROM Labour");
        if (rst.next()) {
            count = rst.getInt("count"); // Get the count from the ResultSet
        }
        return count;
    }
}
