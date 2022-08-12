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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.techmaster.finalproject.dto.UserDTO;
import vn.techmaster.finalproject.exception.AuthorizedException;
import vn.techmaster.finalproject.exception.UserException;
import vn.techmaster.finalproject.model.Reply;
import vn.techmaster.finalproject.model.Roles;
import vn.techmaster.finalproject.model.State;
import vn.techmaster.finalproject.model.User;
import vn.techmaster.finalproject.repository.ContactRepo;
import vn.techmaster.finalproject.request.CreatAccountRequest;
import vn.techmaster.finalproject.request.LoginRequest;
import vn.techmaster.finalproject.request.ReplyRequest;
import vn.techmaster.finalproject.request.UserRequest;
import vn.techmaster.finalproject.service.ContactService;
import vn.techmaster.finalproject.service.ReplyService;
import vn.techmaster.finalproject.service.UserService;
import vn.techmaster.finalproject.service.ReverseService;
import vn.techmaster.finalproject.service.BillService;

@Controller
@RequestMapping("/api/v1/admin")
public class AdminManagerController {
    @Autowired private ContactService contactService;
    @Autowired private ContactRepo contactRepo;
    @Autowired private ReplyService replyService;
    @Autowired private UserService userService;
    @Autowired private ReverseService reverseService;
    @Autowired private BillService billService;

    @GetMapping("/")
    public String showLogin(Model model) {
        model.addAttribute("loginrequest", new LoginRequest("", ""));
        return "login_admin";
    }

    @PostMapping("/")
    public String handleLogin(@Valid @ModelAttribute("loginrequest") LoginRequest loginRequest,
            BindingResult result,
            HttpSession session, Model model) {
        if (result.hasErrors()) {
            return "login_admin";
        }
        
        User user;
        try {
            user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            if(!userService.findByEmail(loginRequest.getEmail()).get().getRole().equals(Roles.ADMIN)){
                result.addError(new FieldError("loginrequest", "email", "User is not ADMIN"));
                return "login_admin";
            }
            session.setAttribute("user",
                    new UserDTO(user.getId(), user.getFullname(), user.getEmail(), user.getState(), user.getRole()));
                    model.addAttribute("listuser", userService.getAllUser());        
                    return "redirect:/api/v1/admin/view-user";
        } catch (UserException ex) {
            switch (ex.getMessage()) {
                case "User is not found":
                    result.addError(new FieldError("loginrequest", "email", "Email does not exist"));
                    break;
                case "User is locked":
                    result.addError(new FieldError("loginrequest", "email", "Email is locked"));
                    break;
                case "User is not activated":
                    result.addError(new FieldError("loginrequest", "email", "User is not activated"));
                    break;
                case "Password is incorrect":
                    result.addError(new FieldError("loginrequest", "password", "Password is incorrect"));
                    break;
            }
            return "login_admin";
        }
    }
 

    @GetMapping("/inbox")
    public String showAllInbox(Model model, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        if(!userService.findById(userDTO.getId()).get().getRole().equals(Roles.ADMIN)){
            throw new AuthorizedException("Bạn không có quyền Admin nên không thể xem trang này !");
        }
        model.addAttribute("inboxes", contactService.getAllMessage());
        return "inbox_admin";
    }

    @GetMapping("/reply/{inboxID}")
    public String replyInboxAdmin(Model model, HttpSession session, @PathVariable String inboxID){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        if(!userService.findById(userDTO.getId()).get().getRole().equals(Roles.ADMIN)){
            throw new AuthorizedException("Bạn không có quyền Admin nên không thể xem trang này !");
        }
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
        if(!userService.findById(userDTO.getId()).get().getRole().equals(Roles.ADMIN)){
            throw new AuthorizedException("Bạn không có quyền Admin nên không thể xem trang này !");
        }
        model.addAttribute("listuser", userService.getAllUser());
        return "list_user_admin";
    }

    @GetMapping("/creat-user-admin")
    public String creatUserByAdmin(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        if(!userService.findById(userDTO.getId()).get().getRole().equals(Roles.ADMIN)){
            throw new AuthorizedException("Bạn không có quyền Admin nên không thể xem trang này !");
        }
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
        if(!userService.findById(userDTO.getId()).get().getRole().equals(Roles.ADMIN)){
            throw new AuthorizedException("Bạn không có quyền Admin nên không thể xem trang này !");
        }
        User currentUser = userService.findById(userID).get();
        model.addAttribute("userRequest", new UserRequest(currentUser.getId(),currentUser.getFullname(),currentUser.getEmail(),currentUser.getMobile(),currentUser.getAddress(),currentUser.getWallet(),currentUser.getCity()));
        return "edit_user_admin";
    }

    @PostMapping("/edit-user-admin")
    public String postMethodName(@Valid @ModelAttribute("userRequest") UserRequest userRequest, HttpSession session, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "edit_user_admin";
        }
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        if(!userService.findById(userDTO.getId()).get().getRole().equals(Roles.ADMIN)){
            throw new AuthorizedException("Bạn không có quyền Admin nên không thể xem trang này !");
        }
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
        new UserRequest(updateUser.getId(),updateUser.getFullname(),updateUser.getEmail(),updateUser.getMobile(),updateUser.getAddress(),currentUser.getWallet(),updateUser.getCity()));
        return "redirect:/api/v1/admin/view-user";
    }

    @GetMapping("listreverse")
    public String getAllReverse(Model model, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(!userService.findById(userDTO.getId()).get().getRole().equals(Roles.ADMIN)){
            throw new AuthorizedException("Bạn không có quyền Admin nên không thể xem trang này !");
        }
        model.addAttribute("user", userDTO);
        model.addAttribute("reverses", reverseService.getAllReverse());
        return "all_reverse_admin";
    }

    @GetMapping("listbill")
    public String getAllBill(Model model, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(!userService.findById(userDTO.getId()).get().getRole().equals(Roles.ADMIN)){
            throw new AuthorizedException("Bạn không có quyền Admin nên không thể xem trang này !");
        }
        model.addAttribute("user", userDTO);
        model.addAttribute("bills", billService.getAllBill());
        return "all_bill_admin";
    }

    @GetMapping("lock-user-admin/{userID}")
    public String lockUser(@PathVariable("userID") String userID, HttpSession session,
     Model model,RedirectAttributes redirectAttributes){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        if(!userService.findById(userDTO.getId()).get().getRole().equals(Roles.ADMIN)){
            throw new AuthorizedException("Bạn không có quyền Admin nên không thể xem trang này !");
        }
        User currentUser = userService.findById(userID).get();
        if(currentUser.getRole().equals(Roles.ADMIN)){
            redirectAttributes.addFlashAttribute("alert", "Không thể khóa tài khoản quyền ADMIN");
            return "redirect:/api/v1/admin/view-user";
        }
        if(currentUser.getState().equals(State.ACTIVE)){
            currentUser.setState(State.DISABLED);
            userService.edit(currentUser);
        }

        return "redirect:/api/v1/admin/view-user";
    }

    @GetMapping("unlock-user-admin/{userID}")
    public String unlockUser(@PathVariable("userID") String userID,Model model, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        if(!userService.findById(userDTO.getId()).get().getRole().equals(Roles.ADMIN)){
            throw new AuthorizedException("Bạn không có quyền Admin nên không thể xem trang này !");
        }
        User currentUser = userService.findById(userID).get();
        if(currentUser.getState().equals(State.DISABLED)){
            currentUser.setState(State.ACTIVE);
            userService.edit(currentUser);
        }

        return "redirect:/api/v1/admin/view-user";
    }

    @GetMapping("delete-user/{userID}")
    public String deleteUserByAdmin(@PathVariable("userID") String userID,Model model, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        if(!userService.findById(userDTO.getId()).get().getRole().equals(Roles.ADMIN)){
            throw new AuthorizedException("Bạn không có quyền Admin nên không thể xem trang này !");
        }
        User needDeleteUser = userService.findById(userID).get();
        userService.delete(needDeleteUser);
        
        return "redirect:/api/v1/admin/view-user";
    }

}
