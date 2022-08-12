package com.example.batdongsan.service;

import com.example.batdongsan.entity.TinDangBan;
import com.example.batdongsan.entity.TinDangStatus;
import com.example.batdongsan.entity.TinDangThue;
import com.example.batdongsan.repository.TinDangBanRepository;
import com.example.batdongsan.repository.TinDangThueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
@Slf4j
public class ScanService {
    @Autowired
    private TinDangBanRepository tinDangBanRepository;

    @Autowired
    private TinDangThueRepository tinDangThueRepository;


    @Scheduled(cron = "*/30 * * * * *")
    private  void  scanTinDangBan(){
        List<TinDangBan> listTinDangban = tinDangBanRepository.findAllByTinDangStatus(TinDangStatus.PUBLIC);
        LocalDateTime timeNow = LocalDateTime.now();
        for (int i = 0; i < listTinDangban.size(); i++) {
           if(timeNow.isAfter(listTinDangban.get(i).getFinishAt())){
               listTinDangban.get(i).setTinDangStatus(TinDangStatus.PRIVATE);
           }
        }
        tinDangBanRepository.saveAll(listTinDangban);
        // log
        String time  = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        log.info("Scan Tin Dang Ban ${}",time);
    }

    @Scheduled(cron = "*/30 * * * * *")
    private  void  scanTinDangThue(){
        List<TinDangThue> listTinDangThue = tinDangThueRepository.findAllByTinDangStatus(TinDangStatus.PUBLIC);
        LocalDateTime timeNow = LocalDateTime.now();
        for (int i = 0; i < listTinDangThue.size(); i++) {
            if(timeNow.isAfter(listTinDangThue.get(i).getFinishAt())){
                listTinDangThue.get(i).setTinDangStatus(TinDangStatus.PRIVATE);
            }
        }
        tinDangThueRepository.saveAll(listTinDangThue);
        // log
        String time  = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        log.info("Scan Tin Dang Thue ${}",time);
    }


}
