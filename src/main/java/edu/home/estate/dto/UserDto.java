package edu.home.estate.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String address;
    private Date dateOfBirth;
    private String password;



}
