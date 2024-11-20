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
    private int id;
    private String attendance;
    private Date date;
    private Time time;
    private String labour_id;

    public AttendanceDto(String attendance, Date dateAttendance, Date dateTimeAttendance, String labourId) {
        this.attendance = attendance;
        this.date = dateAttendance;
        this.time = new Time(dateTimeAttendance.getTime());
        this.labour_id = labourId;
    }

    public AttendanceDto(int id, String attendance, Date dateAttendance, String labourId) {
        this.id = id;
        this.attendance = attendance;
        this.date = dateAttendance;
        this.labour_id = labourId;
    }
}
