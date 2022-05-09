package vn.techmaster.login_authentication.controller;

import java.util.Optional;

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
    @Autowired
    private EmailService emailService;
    @Autowired
    private ActiveRepo activeRepo;

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
        model.addAttribute("registerrequest", new RegisterRequest ("","",""));
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@Valid @ModelAttribute("registerrequest") RegisterRequest registerRequest, Model model){
       
        // Add user pending để test exception
        String code = userService.generatedActivecode();
        userService.addUser(registerRequest.fullname(), registerRequest.email(), registerRequest.password());
        emailService.sendMail(registerRequest.email(), "Mã Kích Hoạt", "Mã kích hoạt email là: "+ code);
        activeRepo.addActive(registerRequest.email(), code);
        System.out.println(code);
        // Add user được active luôn để test login
        // userService.addUserThenAutoActivate(registerRequest.fullname(), registerRequest.email(), registerRequest.password());

        return "index";
    }

    @GetMapping(value = "active")
    public String showFormActive(Model model){
        model.addAttribute("activerequest", new ActiveRequest("",""));
        return "active";
    }

    @PostMapping(value = "active")
    public String submitActive(@Valid @ModelAttribute("activerequest") ActiveRequest activeRequest){
        // if (result.hasErrors()) {
        //     return "active";
        // }
        Optional<User> o_user = userRepo.findByEmail(activeRequest.email());
        if (!o_user.isPresent()) {
            throw new UserException("User is not found");
        }
        else if(userService.activateUser(activeRequest.email(),activeRequest.active_code()) == true){
            userService.findByEmail(activeRequest.email()).get().setState(State.ACTIVE);
            return "index";
        }
            
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
