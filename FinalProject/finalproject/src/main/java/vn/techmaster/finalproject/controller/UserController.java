package vn.techmaster.finalproject.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;


import vn.techmaster.finalproject.dto.UserDTO;
import vn.techmaster.finalproject.hash.Hashing;
import vn.techmaster.finalproject.model.Reverse;
import vn.techmaster.finalproject.model.Roles;
import vn.techmaster.finalproject.model.State;
import vn.techmaster.finalproject.model.User;
import vn.techmaster.finalproject.repository.UserRepo;
import vn.techmaster.finalproject.request.ContactRequest;
import vn.techmaster.finalproject.request.ForgotRequest;
import vn.techmaster.finalproject.request.UpdatePasswordRequest;
import vn.techmaster.finalproject.request.UserRequest;
import vn.techmaster.finalproject.service.BillService;
import vn.techmaster.finalproject.service.ContactService;
import vn.techmaster.finalproject.service.ReverseService;
import vn.techmaster.finalproject.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired private UserRepo userRepo;
    @Autowired private UserService userService;
    @Autowired private Hashing hash;
    @Autowired private ReverseService reverseService;
    @Autowired private BillService billService;
    @Autowired private ContactService contactService;

    @GetMapping("findbyid/{id}")
    public String getUserDetailByID(Model model, HttpSession session, @PathVariable String id){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        User currentUser = userRepo.findById(id).get();
        
        model.addAttribute("userRequest",
        new UserRequest(currentUser.getId(),currentUser.getFullname(),currentUser.getEmail(),currentUser.getMobile(),currentUser.getAddress(),currentUser.getCity()));
        
        return "user_detail";
    }

    @GetMapping("contact")
    public String sendMessageToAdmin(Model model, HttpSession session){
        model.addAttribute("contactRequest", new ContactRequest("","","","",""));   
        return "contact";
    }

    @PostMapping("contact")
    public String sendMessageToAdmin(@Valid @ModelAttribute("contactRequest") ContactRequest contactRequest, BindingResult result){
        if (result.hasErrors()) {
            return "contact";
        }
       contactService.addNewMessage(contactRequest);
       return "redirect:/";
    }

    @PostMapping("findbyid/edit")
    public String postMethodName(@Valid @ModelAttribute("userRequest") UserRequest userRequest, HttpSession session, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "user_detail";
        }
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        User currentUser = userRepo.findById(userRequest.getId()).get();
        User updateUser = User.builder()
        .id(userRequest.getId())
        .fullname(userRequest.getFullname())
        .hashed_password(currentUser.getHashed_password())
        .email(userRequest.getEmail())
        .mobile(userRequest.getMobile())
        .address(userRequest.getAddress())
        .city(userRequest.getCity())
        .role(Roles.MEMBER)
        .state(State.ACTIVE)
        .reverses(currentUser.getReverses())
        .bills(currentUser.getBills())
        .creatAt(currentUser.getCreatAt())
        .updateAt(LocalDateTime.now())
        .build();
        userService.edit(updateUser);
        model.addAttribute("userRequest",
        new UserRequest(updateUser.getId(),updateUser.getFullname(),updateUser.getEmail(),updateUser.getMobile(),updateUser.getAddress(),updateUser.getCity()));
        return "redirect:/api/v1/user/findbyid/" + updateUser.getId();
    }


    @GetMapping("listreverse/{id}")
    public String getAllReverse(Model model, HttpSession session, @PathVariable String id){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("reverses", reverseService.findAllReverseByUserID(id));
        return "reverse_detail";
    }

    @GetMapping("listbill/{id}")
    public String getAllBill(Model model, HttpSession session, @PathVariable String id){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("bills", billService.findAllBillByUserID(id));
        return "bill_detail";
    }

    @GetMapping("findbyid/updatepass/{id}")
    public String updatePass(Model model, HttpSession session, @PathVariable String id){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("updatePass", new UpdatePasswordRequest(id,"",""));
        return "change_password";
    }

    @GetMapping("/forgotpass")
    public String forgotPass(Model model){
        model.addAttribute("forgotRequest", new ForgotRequest());
        return "forgot_password";
    }

    @PostMapping("/forgotpass")
    public String forgotPass(@Valid @ModelAttribute("forgotRequest") ForgotRequest forgotRequest, BindingResult result){
        if (result.hasErrors()) {
            return "forgot_password";
        }
        if(!userRepo.findUsersByEmail(forgotRequest.getEmail()).isPresent()){
            result.addError(new FieldError("forgotRequest", "email", "Email không đúng hoặc không tồn tại"));
            return "forgot_password";
        }

        userService.forgotPassword(forgotRequest.getEmail());
        return "redirect:/api/v1/security/login";
    }
    
    
    @PostMapping("findbyid/updatepass")
    public String updatePass(@Valid @ModelAttribute("updatePass") UpdatePasswordRequest updatePasswordRequest,
    BindingResult result, HttpSession session, Model model){
        if (result.hasErrors()) {
            return "change_password";
        }
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        User currentUser = userRepo.findById(updatePasswordRequest.id()).get();
        if(!currentUser.getHashed_password().equals(hash.hashPassword(updatePasswordRequest.oldPassword()))){
            result.addError(new FieldError("updatePass", "oldPassword", "Mật khẩu cũ không chính xác"));
            return "change_password";
        }

        if(updatePasswordRequest.newPassword().equals(updatePasswordRequest.oldPassword())){
            result.addError(new FieldError("updatePass", "newPassword", "Mật khẩu mới không được giống mật khẩu cũ"));
            return "change_password";
        }

        userService.updatePassword(currentUser.getId(),updatePasswordRequest);
        
        
        return "redirect:/api/v1/user/findbyid/" + updatePasswordRequest.id();
    }
    
}
