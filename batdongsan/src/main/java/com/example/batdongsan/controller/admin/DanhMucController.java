package com.example.batdongsan.controller.admin;

import com.example.batdongsan.repository.DanhMucRepository;
import com.example.batdongsan.repository.LoaiNhaDatRepository;
import com.example.batdongsan.entity.DanhMuc;
import com.example.batdongsan.entity.LoaiNhaDat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class DanhMucController {

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private LoaiNhaDatRepository loaiNhaDatRepository;
    @GetMapping("/danhmuc")
    public String getAll(Model model){
        model.addAttribute("listDM",danhMucRepository.findAll());
        return "admin/danhmuc/list_danhmuc";
    }
    @GetMapping("/danhmuc/new")
    private String createDanhmuc(Model model){
        DanhMuc dm = new DanhMuc();
        model.addAttribute("danhmuc",dm);
        return  "admin/danhmuc/danhmuc_form";
    }
    @GetMapping("/danhmuc/edit/{id}")
    private String editDanhmuc(@PathVariable(name = "id") Integer id, Model model){
        DanhMuc dm = danhMucRepository.findById(id).get();
        model.addAttribute("danhmuc",dm);
        return  "admin/danhmuc/danhmuc_form";
    }

    @PostMapping("/danhmuc/save")
    public String saveDanhMuc(DanhMuc danhMuc , RedirectAttributes redirectAttributes)  {
        if (danhMuc.getId() == null) {
            danhMucRepository.save(danhMuc);
            danhMuc.setCreate_at(LocalDateTime.now());
            redirectAttributes.addFlashAttribute("message", "Thêm Danh Mục Thành Công");
        } else {
            DanhMuc dm = danhMucRepository.findById(danhMuc.getId()).get();
            dm.setName(danhMuc.getName());
            dm.setUpdate_at(LocalDateTime.now());
            danhMucRepository.save(dm);
            redirectAttributes.addFlashAttribute("message", "Sửa Danh Mục " + danhMuc.getId() + " Thành Công");
        }
        return "redirect:/admin/danhmuc";
    }
    @GetMapping("/danhmuc/delete/{id}")
    private String deleteDanhmuc(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes){
        DanhMuc dm = danhMucRepository.findById(id).get();
        List<LoaiNhaDat> loaiNhaDatList =  loaiNhaDatRepository.findLoaiNhaDatsByDanhMuc(dm);
        for (int i = 0; i < loaiNhaDatList.size(); i++) {
            loaiNhaDatList.get(i).setDanhMuc(null);
        }
        loaiNhaDatRepository.saveAll(loaiNhaDatList);
        danhMucRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xoá Danh Mục " + id + " Thành Công");
        return "redirect:/admin/danhmuc";
    }

}
