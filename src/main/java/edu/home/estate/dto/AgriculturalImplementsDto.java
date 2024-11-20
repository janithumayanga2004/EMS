package edu.home.estate.dto;

import java.util.ArrayList;
import java.util.Date;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class AgriculturalImplementsDto {
    private String id;
    private String name;
    private int quantity;
    private Date date;


    public AgriculturalImplementsDto(String string, String string1, int anInt, java.sql.Date date) {
        this.id = string;
        this.name = string1;
        this.quantity = anInt;
        this.date = date;
    }
}
