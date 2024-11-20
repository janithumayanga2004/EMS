package edu.home.estate.dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class DivisionDto {
    private String id;
    private String name;
    private String area;
    private String bungalows;
    private String estate_id;

    private ArrayList<CategoryDetailsDto>categoryDetailsDtos;

    public DivisionDto(String string, String string1, String string2, String string3, String string4) {
        this.id = string;
        this.name = string1;
        this.area = string2;
        this.bungalows = string3;
        this.estate_id = string4;

    }
}
