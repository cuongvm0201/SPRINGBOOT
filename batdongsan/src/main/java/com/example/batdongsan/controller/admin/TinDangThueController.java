package com.example.batdongsan.controller.admin;

import com.example.batdongsan.entity.TinDangBan;
import com.example.batdongsan.entity.TinDangThue;
import com.example.batdongsan.repository.DanhMucRepository;
import com.example.batdongsan.repository.LoaiNhaDatRepository;
import com.example.batdongsan.repository.TinDangThueRepository;
import com.example.batdongsan.security.BDSUserDetails;
import com.example.batdongsan.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TinDangThueController {
    @Autowired
    private TinDangThueRepository tinDangThueRepository;

    @Autowired
    private LoaiNhaDatRepository loaiNhaDatRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @GetMapping("/tindangthue")
    public String getAll(Model model) {
        return listByPage(1,model,"");
    }

    @GetMapping("/tindangthue/search")
    public String getTinDangBanSearch(Model model,@RequestParam String keyword) {
        return listByPage(1,model,keyword);
    }

    @GetMapping("/tindangthue/page/{pageNumber}")
    public String listByPage(@PathVariable("pageNumber") int pageNumber, Model model,
                             @RequestParam String keyword){
        int currentPage = pageNumber;

        Page<TinDangThue> page ;
        Pageable pageable = PageRequest.of(pageNumber-1,3);
        page = tinDangThueRepository.findAllByTitleContains(keyword,pageable);

        List<TinDangThue> tinDangThueList = page.getContent();
        if(page.getContent().isEmpty()){
            model.addAttribute("khongCoTinDang","empty");
        }
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("listTinDangThue",tinDangThueList);
        model.addAttribute("keyword",keyword);
        return "admin/tindangthue/tindangthue";
    }

    @GetMapping("/tindangthue/new")
    public String createTinTuc(Model model) {
        TinDangThue tinDangThue = new TinDangThue();
        tinDangThue.setId((int) (Math.random() * 1000));
        tinDangThue.setStartAt(LocalDateTime.now());
        model.addAttribute("categories", loaiNhaDatRepository.findLoaiNhaDatsByDanhMuc(danhMucRepository.findById(2).get()));
        model.addAttribute("tindangthueRequest", tinDangThue);
        return "admin/tindangthue/tindangthue_form";
    }

    @GetMapping("/tindangthue/edit/{id}")
    public String editTinTuc(@PathVariable(name = "id") int id, Model model) {
        TinDangThue tinDangThue = tinDangThueRepository.findById(id).get();
        model.addAttribute("categories", loaiNhaDatRepository.findLoaiNhaDatsByDanhMuc(danhMucRepository.findById(2).get()));
        model.addAttribute("tindangthueRequest", tinDangThue);
        model.addAttribute("editTitle","Edit");
        return "admin/tindangthue/tindangthue_form";
    }

    @PostMapping("/tindangthue/save")
    public String saveTinTuc(TinDangThue tinDangThue,
                             @RequestParam("mainPictureFile") MultipartFile multipartFile1,
                             @RequestParam("photo1File") MultipartFile multipartFile2,
                             @RequestParam("photo2File") MultipartFile multipartFile3,
                             @RequestParam("photo3File") MultipartFile multipartFile4) throws IOException {
        if (!multipartFile1.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
            tinDangThue.setMainPhoto(fileName);

            String uploadDir = "tindangthue-photo/" + tinDangThue.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile1);
        }
        if (!multipartFile2.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile2.getOriginalFilename());
            tinDangThue.setPhoto1(fileName);
            String uploadDir = "tindangthue-photo/" + tinDangThue.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile2);
        }
        if (!multipartFile3.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile3.getOriginalFilename());
            tinDangThue.setPhoto2(fileName);
            String uploadDir = "tindangthue-photo/" + tinDangThue.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile3);
        }
        if (!multipartFile4.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile4.getOriginalFilename());
            tinDangThue.setPhoto3(fileName);
            String uploadDir = "tindangthue-photo/" + tinDangThue.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile4);
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime end = tinDangThue.getStartAt().plus(Period.ofDays(tinDangThue.getSoNgayDang()));
        tinDangThue.setFinishAt(end);
        tinDangThue.setUser(((BDSUserDetails) principal).getUser());
        tinDangThueRepository.save(tinDangThue);
        return "redirect:/admin/tindangthue";
    }


    @GetMapping("/tindangthue/detail/{id}")
    public String detailTinTuc(@PathVariable(name = "id") int id, Model model) {
        TinDangThue tinDangThue = tinDangThueRepository.findById(id).get();
        model.addAttribute("tindangthue", tinDangThue);
        return "admin/tindangthue/detail_tindangthue";
    }


    @GetMapping("/tindangthue/delete/{id}")
    public String deleteTinTuc(@PathVariable(name = "id") int id, Model model) {
        tinDangThueRepository.deleteById(id);
        return "redirect:/admin/tindangthue";
    }

}
