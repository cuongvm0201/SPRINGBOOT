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
        model.addAttribute("user", userDTO);
        House currentHouse = houseRepo.findById(houseID).get();
        User currentUser = userRepo.findById(userDTO.getId()).get();
        model.addAttribute("reverseRequest",
        new ReverseRequest("",userID,houseID,
        currentHouse.getName(),currentUser.getFullname(),currentUser.getMobile(),"",""));
        return "reverse_add";
    }

    @PostMapping("/newreverse/creat")
    public String creatNewReverse(@Valid @ModelAttribute("reverseRequest") ReverseRequest reverseRequest,
    BindingResult result,
    Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
          // Nêú có lỗi thì trả về trình duyệt
          if (result.hasErrors()) {
            return "reverse_add";
         }
         // check lịch đặt của nhà với id - start
            LocalDate checkin = LocalDate.parse(reverseRequest.getCheckin());
            LocalDate checkout = LocalDate.parse(reverseRequest.getCheckout());
        
         
         if(checkin.compareTo(checkout) > 0){
             result.addError(new FieldError("reverseRequest", "checkin", "Ngày không hợp lệ"));
             return "reverse_add";
         }
 
         if(checkout.compareTo(LocalDate.now()) <= 0){
             result.addError(new FieldError("reverseRequest", "checkout", "Ngày không hợp lệ"));
             return "reverse_add";
         }
 
        List<House> allCurrentHouse = houseRepo.findAll();
        List<Reverse> allCurrentReverse = reverseRepo.findAll();
       
       
        
        for (int i = 0; i < allCurrentHouse.size(); i++) {
            for (int j = 0; j < allCurrentHouse.get(i).getReverses().size(); j++) {
                Reverse a = allCurrentHouse.get(i).getReverses().get(j);
                    if (checkin.equals(a.getCheckin()) && checkout.equals(a.getCheckout())) {
                        result.addError(new FieldError("reverseRequest", "checkin", "Ngày này đã có lịch đặt"));
                        result.addError(new FieldError("reverseRequest", "checkout", "Ngày này đã có lịch đặt"));
                        return "reverse_add";
                    }
                    if ((checkin.compareTo(a.getCheckin()) <= 0 && checkout.compareTo(a.getCheckout()) >= 0)) {
                        result.addError(new FieldError("reverseRequest", "checkin", "Ngày này đã có lịch đặt"));
                        result.addError(new FieldError("reverseRequest", "checkout", "Ngày này đã có lịch đặt"));
                        return "reverse_add";
                        }

                        if ((checkin.compareTo(a.getCheckin()) >= 0 && checkout.compareTo(a.getCheckout()) <= 0)) {
                            result.addError(new FieldError("reverseRequest", "checkin", "Ngày này đã có lịch đặt"));
                            result.addError(new FieldError("reverseRequest", "checkout", "Ngày này đã có lịch đặt"));
                            return "reverse_add";
                            }
                            if (((checkin.compareTo(a.getCheckin()) >= 0 && checkin.compareTo(a.getCheckout()) <= 0) &&  checkout.compareTo(a.getCheckout()) >= 0)) {
                                result.addError(new FieldError("reverseRequest", "checkin", "Ngày này đã có lịch đặt"));
                                result.addError(new FieldError("reverseRequest", "checkout", "Ngày này đã có lịch đặt"));
                                return "reverse_add";
                                }       
            }
        }
        
        // check lịch đặt của nhà với id - end 

      


       
        String newReverseID = UUID.randomUUID().toString();
        session.setAttribute("reverseDTO",
                    new Reverse(newReverseID,
                    userService.findById(userDTO.getId()).get(),
                    houseService.findById(reverseRequest.getHouseID()).get(),
                     null,
                     checkin,
                     checkout
                     ));
          model.addAttribute("payRequest", new PayRequest(newReverseID,reverseRequest.getUserID(),"","","","",""));
          
        return "redirect:/api/v1/reverse/newreverse/payment/" + newReverseID + "/" + userDTO.getId() ;
    }

    @GetMapping("/newreverse/payment/{reverseID}/{userID}")
    public String paymentReverse(Model model,HttpSession session, @PathVariable String reverseID, @PathVariable String userID){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        Reverse newReverse = (Reverse) session.getAttribute("reverseDTO");
        model.addAttribute("reverseDTO", newReverse);
        model.addAttribute("user", userDTO);
        model.addAttribute("payRequest", new PayRequest(newReverse.getId(),userID,"","","","",""));
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

        Reverse newReverse = (Reverse) session.getAttribute("reverseDTO");
        model.addAttribute("reverseDTO", newReverse);
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        reverseService.creatNewReverse(newReverse);
        Bill newBill = new Bill();
        newBill.setId(UUID.randomUUID().toString());
        newBill.setUser(userRepo.findById(userDTO.getId()).get());
        newBill.setReverse(reverseRepo.findById(payRequest.getReverseID()).get());
        newBill.setCreatAt(LocalDateTime.now());
        billService.creatBillByUser(newBill);
        session.removeAttribute("reverseDTO");
        return "success";
    }
    
    @GetMapping("/newreverse/payment-cancel")
    public String cancelPayment(HttpSession session) {
        session.removeAttribute("reverseDTO");
        return "redirect:/";
    }

}
