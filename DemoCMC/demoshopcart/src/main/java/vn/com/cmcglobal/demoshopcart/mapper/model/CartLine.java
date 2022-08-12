package vn.com.cmcglobal.demoshopcart.mapper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartLine {
    private int id;
    private int cart_id;
    private int product_id;
    private int count;
    public void increaseByOne() {
        count += 1;
      }
}
