package edu.home.estate.dto.tm;

import java.sql.Time;
import java.util.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class AttendanceTM {
    private int id;
    private String attendance;
    private Date date;
    private Time time;
    private String labour_id;

}
