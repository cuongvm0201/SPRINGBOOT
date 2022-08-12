package vn.com.cmcglobal.demoshopcart.mapper.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private int id;
    private long total;
    private int user_id;
    // public Cart(List<CartLine> cartlines){
    //     cartlines.stream().forEach(cartlineitem -> {
    //       total += cartlineitem.getCount() * cartlineitem.getProduct().getPrice();
    //     });
    //   }
}
