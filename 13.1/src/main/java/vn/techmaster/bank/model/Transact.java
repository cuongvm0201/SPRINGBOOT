package vn.techmaster.bank.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class Transact {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable=true, insertable=true, updatable=false)
  private Account fromAcc; //Chuyển từ Account nào

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable=true, insertable=true, updatable=false)
  private Account toAcc; //Chuyển đến Account nào

  private Long amount;

  private TransactType transactType;
 
  private LocalDateTime createdAt;

  @PrePersist // Trước khi lưu khi khởi tạo Entity
  public void prePersist() {
     createdAt = LocalDateTime.now();
  }
}
