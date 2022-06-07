package vn.techmaster.demouserbank.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
@Table(name = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String address;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Account> accounts = new ArrayList<>();
    public void addAccount(Account account) {
        account.setUser(this);
        accounts.add(account);
      }

      public void removeAccount(Account account) {
        account.setUser(null);
        accounts.remove(account);   
      }
}
