package edu.home.estate.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CategoryDetailsDto {
    private int id;
    private String quantity;
    private String category_id;
    private String division_id;

    public CategoryDetailsDto(String quantity, String category_id, String division_id) {
        this.quantity = quantity;
        this.category_id = category_id;
        this.division_id = division_id;
    }
}
