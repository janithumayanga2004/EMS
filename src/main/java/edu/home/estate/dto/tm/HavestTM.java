package edu.home.estate.dto.tm;

import java.util.Date;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HavestTM {
    private String id;
    private String quantity;
    private Date date;
    private String labour_id;
    private String category_id;
}
