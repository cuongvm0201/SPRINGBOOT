package com.example.batdongsan.controller;

import com.example.batdongsan.entity.TinDangBan;
import com.example.batdongsan.entity.TinDangStatus;
import com.example.batdongsan.entity.User;
import com.example.batdongsan.repository.DanhMucRepository;
import com.example.batdongsan.repository.LoaiNhaDatRepository;
import com.example.batdongsan.repository.TinDangBanRepository;
import com.example.batdongsan.request.ReupRequest;
import com.example.batdongsan.security.BDSUserDetails;
import com.example.batdongsan.service.ReupService;
import com.example.batdongsan.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;


@Controller
@RequestMapping("/dangtin")
public class DangTinBanController {
    @Autowired
    private TinDangBanRepository tinDangBanRepository;


    @Autowired
    private LoaiNhaDatRepository loaiNhaDatRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private ReupService reupService;
    @GetMapping("/dangtinban")
    public String homePage(Model model) {
        return listByPage(1, model, "");
    }

    @GetMapping("/dangtinban/search")
    public String getTinDangBanSearch(Model model, @RequestParam String keyword) {
        return listByPage(1, model, keyword);
    }


    @GetMapping("/dangtinban/page/{pageNumber}")
    public String listByPage(@PathVariable("pageNumber") int pageNumber, Model model,
                             @RequestParam String keyword) {
        int currentPage = pageNumber;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (((BDSUserDetails) principal).getUser());
        Page<TinDangBan> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 3);
        page = tinDangBanRepository.findAllByTinDangStatusAndUserAndTitleContains(TinDangStatus.PUBLIC,user,keyword,pageable);

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
        model.addAttribute("keyword", keyword);
        return "/dangtin/tinbanpublic";
    }

    @GetMapping("/dangtinban/new")
    public String createTinTuc(Model model) {
        TinDangBan tinDangBan = new TinDangBan();
        tinDangBan.setId((int) (Math.random() * 1000));
        tinDangBan.setTinDangStatus(TinDangStatus.PUBLIC);
        tinDangBan.setStartAt(LocalDateTime.now());
        model.addAttribute("categories", loaiNhaDatRepository.findLoaiNhaDatsByDanhMuc(danhMucRepository.findById(1).get()));
        model.addAttribute("tindangbanRequest", tinDangBan);
        return "dangtin/tinbanform";
    }

    @GetMapping("/dangtinban/edit/{id}")
    public String editTinTuc(@PathVariable(name = "id") int id, Model model) {
        TinDangBan tinDangBan = tinDangBanRepository.findById(id).get();
        model.addAttribute("categories", loaiNhaDatRepository.findLoaiNhaDatsByDanhMuc(danhMucRepository.findById(1).get()));
        model.addAttribute("tindangbanRequest", tinDangBan);
        model.addAttribute("editTitle","Edit");
        return "dangtin/tinbanform";
    }

    @PostMapping("/dangtinban/save")
    public String saveTinTuc(TinDangBan tinDangBan,
                             @RequestParam("mainPictureFile") MultipartFile multipartFile1,
                             @RequestParam("photo1File") MultipartFile multipartFile2,
                             @RequestParam("photo2File") MultipartFile multipartFile3,
                             @RequestParam("photo3File") MultipartFile multipartFile4,Model model) throws IOException {
        if (!multipartFile1.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
            tinDangBan.setMainPhoto(fileName);

            String uploadDir = "tindangban-photo/" + tinDangBan.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile1);
        }
        if (!multipartFile2.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile2.getOriginalFilename());
            tinDangBan.setPhoto1(fileName);
            String uploadDir = "tindangban-photo/" + tinDangBan.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile2);
        }
        if (!multipartFile3.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile3.getOriginalFilename());
            tinDangBan.setPhoto2(fileName);
            String uploadDir = "tindangban-photo/" + tinDangBan.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile3);
        }
        if (!multipartFile4.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile4.getOriginalFilename());
            tinDangBan.setPhoto3(fileName);
            String uploadDir = "tindangban-photo/" + tinDangBan.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile4);
        }



        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime end = tinDangBan.getStartAt().plus(Period.ofDays(tinDangBan.getSoNgayDang()));
        tinDangBan.setFinishAt(end);
        tinDangBan.setUser(((BDSUserDetails) principal).getUser());
        tinDangBanRepository.save(tinDangBan);
        return "redirect:/dangtin/dangtinban";
    }
    @GetMapping("/dangtinban/delete/{id}")
    public String deleteTinTuc(@PathVariable(name = "id") int id, Model model) {
        tinDangBanRepository.deleteById(id);
        return "redirect:/dangtin/dangtinban";
    }

    @GetMapping("/dangtinbanprivate")
    public String homePrivatePage(Model model) {
        return listByPagePrivate(1, model, "");
    }

    @GetMapping("/dangtinbanprivate/search")
    public String getTinDangBanPrivateSearch(Model model, @RequestParam String keyword) {
        return listByPagePrivate(1, model, keyword);
    }


    @GetMapping("/dangtinbanprivate/page/{pageNumber}")
    public String listByPagePrivate(@PathVariable("pageNumber") int pageNumber, Model model,
                             @RequestParam String keyword) {
        int currentPage = pageNumber;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (((BDSUserDetails) principal).getUser());
        Page<TinDangBan> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 3);
        page = tinDangBanRepository.findAllByTinDangStatusAndUserAndTitleContains(TinDangStatus.PRIVATE,user,keyword,pageable);

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
        model.addAttribute("keyword", keyword);
        return "/dangtin/tinbanprivate";
    }
    @GetMapping("/dangtinban/reup/{id}")
    public String pageReup(@PathVariable int id,Model model){
        ReupRequest reupRequest = new ReupRequest();
        reupRequest.setId(id);
        model.addAttribute("reupRequest",reupRequest);
        return "dangtin/ReupTinBan";
    }
    @PostMapping("dangtinban/reup")
    public String reupTin(ReupRequest reupRequest){
        reupService.reupTinDangBan(reupRequest);
        return "redirect:/dangtin/dangtinban";
    }



}
