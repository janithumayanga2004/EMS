package edu.home.estate.dto;

import java.util.Date;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class HavestDto {
    private String id;
    private String quantity;
    private Date date;
    private String labour_id;
    private String category_id;
}
