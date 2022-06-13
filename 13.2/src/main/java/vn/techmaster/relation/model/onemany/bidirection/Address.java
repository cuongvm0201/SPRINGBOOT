package vn.techmaster.relation.model.onemany.bidirection;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "address")
@Table(name = "address")
@Data
@NoArgsConstructor
public class Address {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String detail;
  public Address(String detail) {
    this.detail = detail;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private Customer customer;
}
