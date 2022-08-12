package com.example.batdongsan;

import com.example.batdongsan.entity.LoaiNhaDat;
import com.example.batdongsan.entity.TinDangBan;
import com.example.batdongsan.entity.TinDangStatus;
import com.example.batdongsan.entity.User;
import com.example.batdongsan.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Optional;


@SpringBootApplication
public class BatdongsanApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoaiNhaDatRepository loaiNhaDatRepository;

    @Autowired
    private TinDangBanRepository tinDangBanRepository;

    @Autowired
    private TinDangThueRepository tinDangThueRepository;


    public static void main(String[] args) {
        SpringApplication.run(BatdongsanApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> user = userRepository.findById(3);
        Optional<LoaiNhaDat> loaiNhaDat = loaiNhaDatRepository.findById(1);
        TinDangBan tinDangBan = new TinDangBan(1,"Bán Nhà Bạch Mai","Hà Nội","Hai Bà Trưng","Phường bạch mai","số nhà 23 ngõ 381",36,".............", LocalDateTime.now(),LocalDateTime.now(),10,LocalDateTime.now(),"0327415594", TinDangStatus.PUBLIC,null,null,null,null,3.5,
                "Đông Bắc","Đông",3,2,10,10,3,"Sổ đỏ chính chủ","Full nội thất",loaiNhaDat.get(),user.get(),1000);
        tinDangBanRepository.save(tinDangBan);

    }

}
