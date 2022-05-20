package vn.techmaster.login_authentication.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.login_authentication.dto.UserDTO;
import vn.techmaster.login_authentication.exception.UserException;
import vn.techmaster.login_authentication.model.State;
import vn.techmaster.login_authentication.model.User;
import vn.techmaster.login_authentication.repository.ActiveRepo;
import vn.techmaster.login_authentication.repository.UserRepo;
import vn.techmaster.login_authentication.request.ActiveRequest;
import vn.techmaster.login_authentication.request.LoginRequest;
import vn.techmaster.login_authentication.request.RegisterRequest;
import vn.techmaster.login_authentication.service.EmailService;
import vn.techmaster.login_authentication.service.UserService;
import vn.techmaster.login_authentication.util.Utils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
   
   

    @GetMapping
    public String showHomePage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        if (userDTO != null) {
            model.addAttribute("user", userDTO);
        }
        return "index";
    }

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
            user = userService.login(loginRequest.email(), loginRequest.password());
            session.setAttribute("user", new UserDTO(user.getId(), user.getFullname(), user.getEmail()));
            return "redirect:/";
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
        model.addAttribute("registerrequest", new RegisterRequest ("","","",""));
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@Valid @ModelAttribute("registerrequest") RegisterRequest registerRequest,BindingResult result){
        if(!registerRequest.password().equals(registerRequest.confimPassword())){
            result.addError(new FieldError("registerrequest", "confimPassword", "Mật khẩu không trùng nhau"));
            return "register";
        }
        if (result.hasErrors()) {
            return "register";
        }
        User user;
        try {
            userService.addUser(registerRequest.fullname(),registerRequest.email(),registerRequest.password());
        }catch (UserException e){
            result.addError(new FieldError("register", "email", e.getMessage()));
            return "register";
        }
        return "redirect:/";
    }

    @GetMapping("/validate/{register-code}")
    public String validateUser(@PathVariable("register-code")String code ){
        userRepo.checkValidate(code);
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
