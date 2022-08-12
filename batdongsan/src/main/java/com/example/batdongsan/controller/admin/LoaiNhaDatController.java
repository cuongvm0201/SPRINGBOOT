package com.example.batdongsan.controller.admin;

import com.example.batdongsan.repository.DanhMucRepository;
import com.example.batdongsan.repository.LoaiNhaDatRepository;
import com.example.batdongsan.entity.LoaiNhaDat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;


@Controller
@RequestMapping("/admin")
public class LoaiNhaDatController {
    @Autowired
    private LoaiNhaDatRepository loaiNhaDatRepository;
    @Autowired
    private DanhMucRepository danhMucRepository;
    @GetMapping("/loainhadat")
    public String getAll(Model model){
        model.addAttribute("listLoaiNhaDat",loaiNhaDatRepository.findAll());
        return  "admin/loainhadat/list_loainhadat";
    }
    @GetMapping("/loainhadat/new")
    public String createLoaiNhaDat(Model model){
        LoaiNhaDat lnd = new LoaiNhaDat();
        model.addAttribute("lnd",lnd);
        model.addAttribute("listDM",danhMucRepository.findAll());
        return "admin/loainhadat/loainhadat_form";
    }
    @GetMapping("/loainhadat/edit/{id}")
    public String editLoaiNhaDat(@PathVariable(name = "id") Integer id,Model model){
        LoaiNhaDat lnd = loaiNhaDatRepository.findById(id).get();
        model.addAttribute("lnd",lnd);
        model.addAttribute("listDM",danhMucRepository.findAll());
        return "admin/loainhadat/loainhadat_form";
    }
    @PostMapping("/loainhadat/save")
    public String saveLoaiNhaDat(LoaiNhaDat loaiNhaDat){
        if(loaiNhaDat.getId() == null){
            loaiNhaDat.setCreate_at(LocalDateTime.now());
            loaiNhaDatRepository.save(loaiNhaDat);
        } else {
            loaiNhaDat.setUpdate_at(LocalDateTime.now());
            loaiNhaDatRepository.save(loaiNhaDat);
        }
        return "redirect:/admin/loainhadat";
    }

    @GetMapping("/loainhadat/delete/{id}")
    public String deleteLoaiNhaDat(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes){
        try{
            loaiNhaDatRepository.deleteById(id);
        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("message", "Không thể xoá vì Loại Nhà Đất có tồn tại bài viết");
        }
        return "redirect:/admin/loainhadat";
    }

}
