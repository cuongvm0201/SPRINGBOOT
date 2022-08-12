package com.example.batdongsan.request;

import lombok.*;


import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimKiemRequest {
    private String thanhPho;
    private String quanHuyen;
    private String phuongXa;
    private String keyword;
    private String loaiNha_id;
    @NotBlank
    private String muaGia;
    @NotBlank
    private String dienTich;
}
