package edu.home.estate.dto.tm;

import java.util.Date;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class AgriculturalImplementsTM {
    private String id;
    private String name;
    private int quantity;
    private Date date;
}
