package vn.techmaster.demomanageruser_jobhunt.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.demomanageruser_jobhunt.dto.UserDTO;
import vn.techmaster.demomanageruser_jobhunt.exception.UserException;
import vn.techmaster.demomanageruser_jobhunt.model.User;
import vn.techmaster.demomanageruser_jobhunt.repository.UserRepo;
import vn.techmaster.demomanageruser_jobhunt.request.RegisterRequest;
import vn.techmaster.demomanageruser_jobhunt.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired private UserRepo userRepo;
    @Autowired private UserService userService;
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

    @GetMapping("create")
    public String showRegister(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        if (userDTO != null) {
            model.addAttribute("registerrequest", new RegisterRequest ("","","",""));
        }
        
        return "create";
    }

    @PostMapping("create")
    public String registerUser(@Valid @ModelAttribute("registerrequest") RegisterRequest registerRequest,BindingResult result){
        if(!registerRequest.password().equals(registerRequest.confimPassword())){
            result.addError(new FieldError("registerrequest", "confimPassword", "Mật khẩu không trùng nhau"));
            return "create";
        }
        if (result.hasErrors()) {
            return "create";
        }
        User user;
        try {
            userService.addUserThenAutoActivate(registerRequest.fullname(),registerRequest.email(),registerRequest.password());
        }catch (UserException e){
            result.addError(new FieldError("register", "email", e.getMessage()));
            return "create";
        }
        return "redirect:/admin";
    }
}
