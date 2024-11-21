package edu.home.estate.model;

import edu.home.estate.dto.AttendanceDto;
import edu.home.estate.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class AttendanceModel {
    public ArrayList<AttendanceDto> getallAttendance() throws SQLException {
        ResultSet rst =CrudUtil.execute("select * from Attendance");
        ArrayList<AttendanceDto> attendanceDtos = new ArrayList<>();

        while (rst.next()) {
            AttendanceDto attendanceDto = new AttendanceDto(
                    rst.getString(1),
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
        String query = "INSERT INTO Attendance (id, attendance, date, time, labour_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, attendanceDto.getId());
            statement.setString(2, attendanceDto.getAttendance());
            statement.setDate(3, new java.sql.Date(attendanceDto.getDate().getTime()));
            statement.setTime(4, attendanceDto.getTime());
            statement.setString(5, attendanceDto.getLabour_id());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteAttendance(int id) throws SQLException {
        return CrudUtil.execute(
                "delete from Attendance where id = ?",id
        );
    }

    public boolean updateAttendance(AttendanceDto attendanceDto) throws SQLException {
        String query = "UPDATE Attendance SET attendance = ?, date = ?, time = ?, labour_id = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, attendanceDto.getAttendance());
            statement.setDate(2, new java.sql.Date(attendanceDto.getDate().getTime()));
            statement.setTime(3, attendanceDto.getTime());
            statement.setString(4, attendanceDto.getLabour_id());
            statement.setString(5, attendanceDto.getId());

            return statement.executeUpdate() > 0;
        }
    }

    public String getNextAttendanceId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select id from Attendance order by id desc limit 1");

        if (rst.next()) {
            String lastID = rst.getString(1);
            String subString = lastID.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i+1;
            return String.format("A%03d", newIdIndex);
        }
        return "A001";
    }

    public class DatabaseConnection {
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/Estate", "root", "1234");
        }
    }

    public int getDailyAttendanceCount() throws SQLException {
        int count = 0;
        ResultSet rst = CrudUtil.execute("SELECT COUNT(*) AS count FROM Attendance WHERE date = CURDATE()");
        if (rst.next()) {
            count = rst.getInt("count");
        }
        return count;
    }


}
