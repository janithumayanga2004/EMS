package edu.home.estate.model;

import edu.home.estate.dto.AttendanceDto;
import edu.home.estate.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceModel {
    public ArrayList<AttendanceDto> getallAttendance() throws SQLException {
        ResultSet rst =CrudUtil.execute("select * from Attendance");
        ArrayList<AttendanceDto> attendanceDtos = new ArrayList<>();

        while (rst.next()) {
            AttendanceDto attendanceDto = new AttendanceDto(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getTime(4),
                    rst.getString(5)
            );
            attendanceDtos.add(attendanceDto);
        }
        return attendanceDtos;
    }

    public boolean saveAttendance(AttendanceDto attendanceDto) throws SQLException {
        return CrudUtil.execute(
                "insert into Attendance(attendance,date,time,labour_id) values(?,?,?,?)",
                attendanceDto.getAttendance(),
                attendanceDto.getDate(),
                attendanceDto.getTime(),
                attendanceDto.getLabour_id()
        );
    }

    public boolean deleteAttendance(int id) throws SQLException {
        return CrudUtil.execute(
                "delete from Attendance where id = ?",id
        );
    }

    public boolean updateAttendance(AttendanceDto attendanceDto) throws SQLException {
        return CrudUtil.execute(
                "update Attendance attendance=?,date=?,time=?,labour_id=? where id=?",
                attendanceDto.getAttendance(),
                attendanceDto.getDate(),
                attendanceDto.getTime(),
                attendanceDto.getLabour_id(),
                attendanceDto.getId()

        );
    }


}
