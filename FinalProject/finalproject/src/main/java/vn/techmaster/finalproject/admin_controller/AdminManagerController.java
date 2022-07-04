package vn.techmaster.finalproject.admin_controller;

import java.time.LocalDateTime;
import java.util.UUID;

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
import vn.techmaster.finalproject.model.Reply;
import vn.techmaster.finalproject.model.Roles;
import vn.techmaster.finalproject.model.State;
import vn.techmaster.finalproject.model.User;
import vn.techmaster.finalproject.repository.ContactRepo;
import vn.techmaster.finalproject.request.CreatAccountRequest;
import vn.techmaster.finalproject.request.ReplyRequest;
import vn.techmaster.finalproject.request.UserRequest;
import vn.techmaster.finalproject.service.ContactService;
import vn.techmaster.finalproject.service.ReplyService;
import vn.techmaster.finalproject.service.UserService;

@Controller
@RequestMapping("/api/v1/admin")
public class AdminManagerController {
    @Autowired private ContactService contactService;
    @Autowired private ContactRepo contactRepo;
    @Autowired private ReplyService replyService;
    @Autowired private UserService userService;

    @GetMapping("/inbox")
    public String showAllInbox(Model model, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("inboxes", contactService.getAllMessage());
        return "inbox_admin";
    }

    @GetMapping("/reply/{inboxID}")
    public String replyInboxAdmin(Model model, HttpSession session, @PathVariable String inboxID){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("replyRequest", new ReplyRequest(userDTO.getId(),inboxID,""));
        return "reply_inbox_admin";
    }

    @PostMapping("/reply")
    public String replyInboxAdmin(@ModelAttribute("replyRequest") ReplyRequest replyRequest, Model model, HttpSession session ){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        String customerEmail = contactRepo.findById(replyRequest.getInboxID()).get().getEmail();
        String id = UUID.randomUUID().toString();
        Reply newReply = Reply.builder()
        .id(id)
        .adminID(replyRequest.getAdminID())
        .inboxID(replyRequest.getInboxID())
        .message(replyRequest.getMessage())
        .build();
        replyService.creatNewReply(newReply, replyRequest.getAdminID(), customerEmail);
        return "redirect:/api/v1/admin/inbox";
    }

    @GetMapping("/view-user")
    public String viewAllUser(Model model, HttpSession session ){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("listuser", userService.getAllUser());
        return "list_user_admin";
    }

    @GetMapping("/creat-user-admin")
    public String creatUserByAdmin(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("creatAccRequest", new CreatAccountRequest("", "", "", "",null,null));
        return "creat_user_admin";
    }


    @PostMapping("/creat-user-admin")
    public String creatUserByAdmin(@Valid @ModelAttribute("creatAccRequest") CreatAccountRequest creatAccountRequest,
    BindingResult result){
        if(userService.checkEmail(creatAccountRequest.getEmail()) == true) {
            result.addError(new FieldError("creatAccRequest", "email", "Email đã tồn tại"));
            return "creat_user_admin";
        }

        if (!creatAccountRequest.getPassword().equals(creatAccountRequest.getConfimPassword())) {
            result.addError(new FieldError("creatAccRequest", "confimPassword", "Mật khẩu không trùng nhau"));
            return "creat_user_admin";
        }

        if(creatAccountRequest.getState() == null) {
            result.addError(new FieldError("creatAccRequest", "state", "Trạng thái không được để trống"));
            return "creat_user_admin";
        }

        if(creatAccountRequest.getRole() == null) {
            result.addError(new FieldError("creatAccRequest", "role", "Hạng thành viên không được để trống"));
            return "creat_user_admin";
        }

        if (result.hasErrors()) {
            return "creat_user_admin";
        }

        userService.addUserByAdmin(
            creatAccountRequest.getFullname(),
             creatAccountRequest.getEmail(),
              creatAccountRequest.getPassword(),
               creatAccountRequest.getState(),
               creatAccountRequest.getRole());

        return "redirect:/api/v1/admin/view-user";
    }

    @GetMapping("/edit-user-admin/{userID}")
    public String editUserByAdmin(Model model, HttpSession session, @PathVariable String userID ){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        User currentUser = userService.findById(userID).get();
        model.addAttribute("userRequest", new UserRequest(currentUser.getId(),currentUser.getFullname(),currentUser.getEmail(),currentUser.getMobile(),currentUser.getAddress(),currentUser.getCity()));
        return "edit_user_admin";
    }

    @PostMapping("/edit-user-admin")
    public String postMethodName(@Valid @ModelAttribute("userRequest") UserRequest userRequest, HttpSession session, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "edit_user_admin";
        }
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        User currentUser = userService.findById(userRequest.getId()).get();
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
        return "redirect:/api/v1/admin/view-user";
    }

}
