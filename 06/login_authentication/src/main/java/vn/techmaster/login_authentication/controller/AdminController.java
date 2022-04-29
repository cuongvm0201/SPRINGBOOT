package vn.techmaster.login_authentication.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.login_authentication.dto.UserDTO;
import vn.techmaster.login_authentication.repository.UserRepo;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired private UserRepo userRepo;
    @GetMapping
    public String showListUsser(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        if (userDTO != null) {
            model.addAttribute("user", userDTO);
            model.addAttribute("allmember",userRepo.getAll());
        }
        return "admin";
    }
}
