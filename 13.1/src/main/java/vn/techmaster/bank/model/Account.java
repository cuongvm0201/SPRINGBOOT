package vn.techmaster.bank.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  @Id
  private String id;
  

  @ManyToOne(fetch = FetchType.LAZY)
  private Bank bank; //Mỗi account phải mở ở một ngân hàng
  
  @ManyToOne(fetch = FetchType.LAZY)
  private User user; //Mỗi account phải gắn vào một user

  private Double balance;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "account_id")
  private List<AccountSaver> accounts_saver = new ArrayList<>();

  private LocalDateTime openAt;


}
