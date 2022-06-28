package vn.techmaster.bank.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountSaver {
    @Id 
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private Account account; //Mỗi accountsaver phải gắn vào một account
  private Double startBalance;
  private Double endBalance;
  private Long months;
  private Double rate;

  @Enumerated(EnumType.STRING)
  private TypeSave typeSave;
  
  @Enumerated(EnumType.STRING)
  private AutoSaver autoSaver;

  private LocalDateTime openAt;
  private LocalDateTime updateAt;
  private LocalDateTime closeAt;
public AccountSaver orElseThrow(Object object) {
    return null;
}

  // @PrePersist // Trước khi lệnh chạy
  //   public void prePersist() {
  //       openAt = LocalDateTime.now();
  //   }

  


  
}
