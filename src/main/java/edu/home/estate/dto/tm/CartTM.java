package edu.home.estate.dto.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CartTM {
    private  String categoryId;
    private  String categoryName;
    private String description;
    private String quantity;
}
