package vn.techmaster.finalproject.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class Bill {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user; //Mỗi hóa đơn phải gắn vào một user
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Reverse reverse; //Mỗi hóa đơn phải gắn vào 1 lịch sử đặt phòng
    private Long reverseDay;
    private Long totalPrice;
    private LocalDateTime creatAt;
    
}
