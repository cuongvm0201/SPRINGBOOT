package com.example.batdongsan.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReupRequest {
    private  int id;
    private int soNgay;
    private long soTien;
}
