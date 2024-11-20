package edu.home.estate.dto;
import java.util.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LabourDto {
    private String id;
    private String name;
    private String address;
    private Date dob;
    private String division_id;
}
