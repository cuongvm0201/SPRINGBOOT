package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserException;
import com.example.demo.model.Job;
import com.example.demo.model.Roles;
import com.example.demo.model.State;
import com.example.demo.model.User;
import com.example.demo.repository.ApplicantRepo;
import com.example.demo.repository.EmployerRepo;
import com.example.demo.repository.JobRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.request.SearchRequest;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(value = "/api")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String showLogin(Model model) {
        model.addAttribute("loginrequest", new LoginRequest("", ""));
        return "login";
    }

    @PostMapping("login")
    public String handleLogin(@Valid @ModelAttribute("loginrequest") LoginRequest loginRequest,
            BindingResult result,
            HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }
        User user;
        try {
            user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            session.setAttribute("user",
                    new UserDTO(user.getId(), user.getFullname(), user.getEmail(), user.getRole()));
            return "redirect:/job";
        } catch (UserException ex) {
            switch (ex.getMessage()) {
                case "User is not found":
                    result.addError(new FieldError("loginrequest", "email", "Email does not exist"));
                    break;
                case "User is not activated":
                    result.addError(new FieldError("loginrequest", "email", "User is not activated"));
                    break;
                case "Password is incorrect":
                    result.addError(new FieldError("loginrequest", "password", "Password is incorrect"));
                    break;
            }
            return "login";
        }
    }

    @GetMapping("register")
    public String showRegister(Model model) {
        model.addAttribute("registerrequest", new RegisterRequest("", "", "", ""));
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@Valid @ModelAttribute("registerrequest") RegisterRequest registerRequest,
            BindingResult result) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfimPassword())) {
            result.addError(new FieldError("registerrequest", "confimPassword", "Mật khẩu không trùng nhau"));
            return "register";
        }
        if (result.hasErrors()) {
            return "register";
        }
        User user;
        try {
            userService.addUser(registerRequest.getFullname(), registerRequest.getEmail(), registerRequest.getPassword());
        } catch (UserException e) {
            result.addError(new FieldError("register", "email", e.getMessage()));
            return "register";
        }
        return "redirect:/";
    }

    @GetMapping("/validate/{register-code}")
    public String validateUser(@PathVariable("register-code") String code) {
        userService.checkValidate(code);
        return "active";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("foo")
    public String foo() {
        throw new UserException("Some Error");
    }

}
