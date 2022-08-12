package com.example.batdongsan.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class TinDangThue extends TinDang{

    private int soTang;

    private double giaBan;

    private int soPhongNgu;

    private int soPhongToilet;

    private String noiThat;

    private double chiPhi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loaiNhaDat_id")
    private LoaiNhaDat loaiNhaDat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Transient
    public String getPhotosMainPath() {
        return "/tindangthue-photo/" + super.getId() + "/" + super.getMainPhoto();
    }
    @Transient
    public String getPhoto1Path() {
        return "/tindangthue-photo/" + super.getId() + "/" + super.getPhoto1();
    }
    @Transient
    public String getPhoto2Path() {
        return "/tindangthue-photo/" + super.getId() + "/" + super.getPhoto2();
    }

    @Transient
    public String getPhoto3Path() {
        return "/tindangthue-photo/" + super.getId() + "/" + super.getPhoto3();
    }


    public TinDangThue() {
    }


    public TinDangThue(int id, String title, String thanhPho, String quanHuyen, String phuong, String diaChi, double dienTich, String moTa, LocalDateTime startAt, LocalDateTime updateAt, int SoNgayDang, LocalDateTime finishAt, String sdt, TinDangStatus tinDangStatus, String mainPhoto, String photo1, String photo2, String photo3, int soTang, double giaBan, int soPhongNgu, int soPhongToilet, String noiThat, double chiPhi, LoaiNhaDat loaiNhaDat, User user) {
        super(id, title, thanhPho, quanHuyen, phuong, diaChi, dienTich, moTa, startAt, updateAt, SoNgayDang, finishAt, sdt, tinDangStatus, mainPhoto, photo1, photo2, photo3);
        this.soTang = soTang;
        this.giaBan = giaBan;
        this.soPhongNgu = soPhongNgu;
        this.soPhongToilet = soPhongToilet;
        this.noiThat = noiThat;
        this.chiPhi = chiPhi;
        this.loaiNhaDat = loaiNhaDat;
        this.user = user;
    }
}
