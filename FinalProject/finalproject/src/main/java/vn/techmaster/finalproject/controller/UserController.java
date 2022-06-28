package vn.techmaster.finalproject.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.finalproject.dto.UserDTO;
import vn.techmaster.finalproject.hash.Hashing;
import vn.techmaster.finalproject.model.User;
import vn.techmaster.finalproject.repository.UserRepo;
import vn.techmaster.finalproject.request.UpdatePasswordRequest;
import vn.techmaster.finalproject.request.UserRequest;
import vn.techmaster.finalproject.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired private UserRepo userRepo;
    @Autowired private UserService userService;
    @Autowired private Hashing hash;
    @GetMapping("/getalluser")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userRepo.findAll());
    }

    @GetMapping("findbyid/{id}")
    public String getUserDetailByID(Model model, HttpSession session, @PathVariable String id){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        User currentUser = userRepo.findById(id).get();
        
        model.addAttribute("userRequest",
        new UserRequest(currentUser.getId(),currentUser.getFullname(),currentUser.getEmail(),currentUser.getMobile(),currentUser.getAddress(),currentUser.getCity()));
        
        return "user_detail";
    }

    @PostMapping("findbyid/edit")
    public String postMethodName(@Valid @ModelAttribute("userRequest") UserRequest userRequest, HttpSession session, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "user_detail";
        }
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        User currentUser = userRepo.findById(userRequest.getId()).get();
        User updateUser = User.builder()
        .id(userRequest.getId())
        .fullname(userRequest.getFullname())
        .email(userRequest.getEmail())
        .mobile(userRequest.getMobile())
        .address(userRequest.getAddress())
        .city(userRequest.getCity())
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

    @GetMapping("findbyid/updatepass/{id}")
    public String updatePass(Model model, HttpSession session, @PathVariable String id){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        model.addAttribute("updatePass", new UpdatePasswordRequest(id,"",""));
        return "change_password";
    }
    
    
    @PostMapping("findbyid/updatepass")
    public String updatePass(@Valid @ModelAttribute("updatePass") UpdatePasswordRequest updatePasswordRequest,
    BindingResult result, HttpSession session, Model model){
        if (result.hasErrors()) {
            return "redirect:/user/findbyid/updatepass/" + updatePasswordRequest.getId();
        }
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        User currentUser = userRepo.findById(updatePasswordRequest.getId()).get();
        if(!currentUser.getHashed_password().equals(hash.hashPassword(updatePasswordRequest.getOldPassword()))){
            result.addError(new FieldError("updatePass", "oldpassword", "Mật khẩu cũ không chính xác"));
        }

        if(updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getOldPassword())){
            result.addError(new FieldError("updatePass", "newpassword", "Mật khẩu mới không được giống mật khẩu cũ"));
        }

        userService.updatePassword(currentUser.getId(),updatePasswordRequest);
        
        
        return "redirect:/user/findbyid/" + updatePasswordRequest.getId();
    }
    
}
