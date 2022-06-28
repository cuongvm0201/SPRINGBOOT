package vn.techmaster.finalproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private String name;
    private String cardnumber;
    private String month;
    private String year;
    private String securitycode;
}
