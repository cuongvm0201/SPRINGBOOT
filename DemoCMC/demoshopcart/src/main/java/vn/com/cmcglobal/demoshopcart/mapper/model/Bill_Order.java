package vn.com.cmcglobal.demoshopcart.mapper.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill_Order {
    private int id;
    private int user_id;
    private int cartline_id;
    private LocalDateTime creatAt;
}
