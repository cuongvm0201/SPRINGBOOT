package vn.techmaster.finalproject.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.finalproject.dto.UserDTO;
import vn.techmaster.finalproject.exception.UserException;
import vn.techmaster.finalproject.model.User;
import vn.techmaster.finalproject.repository.HouseRepo;
import vn.techmaster.finalproject.request.LoginRequest;
import vn.techmaster.finalproject.request.RegisterRequest;
import vn.techmaster.finalproject.service.UserService;

@Controller
@RequestMapping(value = "/api/v1/security")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private HouseRepo houseRepo;
    @GetMapping("login")
    public String showLogin(Model model) {
        model.addAttribute("loginrequest", new LoginRequest("", ""));
        return "login";
    }

    @PostMapping("login")
    public String handleLogin(@Valid @ModelAttribute("loginrequest") LoginRequest loginRequest,
            BindingResult result,
            HttpSession session, Model model) {
        if (result.hasErrors()) {
            return "login";
        }
        User user;
        try {
            user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            model.addAttribute("houses", houseRepo.findAll());
            session.setAttribute("user",
                    new UserDTO(user.getId(), user.getFullname(), user.getEmail(), user.getRole()));
                    
            return "redirect:/api/v1/house";
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
        if(userService.checkEmail(registerRequest.getEmail()) == true) {
            result.addError(new FieldError("registerrequest", "email", "Email đã tồn tại"));
            return "register";
        }
                
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


}
