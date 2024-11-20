package edu.home.estate.dto.tm;
import java.util.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class LabourTM {
    private String id;
    private String name;
    private String address;
    private Date dob;
    private String division_id;

}
