package vn.techmaster.finalproject.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; //Mỗi hóa đơn phải gắn vào một user
    @OneToOne
    @JoinColumn(name = "reverse_id", nullable = true)
    private Reverse reverse; //Mỗi hóa đơn phải gắn vào 1 lịch sử đặt phòng
    private Long reverseDay;
    private Long totalPrice;
    private LocalDateTime creatAt;
    
    @PrePersist
    public void prePersist() {
        creatAt = LocalDateTime.now();
      }

      @PreRemove
      public void preRemove() {
          this.setReverse(null);
          this.setUser(null);
        }

}
