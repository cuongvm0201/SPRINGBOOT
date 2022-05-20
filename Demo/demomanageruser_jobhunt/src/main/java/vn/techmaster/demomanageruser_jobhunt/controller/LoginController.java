package vn.techmaster.demomanageruser_jobhunt.controller;

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

import vn.techmaster.demomanageruser_jobhunt.dto.UserDTO;
import vn.techmaster.demomanageruser_jobhunt.exception.UserException;
import vn.techmaster.demomanageruser_jobhunt.model.Job;
import vn.techmaster.demomanageruser_jobhunt.model.JobResponse;
import vn.techmaster.demomanageruser_jobhunt.model.Roles;
import vn.techmaster.demomanageruser_jobhunt.model.State;
import vn.techmaster.demomanageruser_jobhunt.model.User;
import vn.techmaster.demomanageruser_jobhunt.repository.ApplicantRepo;
import vn.techmaster.demomanageruser_jobhunt.repository.EmployerRepo;
import vn.techmaster.demomanageruser_jobhunt.repository.JobRepo;
import vn.techmaster.demomanageruser_jobhunt.repository.UserRepo;
import vn.techmaster.demomanageruser_jobhunt.request.LoginRequest;
import vn.techmaster.demomanageruser_jobhunt.request.RegisterRequest;
import vn.techmaster.demomanageruser_jobhunt.request.SearchRequest;
import vn.techmaster.demomanageruser_jobhunt.service.EmailService;
import vn.techmaster.demomanageruser_jobhunt.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JobRepo jobRepo;
    @Autowired
    private EmployerRepo empRepo;
    @Autowired
    private ApplicantRepo applicantRepo;

    @GetMapping
    public String showHomePage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        if (userDTO != null) {
            model.addAttribute("jobs",jobRepo.getAll());
            model.addAttribute("user", userDTO);
            model.addAttribute("searchRequest", new SearchRequest());
            model.addAttribute("employers", empRepo.getMapEmp());
            model.addAttribute("applicant_count", applicantRepo.countApplicant());
        }
        return "index";
    }

    @GetMapping(value = "/search")
    public String searchKeyCity(@RequestBody @ModelAttribute("searchRequest") SearchRequest searchRequest, Model model,
            HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO != null) {
            model.addAttribute("user", userDTO);
            model.addAttribute("jobs", jobRepo.searchKeyCity(searchRequest));
            model.addAttribute("employers", empRepo.getMapEmp());
            model.addAttribute("applicant_count", applicantRepo.countApplicant());
        }
        return "index";
    }

    @GetMapping(value = "job/admin/{id}")
  public String showJobDetailByIDAdmin(Model model, @PathVariable String id,HttpSession session) {
    Job job = jobRepo.findById(id);
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    model.addAttribute("user", userDTO);
    model.addAttribute("job", job);
    model.addAttribute("employer", empRepo.findById(job.getEmp_id()));
    model.addAttribute("applicants", applicantRepo.findByJobId(id));
    return "job";
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
            session.setAttribute("user",
                    new UserDTO(user.getId(), user.getFullname(), user.getEmail(), user.getRole()));
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
        model.addAttribute("registerrequest", new RegisterRequest("", "", "", ""));
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@Valid @ModelAttribute("registerrequest") RegisterRequest registerRequest,
            BindingResult result) {
        if (!registerRequest.password().equals(registerRequest.confimPassword())) {
            result.addError(new FieldError("registerrequest", "confimPassword", "Mật khẩu không trùng nhau"));
            return "register";
        }
        if (result.hasErrors()) {
            return "register";
        }
        User user;
        try {
            userService.addUser(registerRequest.fullname(), registerRequest.email(), registerRequest.password());
        } catch (UserException e) {
            result.addError(new FieldError("register", "email", e.getMessage()));
            return "register";
        }
        return "redirect:/";
    }

    @GetMapping("/validate/{register-code}")
    public String validateUser(@PathVariable("register-code") String code) {
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
