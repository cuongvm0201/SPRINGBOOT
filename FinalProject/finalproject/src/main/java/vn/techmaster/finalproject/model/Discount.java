package vn.techmaster.finalproject.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Double rateBonus;
}
