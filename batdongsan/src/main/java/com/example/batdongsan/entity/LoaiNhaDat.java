package com.example.batdongsan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoaiNhaDat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private LocalDateTime create_at;

    private LocalDateTime update_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "danh_muc_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private DanhMuc danhMuc;

    public LoaiNhaDat(Integer id, String name, LocalDateTime create_at, LocalDateTime update_at) {
        this.id = id;
        this.name = name;
        this.create_at = create_at;
        this.update_at = update_at;
    }
}
