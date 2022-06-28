package vn.techmaster.finalproject.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;


import vn.techmaster.finalproject.dto.UserDTO;
import vn.techmaster.finalproject.model.Bill;
import vn.techmaster.finalproject.model.House;
import vn.techmaster.finalproject.model.Reverse;
import vn.techmaster.finalproject.model.User;
import vn.techmaster.finalproject.repository.HouseRepo;
import vn.techmaster.finalproject.repository.ReverseRepo;
import vn.techmaster.finalproject.repository.UserRepo;
import vn.techmaster.finalproject.request.PayRequest;
import vn.techmaster.finalproject.request.ReverseRequest;
import vn.techmaster.finalproject.service.BillService;
import vn.techmaster.finalproject.service.HouseService;
import vn.techmaster.finalproject.service.ReverseService;
import vn.techmaster.finalproject.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("api/v1/reverse")
public class ReverseController {
    @Autowired private HouseService houseService;
    @Autowired private UserService userService;
    @Autowired private ReverseService reverseService;
    @Autowired private ReverseRepo reverseRepo;
    @Autowired private HouseRepo houseRepo;
    @Autowired private UserRepo userRepo;
    @Autowired private BillService billService;
    @GetMapping("/newreverse/creat/{houseID}/{userID}")
    public String creatNewReverse(Model model, 
    @PathVariable String houseID, 
    @PathVariable String userID,
    HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        House currentHouse = houseRepo.findById(houseID).get();
        User currentUser = userRepo.findById(userDTO.getId()).get();
        model.addAttribute("reverseRequest",
        new ReverseRequest("",userDTO.getId(),houseID,currentHouse.getName(),currentUser.getMobile(),currentUser.getFullname(),"",""));
        return "reverse_add";
    }

    @PostMapping("/newreverse/creat")
    public String creatNewReverse(@Valid @ModelAttribute("reverseRequest") ReverseRequest reverseRequest,
    BindingResult result,
    Model model, HttpSession session) {
          // Nêú có lỗi thì trả về trình duyệt
          if (result.hasErrors()) {
            return "redirect:/api/v1/reverse/newreverse/creat/" + reverseRequest.getHouseID() + "/" + reverseRequest.getUserID();
        }
        // if(reverseRequest.getCheckout() == null && reverseRequest.getCheckin() == null){
        //     result.addError(new FieldError("reverseRequest", "checkin", "Ngày không được để trống"));
        //     result.addError(new FieldError("reverseRequest", "checkout", "Ngày không được để trống"));
        // }
        List<House> allCurrentHouse = houseRepo.findAll();
        List<Reverse> allCurrentReverse = reverseRepo.findAll();
        LocalDate checkin = LocalDate.parse(reverseRequest.getCheckin());
        LocalDate checkout = LocalDate.parse(reverseRequest.getCheckout());
        if(checkout.compareTo(LocalDate.now()) <= 0){
            result.addError(new FieldError("reverseRequest", "checkout", "Ngày không hợp lệ"));
        }

        if(checkin.compareTo(checkout) > 0){
            result.addError(new FieldError("reverseRequest", "checkin", "Ngày không hợp lệ"));
        }
        
        // for (int i = 0; i < allCurrentHouse.size(); i++) {
        //     for (int j = 0; j < allCurrentHouse.get(i).getReverses().size(); j++) {
        //         Reverse a = allCurrentHouse.get(i).getReverses().get(j);
        //             if ((checkin.compareTo(checkout) <= 0 && checkout.compareTo(a.getCheckin()) >=0 && checkout.compareTo(a.getCheckout()) <= 0)) {
        //                 result.addError(new FieldError("reverse", "checkin", "Ngày này không có phòng trống"));
        //                 result.addError(new FieldError("reverse", "checkout", "Ngày này không có phòng trống"));
        //                 }
        //             if ((checkin.compareTo(a.getCheckout()) > 0 && checkout.compareTo(checkin) >= 0)) {
        //                     result.addError(new FieldError("reverse", "checkout", "Ngày không được để trống"));
        //                 }
        //     }
        // }
        
        

      


        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        Reverse newReverse = new Reverse(UUID.randomUUID().toString(), 
        userService.findById(userDTO.getId()).get(),
         houseService.findById(reverseRequest.getHouseID()).get(),
          null, checkin, checkout);
          model.addAttribute("payRequest", new PayRequest(newReverse.getId(),reverseRequest.getUserID(),"","","","",""));
          reverseService.creatNewReverse(newReverse);
        return "redirect:/api/v1/reverse/newreverse/payment/" + newReverse.getId() + "/" + userDTO.getId() ;
    }

    @GetMapping("/newreverse/payment/{reverseID}/{userID}")
    public String paymentReverse(Model model,HttpSession session, @PathVariable String reverseID, @PathVariable String userID){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        model.addAttribute("payRequest", new PayRequest(reverseID,userID,"","","","",""));
        model.addAttribute("reverseID", reverseID);
        model.addAttribute("userID", userID);
        return "Payment";
    }

    @PostMapping("/newreverse/payment")
    public String paymentReverse(@Valid @ModelAttribute("payRequest") PayRequest payRequest,
    BindingResult result ,Model model,HttpSession session) {
        if (result.hasErrors()) {
            return "Payment";
        }
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        
        Bill newBill = new Bill();
        newBill.setId(UUID.randomUUID().toString());
        newBill.setUser(userRepo.findById(userDTO.getId()).get());
        newBill.setReverse(reverseRepo.findById(payRequest.getReverseID()).get());
        newBill.setCreatAt(LocalDateTime.now());
        billService.creatBillByUser(newBill);
        
        return "success";
    }
    

}
