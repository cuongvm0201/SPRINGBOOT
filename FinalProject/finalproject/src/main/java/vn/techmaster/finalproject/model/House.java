package vn.techmaster.finalproject.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class House {
    @Id
    private String id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private City city;
    @Enumerated(EnumType.STRING)
    private TypeHouse typeHouse;
    private String address;
   
    private Long price;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "house_id")
    private List<Reverse> reverses = new ArrayList<>();
    
    private String logo_main;
  
    private String logo_sub_main1;

    private String logo_sub_main2;
 
    private String logo_sub_main3;
    
    private LocalDateTime creatAt;
    private LocalDateTime updateAt;
    private Long view;
    private Long rever;

    public long increment(){
      return view++;
    }

    public long increment1(){
      return rever++;
    }
    @PrePersist
    public void prePersist() {
        creatAt = LocalDateTime.now();
      }

    @PreRemove
    public void preRemove() {
        reverses.stream().forEach(p -> p.setHouse(null));
        reverses.clear();
      }
}
