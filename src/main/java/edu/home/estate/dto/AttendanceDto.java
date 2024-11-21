package edu.home.estate.dto;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class AttendanceDto {
    private String id;
    private String attendance;
    private Date date;
    private Time time;
    private String labour_id;

}
