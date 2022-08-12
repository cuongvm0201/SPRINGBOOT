package vn.com.cmcglobal.demoshopcart.mapper.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private long price;
    private long amount;
    private Category category;
    private LocalDateTime creatAt;
    private LocalDateTime updateAt;
}
