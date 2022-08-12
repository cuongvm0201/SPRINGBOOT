package com.example.batdongsan.controller;

import com.example.batdongsan.entity.TinDangBan;
import com.example.batdongsan.entity.TinDangStatus;
import com.example.batdongsan.entity.TinDangThue;
import com.example.batdongsan.exception.NotFoundException;
import com.example.batdongsan.repository.DanhMucRepository;
import com.example.batdongsan.repository.LoaiNhaDatRepository;
import com.example.batdongsan.repository.TinDangBanRepository;
import com.example.batdongsan.repository.TinDangThueRepository;
import com.example.batdongsan.request.TimKiemRequest;
import com.example.batdongsan.service.TimKiemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private TinDangBanRepository tinDangBanRepository;

    @Autowired
    private TinDangThueRepository tinDangThueRepository;

    @Autowired
    private LoaiNhaDatRepository loaiNhaDatRepository;

    @Autowired
    private TimKiemService timKiemService;

    @GetMapping("/trang-chu")
    public String homePage(Model model) {
        model.addAttribute("timKiemRequest", new TimKiemRequest());
        model.addAttribute("loaiNhaDat", loaiNhaDatRepository.findAll());
        return "home/index";
    }

    @GetMapping ("/trang-chu/timkiem")
    public String timKiemFirstPage(Model model,TimKiemRequest timKiemRequest ,BindingResult result){
        return timKiemPage(1,model,timKiemRequest,result);
    }

    @GetMapping ("/trang-chu/timkiem/{pageNumber}")
    public String timKiemPage(@PathVariable("pageNumber") int pageNumber,Model model,@Valid TimKiemRequest timKiemRequest, BindingResult result) {
        int currentPage = pageNumber;
        if (timKiemRequest.getLoaiNha_id().isEmpty() || timKiemRequest.getMuaGia().isEmpty() || timKiemRequest.getDienTich().isEmpty()) {
            model.addAttribute("loaiNhaDat", loaiNhaDatRepository.findAll());
            result.addError(new FieldError("timKiemRequest", "all", "Vui lòng điền đủ thông tin 3 trường loại nhà mức giá diện tích"));
            return "home/index";
        }

        if(result.hasErrors()){
            return "home/index";
        }


        if(loaiNhaDatRepository.findById(Integer.parseInt(timKiemRequest.getLoaiNha_id())).get().getDanhMuc().getId() == 1){
            Page<TinDangBan> page;
            Pageable pageable = PageRequest.of(pageNumber - 1, 2);
            page = timKiemService.timKiemTinDangBan(timKiemRequest,pageable);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("listTinDangBan", page.getContent());
            if(page.getContent().isEmpty()){
                model.addAttribute("khongCoTinDang","empty");
            }
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("thanhPho",timKiemRequest.getThanhPho());
            model.addAttribute("quanHuyen",timKiemRequest.getQuanHuyen());
            model.addAttribute("phuongXa",timKiemRequest.getPhuongXa());
            model.addAttribute("keyword",timKiemRequest.getKeyword());
            model.addAttribute("loaiNha_id",timKiemRequest.getLoaiNha_id());
            model.addAttribute("mucGia",timKiemRequest.getMuaGia());
            model.addAttribute("dienTich",timKiemRequest.getDienTich());
            return "home/productBan-search";
        }else{
            Page<TinDangThue> page;
            Pageable pageable = PageRequest.of(pageNumber - 1, 2);
            page = timKiemService.timKiemTinDangThue(timKiemRequest,pageable);
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();
            model.addAttribute("listTinDangThue", page.getContent());
            if(page.getContent().isEmpty()){
                model.addAttribute("khongCoTinDang","empty");
            }
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("thanhPho",timKiemRequest.getThanhPho());
            model.addAttribute("quanHuyen",timKiemRequest.getQuanHuyen());
            model.addAttribute("phuongXa",timKiemRequest.getPhuongXa());
            model.addAttribute("keyword",timKiemRequest.getKeyword());
            model.addAttribute("loaiNha_id",timKiemRequest.getLoaiNha_id());
            model.addAttribute("mucGia",timKiemRequest.getMuaGia());
            model.addAttribute("dienTich",timKiemRequest.getDienTich());
            return "home/productThue-search";
        }
    }

    @GetMapping("/trang-chu/tindangban")
    public String tinDangBanPage(Model model) {
        return tindangPage(1, model);
    }

    @GetMapping("/trang-chu/tindangban/{pageNumber}")
    public String tindangPage(@PathVariable("pageNumber") int pageNumber, Model model) {
        int currentPage = pageNumber;
        Page<TinDangBan> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 4);
        page = tinDangBanRepository.findAllByTinDangStatus(TinDangStatus.PUBLIC, pageable);

        List<TinDangBan> tinDangBanList = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listTinDangBan", tinDangBanList);
        return "home/product-tindangban";
    }

    @GetMapping("/trang-chu/tindangban/detail/{id}")
    public String detailTinDangBan(@PathVariable int id, Model model) {
        TinDangBan tinDangBan = tinDangBanRepository.findById(id).orElseThrow(() -> new NotFoundException("No data!"));
        model.addAttribute("tindangban", tinDangBan);
        return "home/productitem-tindangban";
    }

    @GetMapping("/trang-chu/tindangban/filterGiaBan/{pageNumber}")
    public String filterTDBgiaBan(@PathVariable("pageNumber") int pageNumber, @RequestParam("from") double from, @RequestParam("to") double to, Model model) {
        int currentPage = pageNumber;
        Page<TinDangBan> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 2);
        page = tinDangBanRepository.findAllByGiaBanBetweenAndTinDangStatus(from, to, TinDangStatus.PUBLIC, pageable);

        List<TinDangBan> tinDangBanList = page.getContent();
        if(page.getContent().isEmpty()){
            model.addAttribute("khongCoTinDang","empty");
        }
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listTinDangBan", tinDangBanList);
        return "home/product-tindangban";
    }

    @GetMapping("/trang-chu/tindangban/filterDienTich/{pageNumber}")
    public String filterTDBdienTich(@PathVariable("pageNumber") int pageNumber, @RequestParam("from") double from, @RequestParam("to") double to, Model model) {
        int currentPage = pageNumber;
        Page<TinDangBan> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 2);
        page = tinDangBanRepository.findAllByDienTichBetweenAndTinDangStatus(from, to, TinDangStatus.PUBLIC, pageable);

        List<TinDangBan> tinDangBanList = page.getContent();
        if(page.getContent().isEmpty()){
            model.addAttribute("khongCoTinDang","empty");
        }
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listTinDangBan", tinDangBanList);
        return "home/product-tindangban";
    }

    // TIN DANG THUE
    @GetMapping("/trang-chu/tindangthue")
    public String tinDangThuePage(Model model) {
        return tindangThuePage(1, model);
    }

    @GetMapping("/trang-chu/tindangthue/{pageNumber}")
    public String tindangThuePage(@PathVariable("pageNumber") int pageNumber, Model model) {
        int currentPage = pageNumber;
        Page<TinDangThue> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 4);
        page = tinDangThueRepository.findAllByTinDangStatus(TinDangStatus.PUBLIC, pageable);

        List<TinDangThue> tinDangThueList = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listTinDangThue", tinDangThueList);
        return "home/product-tindangthue";
    }

    @GetMapping("/trang-chu/tindangthue/filterGiaBan/{pageNumber}")
    public String filterTDTgiaBan(@PathVariable("pageNumber") int pageNumber, @RequestParam("from") double from, @RequestParam("to") double to, Model model) {
        int currentPage = pageNumber;
        Page<TinDangThue> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 2);
        page = tinDangThueRepository.findAllByGiaBanBetweenAndTinDangStatus(from, to, TinDangStatus.PUBLIC, pageable);

        List<TinDangThue> tinDangBanList = page.getContent();
        if(page.getContent().isEmpty()){
            model.addAttribute("khongCoTinDang","empty");
        }
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listTinDangThue", tinDangBanList);
        return "home/product-tindangthue";
    }

    @GetMapping("/trang-chu/tindangthue/filterDienTich/{pageNumber}")
    public String filterTDTdienTich(@PathVariable("pageNumber") int pageNumber, @RequestParam("from") double from, @RequestParam("to") double to, Model model) {
        int currentPage = pageNumber;
        Page<TinDangThue> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 2);
        page = tinDangThueRepository.findAllByDienTichBetweenAndTinDangStatus(from, to, TinDangStatus.PUBLIC, pageable);

        List<TinDangThue> tinDangBanList = page.getContent();
        if(page.getContent().isEmpty()){
            model.addAttribute("khongCoTinDang","empty");
        }
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listTinDangThue", tinDangBanList);
        return "home/product-tindangthue";
    }


    @GetMapping("/trang-chu/tindangthue/detail/{id}")
    public String detailTinDangThue(@PathVariable int id, Model model) {
        TinDangThue tinDangThue = tinDangThueRepository.findById(id).orElseThrow(() -> new NotFoundException("No data!"));
        model.addAttribute("tindangthue", tinDangThue);
        return "home/productitem-tindangthue";
    }

    @GetMapping("/trang-chu/about")
    public String aboutPage() {
        return "home/about";
    }

    @GetMapping("/trang-chu/tintuc")
    public String newsPage() {
        return "home/news";
    }
}
