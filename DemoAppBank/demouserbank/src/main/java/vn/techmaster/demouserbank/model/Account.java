package vn.techmaster.demouserbank.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "account")
@Table(name = "account")
public class Account {
    @Id
    private String id;
    private String account;
    private String hash_pass;
    private long balance;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  @JoinColumn(name = "user_id")
  private User user;
}
