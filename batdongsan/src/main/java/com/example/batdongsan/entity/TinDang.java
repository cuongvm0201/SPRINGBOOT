package com.example.batdongsan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class TinDang {
    @Id
    private int id;

    private String title;

    private String thanhPho;

    private String quanHuyen;

    private String phuong;

    private String diaChi;

    private double dienTich;

    @Column(name = "mo_ta", columnDefinition = "LONGTEXT")
    private String moTa;

    private LocalDateTime startAt;

    private LocalDateTime updateAt;

    private int SoNgayDang;

    private LocalDateTime finishAt;

    private String sdt;

    private TinDangStatus tinDangStatus;

    @Column(name = "main_photo")
    private String mainPhoto;

    @Column(name = "photo_1")
    private String photo1;

    @Column(name = "photo_2")
    private String photo2;

    @Column(name = "photo_3")
    private String photo3;


}
