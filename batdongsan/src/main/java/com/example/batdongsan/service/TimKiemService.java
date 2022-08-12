package com.example.batdongsan.service;


import com.example.batdongsan.entity.LoaiNhaDat;
import com.example.batdongsan.entity.TinDangBan;
import com.example.batdongsan.entity.TinDangStatus;
import com.example.batdongsan.entity.TinDangThue;
import com.example.batdongsan.repository.LoaiNhaDatRepository;
import com.example.batdongsan.repository.TinDangBanRepository;
import com.example.batdongsan.repository.TinDangThueRepository;
import com.example.batdongsan.request.TimKiemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimKiemService {
    @Autowired
    private TinDangBanRepository tinDangBanRepository;

    @Autowired
    private LoaiNhaDatRepository loaiNhaDatRepository;

    @Autowired
    private TinDangThueRepository tinDangThueRepository;

    public Page<TinDangBan> timKiemTinDangBan(TimKiemRequest timKiemRequest, Pageable pageable) {
        if (!timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && !timKiemRequest.getQuanHuyen().isEmpty()
                && !timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangBanRepository.timKiemTinDangBan(Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), timKiemRequest.getKeyword(), timKiemRequest.getThanhPho(), timKiemRequest.getQuanHuyen(), timKiemRequest.getPhuongXa(), Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]), Integer.parseInt(timKiemRequest.getLoaiNha_id()), pageable);
        }

        if (timKiemRequest.getKeyword().isEmpty()
                && timKiemRequest.getThanhPho().isEmpty()
                && timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangBanRepository.findAllByTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween( TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (!timKiemRequest.getKeyword().isEmpty()
                && timKiemRequest.getThanhPho().isEmpty()
                && timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangBanRepository.findAllByTitleContainsOrMoTaContainsAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getKeyword(), timKiemRequest.getKeyword(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (!timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangBanRepository.findAllByTitleContainsOrMoTaContainsAndThanhPhoAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getKeyword(), timKiemRequest.getKeyword(),timKiemRequest.getThanhPho(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangBanRepository.findAllByThanhPhoAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getThanhPho(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && !timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangBanRepository.findAllByThanhPhoAndQuanHuyenAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getThanhPho(),timKiemRequest.getQuanHuyen(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && !timKiemRequest.getQuanHuyen().isEmpty()
                && !timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangBanRepository.findAllByThanhPhoAndQuanHuyenAndPhuongAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getThanhPho(),timKiemRequest.getQuanHuyen(),timKiemRequest.getPhuongXa(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (!timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && !timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangBanRepository.findAllByTitleContainsOrMoTaContainsAndThanhPhoAndQuanHuyenAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getKeyword(),timKiemRequest.getKeyword(),timKiemRequest.getThanhPho(),timKiemRequest.getQuanHuyen(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }


        return tinDangBanRepository.findAll(pageable);
    }


    public Page<TinDangThue> timKiemTinDangThue(TimKiemRequest timKiemRequest, Pageable pageable) {
        if (!timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && !timKiemRequest.getQuanHuyen().isEmpty()
                && !timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangThueRepository.timKiemTinDangBan(Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), timKiemRequest.getKeyword(), timKiemRequest.getThanhPho(), timKiemRequest.getQuanHuyen(), timKiemRequest.getPhuongXa(), Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]), Integer.parseInt(timKiemRequest.getLoaiNha_id()), pageable);
        }

        if (timKiemRequest.getKeyword().isEmpty()
                && timKiemRequest.getThanhPho().isEmpty()
                && timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return  tinDangThueRepository.findAllByTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (!timKiemRequest.getKeyword().isEmpty()
                && timKiemRequest.getThanhPho().isEmpty()
                && timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangThueRepository.findAllByTitleContainsOrMoTaContainsAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getKeyword(), timKiemRequest.getKeyword(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (!timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangThueRepository.findAllByTitleContainsOrMoTaContainsAndThanhPhoAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getKeyword(), timKiemRequest.getKeyword(),timKiemRequest.getThanhPho(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangThueRepository.findAllByThanhPhoAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getThanhPho(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && !timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangThueRepository.findAllByThanhPhoAndQuanHuyenAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getThanhPho(),timKiemRequest.getQuanHuyen(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && !timKiemRequest.getQuanHuyen().isEmpty()
                && !timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangThueRepository.findAllByThanhPhoAndQuanHuyenAndPhuongAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getThanhPho(),timKiemRequest.getQuanHuyen(),timKiemRequest.getPhuongXa(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }

        if (!timKiemRequest.getKeyword().isEmpty()
                && !timKiemRequest.getThanhPho().isEmpty()
                && !timKiemRequest.getQuanHuyen().isEmpty()
                && timKiemRequest.getPhuongXa().isEmpty() &&
                !timKiemRequest.getLoaiNha_id().isEmpty() &&
                !timKiemRequest.getMuaGia().isEmpty() &&
                !timKiemRequest.getDienTich().isEmpty()) {
            String[] dienTich = timKiemRequest.getDienTich().trim().split("-");
            String[] giaBan = timKiemRequest.getMuaGia().trim().split("-");
            return tinDangThueRepository.findAllByTitleContainsOrMoTaContainsAndThanhPhoAndQuanHuyenAndTinDangStatusAndLoaiNhaDatAndDienTichBetweenAndGiaBanBetween(timKiemRequest.getKeyword(),timKiemRequest.getKeyword(),timKiemRequest.getThanhPho(),timKiemRequest.getQuanHuyen(), TinDangStatus.PUBLIC, loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get(),Double.parseDouble(dienTich[0]), Double.parseDouble(dienTich[1]),Double.parseDouble(giaBan[0]), Double.parseDouble(giaBan[1]), pageable);
        }


        return tinDangThueRepository.findAll(pageable);
    }
}
