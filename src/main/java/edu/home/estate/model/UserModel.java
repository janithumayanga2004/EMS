package edu.home.estate.model;

import edu.home.estate.db.DBConnection;
import edu.home.estate.dto.UserDto;
import edu.home.estate.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserModel {

    public boolean saveUsers(UserDto userDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO Users (firstName, lastName, email, username, address, dateOfBirth, password) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, userDto.getFirstName());
        pst.setString(2, userDto.getLastName());
        pst.setString(3, userDto.getEmail());
        pst.setString(4, userDto.getUsername());
        pst.setString(5, userDto.getAddress());
        pst.setDate(6, new java.sql.Date(userDto.getDateOfBirth().getTime()));
        pst.setString(7, userDto.getPassword());

        int i = pst.executeUpdate();

        return i > 0;
    }

    public static List<UserDto> checkUsers(String email, String password) throws SQLException {

        ArrayList<UserDto> userDtos = new ArrayList<>();

        try{
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                UserDto userDto = new UserDto();

                userDto.setFirstName(rs.getString("firstName"));
                userDto.setLastName(rs.getString("lastName"));
                userDto.setEmail(rs.getString("email"));
                userDto.setUsername(rs.getString("username"));
                userDto.setAddress(rs.getString("address"));
                userDto.setDateOfBirth(rs.getDate("dateOfBirth"));
                userDto.setPassword(rs.getString("password"));
                userDtos.add(userDto);

            }


        }
        catch (Exception e){
            e.printStackTrace();
        }

        return userDtos;

    }





}
