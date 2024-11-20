package edu.home.estate.dto;

import java.util.Date;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class HavestDto {
    private int id;
    private String quantity;
    private Date date;
    private String labour_id;
    private String category_id;

    public HavestDto(String quantity, Date dateAttendance, String labourId, String categoryId) {
        this.quantity = quantity;
        this.date = dateAttendance;
        this.labour_id = labourId;
        this.category_id = categoryId;
    }
}
