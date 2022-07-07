package vn.techmaster.finalproject.model;

import java.time.LocalDate;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reverse {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user; //Mỗi lệnh đặt phòng phải gắn vào một user
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private House house; //Mỗi lệnh đặt phòng phải gắn vào một house

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "reverse_id")
    private List<Bill> bills = new ArrayList<>();

    private LocalDate checkin;
    private LocalDate checkout;
}
