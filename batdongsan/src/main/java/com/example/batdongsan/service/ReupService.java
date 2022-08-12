package com.example.batdongsan.service;

import com.example.batdongsan.entity.TinDangBan;
import com.example.batdongsan.entity.TinDangStatus;
import com.example.batdongsan.entity.TinDangThue;
import com.example.batdongsan.repository.TinDangBanRepository;
import com.example.batdongsan.repository.TinDangThueRepository;
import com.example.batdongsan.request.ReupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;

@Service
public class ReupService {
    @Autowired
    private TinDangBanRepository tinDangBanRepository;

    @Autowired
    private TinDangThueRepository tinDangThueRepository;


    public void reupTinDangBan(ReupRequest reupRequest) {
        TinDangBan tinDangBan = tinDangBanRepository.findById(reupRequest.getId()).get();

        tinDangBan.setTinDangStatus(TinDangStatus.PUBLIC);
        tinDangBan.setSoNgayDang(tinDangBan.getSoNgayDang() + reupRequest.getSoNgay());
        tinDangBan.setChiPhi(tinDangBan.getChiPhi() + reupRequest.getSoTien());
        tinDangBan.setFinishAt(LocalDateTime.now().plus(Period.ofDays(reupRequest.getSoNgay())));

        tinDangBanRepository.save(tinDangBan);
    }

    public void reupTinDangThue(ReupRequest reupRequest) {
        TinDangThue tinDangthue = tinDangThueRepository.findById(reupRequest.getId()).get();

        tinDangthue.setTinDangStatus(TinDangStatus.PUBLIC);
        tinDangthue.setSoNgayDang(tinDangthue.getSoNgayDang() + reupRequest.getSoNgay());
        tinDangthue.setChiPhi(tinDangthue.getChiPhi() + reupRequest.getSoTien());
        tinDangthue.setFinishAt(LocalDateTime.now().plus(Period.ofDays(reupRequest.getSoNgay())));

        tinDangThueRepository.save(tinDangthue);
    }
}
