package vn.techmaster.bank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "rate")
@Table(name = "rate")
public class RateConfig {
    @Id
    private String id;
    private Long months;
    private double rate;
}
