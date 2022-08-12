package com.example.batdongsan.entity;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
@Entity
public class TinDangBan extends TinDang {
    private double giaBan;

    private String huongNha;

    private String huongBanCong;

    private int soPhongNgu;

    private int soPhongToilet;

    private int matTien;

    private int duongVao;

    private int soTang;

    private String phapLy;

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
        return "/tindangban-photo/" + super.getId() + "/" + super.getMainPhoto();
    }
    @Transient
    public String getPhoto1Path() {
        return "/tindangban-photo/" + super.getId() + "/" + super.getPhoto1();
    }
    @Transient
    public String getPhoto2Path() {
        return "/tindangban-photo/" + super.getId() + "/" + super.getPhoto2();
    }

    @Transient
    public String getPhoto3Path() {
        return "/tindangban-photo/" + super.getId() + "/" + super.getPhoto3();
    }

    public TinDangBan() {
    }

    public TinDangBan(int id, String title, String thanhPho, String quanHuyen, String phuong, String diaChi, double dienTich, String moTa, LocalDateTime startAt, LocalDateTime updateAt, int SoNgayDang, LocalDateTime finishAt, String sdt, TinDangStatus tinDangStatus, String mainPhoto, String photo1, String photo2, String photo3, double giaBan, String huongNha, String huongBanCong, int soPhongNgu, int soPhongToilet, int matTien, int duongVao, int soTang, String phapLy, String noiThat, LoaiNhaDat loaiNhaDat, User user,double chiPhi) {
        super(id, title, thanhPho, quanHuyen, phuong, diaChi, dienTich, moTa, startAt, updateAt, SoNgayDang, finishAt, sdt, tinDangStatus, mainPhoto, photo1, photo2, photo3);
        this.giaBan = giaBan;
        this.huongNha = huongNha;
        this.huongBanCong = huongBanCong;
        this.soPhongNgu = soPhongNgu;
        this.soPhongToilet = soPhongToilet;
        this.matTien = matTien;
        this.duongVao = duongVao;
        this.soTang = soTang;
        this.phapLy = phapLy;
        this.noiThat = noiThat;
        this.loaiNhaDat = loaiNhaDat;
        this.user = user;
        this.chiPhi = chiPhi;
    }


}
