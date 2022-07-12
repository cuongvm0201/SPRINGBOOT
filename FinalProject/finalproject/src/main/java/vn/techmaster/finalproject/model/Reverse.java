package vn.techmaster.finalproject.model;

import java.time.LocalDate;



import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reverse {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private House house; //Mỗi lệnh đặt phòng phải gắn vào một house

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; //Mỗi lệnh đặt phòng phải gắn vào một user

    private LocalDate checkin;
    private LocalDate checkout;

    @PreRemove
    public void preRemove() {
        this.setHouse(null);
        this.setUser(null);
      }
}
