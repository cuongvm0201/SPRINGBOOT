package vn.techmaster.finalproject.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
  private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String fullname;
    private String email;
    private String hashed_password;
    private String mobile;
    private String address;
    private Long wallet;
    private String avatar_path;
    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Roles role;
    @Enumerated(EnumType.STRING)
    private City city;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "user_id")
    private List<Reverse> reverses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "user_id")
    private List<Bill> bills = new ArrayList<>();
    
    private LocalDateTime creatAt;
    private LocalDateTime updateAt;

    @PrePersist
    public void prePersist() {
        creatAt = LocalDateTime.now();
      }

    @PreRemove
    public void preRemove() {
        reverses.stream().forEach(p -> p.setUser(null));
        bills.stream().forEach(i -> i.setUser(null));
      }
}
