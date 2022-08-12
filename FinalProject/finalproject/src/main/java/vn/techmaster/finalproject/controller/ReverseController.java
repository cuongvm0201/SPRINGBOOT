package vn.techmaster.finalproject.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.techmaster.finalproject.dto.UserDTO;
import vn.techmaster.finalproject.exception.UserException;
import vn.techmaster.finalproject.model.Bill;
import vn.techmaster.finalproject.model.House;
import vn.techmaster.finalproject.model.PayType;
import vn.techmaster.finalproject.model.Reverse;
import vn.techmaster.finalproject.model.State;
import vn.techmaster.finalproject.model.User;
import vn.techmaster.finalproject.repository.BillRepo;
import vn.techmaster.finalproject.repository.HouseRepo;
import vn.techmaster.finalproject.repository.ReverseRepo;
import vn.techmaster.finalproject.repository.UserRepo;
import vn.techmaster.finalproject.request.PayRequest;
import vn.techmaster.finalproject.request.PayWalletRequest;
import vn.techmaster.finalproject.request.ReverseRequest;
import vn.techmaster.finalproject.request.UserRequest;
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
        if(!userService.findById(userID).isPresent()){
            throw new UserException("Tài khoản không tồn tại !");
        }

        if(userService.findById(userID).get().getState().equals(State.DISABLED)){
            throw new UserException("Tài khoản đã bị khóa !");
        }
        House currentHouse = houseRepo.findById(houseID).get();
        User currentUser = userRepo.findById(userDTO.getId()).get();
        model.addAttribute("reverseRequest",
        new ReverseRequest("",userID,houseID,
        currentHouse.getName(),currentUser.getFullname(),currentUser.getMobile(),"","",null));
        model.addAttribute("payType", PayType.values());
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

         if(reverseRequest.getMobile() == null ){
            result.addError(new FieldError("reverseRequest", "mobile", "Số điện thoại không được để trống"));
            return "reverse_add"; 
         }

         if(reverseRequest.getMobile().length() < 10 || reverseRequest.getMobile().length() > 11){
            result.addError(new FieldError("reverseRequest", "mobile", "Số điện thoại phải đúng định dạng 10 hoặc 11 số"));
            return "reverse_add"; 
         }
         
         
        List<House> allCurrentHouse = houseRepo.findAll();
        House currentHouse = houseService.findById(reverseRequest.getHouseID()).get();
       
       
        
        
            for (int j = 0; j < currentHouse.getReverses().size(); j++) {
                Reverse a = currentHouse.getReverses().get(j);
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
        
        
        // check lịch đặt của nhà với id - end 

        if(reverseRequest.getPayType() == null){
            result.addError(new FieldError("reverseRequest", "payType", "Vui lòng chọn hình thức thanh toán"));
            return "reverse_add";
        }


       
        String newReverseID = UUID.randomUUID().toString();
        session.setAttribute("reverseDTO",
                    new Reverse(newReverseID,
                    houseService.findById(reverseRequest.getHouseID()).get(),
                    userService.findById(userDTO.getId()).get(),
                     checkin,
                     checkout
                     ));
          model.addAttribute("payRequest", new PayRequest(newReverseID,reverseRequest.getUserID(),"","","","",""));
          if(reverseRequest.getPayType().toString().equals("CARD")){
            return "redirect:/api/v1/reverse/newreverse/payment/" + newReverseID + "/" + userDTO.getId();
          }
          if(reverseRequest.getPayType().toString().equals("WALLET")){
            return "redirect:/api/v1/reverse/newreverse/wallet/" + newReverseID + "/" + userDTO.getId();
          }

        return "reverse_add";
    }

    @GetMapping("/newreverse/payment/{reverseID}/{userID}")
    public String paymentReverse(Model model,HttpSession session, @PathVariable String reverseID, @PathVariable String userID){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(!userService.findById(userID).isPresent()){
            throw new UserException("Tài khoản không tồn tại !");
        }

        if(userService.findById(userID).get().getState().equals(State.DISABLED)){
            throw new UserException("Tài khoản đã bị khóa !");
        }
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
        houseService.findById(newReverse.getHouse().getId()).get().increment1();
        houseRepo.save(houseService.findById(newReverse.getHouse().getId()).get());
        Bill newBill = new Bill();
        newBill.setId(UUID.randomUUID().toString());
        newBill.setUser(userRepo.findById(userDTO.getId()).get());
        newBill.setReverse(reverseRepo.findById(payRequest.getReverseID()).get());
        newBill.setCreatAt(LocalDateTime.now());
        billService.creatBillByUser(newBill);
        session.removeAttribute("reverseDTO");
        return "success";
    }

    @GetMapping("/newreverse/wallet/{reverseID}/{userID}")
    public String payByWalletReverse(Model model,HttpSession session, @PathVariable String reverseID, @PathVariable String userID){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(!userService.findById(userID).isPresent()){
            throw new UserException("Tài khoản không tồn tại !");
        }

        if(userService.findById(userID).get().getState().equals(State.DISABLED)){
            throw new UserException("Tài khoản đã bị khóa !");
        }
        User currentUser = userService.findById(userID).get();
        Reverse newReverse = (Reverse) session.getAttribute("reverseDTO");
        House currentHouse = houseService.findById(newReverse.getHouse().getId()).get();
        long startDate = newReverse.getCheckin().toEpochDay();
        long endDate = newReverse.getCheckout().toEpochDay();
        long diffrent = endDate - startDate;
        Long total = diffrent*currentHouse.getPrice();
        model.addAttribute("reverseDTO", newReverse);
        model.addAttribute("user", userDTO);
        model.addAttribute("payWalletRequest", new PayWalletRequest(newReverse.getId(),userID,currentUser.getWallet(),total));
        model.addAttribute("reverseID", reverseID);
        model.addAttribute("userID", userID);
        model.addAttribute("houseID", currentHouse.getId());
        return "wallet";
    }

    @PostMapping("/newreverse/wallet")
    public String payByWalletReverse(@Valid @ModelAttribute("payWalletRequest") PayWalletRequest payWalletRequest,
    BindingResult result ,Model model,HttpSession session,RedirectAttributes redirectAttributes) {
        Reverse newReverse = (Reverse) session.getAttribute("reverseDTO");
        model.addAttribute("reverseDTO", newReverse);
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        if (result.hasErrors()) {
            return "wallet";
        }
        User currentUser = userService.findById(userDTO.getId()).get();
        if(currentUser.getWallet() - payWalletRequest.getTotalPay() < 0){
            result.addError(new FieldError("payWalletRequest", "walletuser", "Số dư ví không đủ"));
            return "wallet";
        }
        
        currentUser.setWallet(currentUser.getWallet() - payWalletRequest.getTotalPay());
        userService.edit(currentUser);
        reverseService.creatNewReverse(newReverse);
        houseService.findById(newReverse.getHouse().getId()).get().increment1();
        houseRepo.save(houseService.findById(newReverse.getHouse().getId()).get());
        Bill newBill = new Bill();
        newBill.setId(UUID.randomUUID().toString());
        newBill.setUser(userRepo.findById(userDTO.getId()).get());
        newBill.setReverse(reverseRepo.findById(payWalletRequest.getReverseID()).get());
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

    @GetMapping("/cancel-reverse/{reverseID}")
    public String cancelReverse(Model model,HttpSession session, @PathVariable String reverseID,RedirectAttributes redirectAttributes){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        Reverse currentReverse = reverseRepo.findById(reverseID).get();
        User currentUser = userService.findById(userDTO.getId()).get();
        List<Bill> listBill = billService.findAllBillByUserID(userDTO.getId());
        Bill currentBill = listBill.stream().filter(i -> i.getReverse().getId().equals(currentReverse.getId())).findFirst().get();

        if(!(LocalDate.now().plusDays(3).compareTo(currentReverse.getCheckin()) <= 0)){
            redirectAttributes.addFlashAttribute("error", "Chỉ hủy được phòng trước 3 ngày so với ngày Check-in");
            return "redirect:/api/v1/user/listreverse/"+currentUser.getId();
        }
        
        
        currentUser.setWallet(currentUser.getWallet() + currentBill.getTotalPrice());
        userService.edit(currentUser);
        billService.deleteBill(currentBill.getId());
        reverseService.deleteReverse(currentReverse.getId());
        model.addAttribute("userRequest",
        new UserRequest(currentUser.getId(),currentUser.getFullname(),currentUser.getEmail(),currentUser.getMobile(),currentUser.getAddress(),currentUser.getWallet(),currentUser.getCity()));
        redirectAttributes.addFlashAttribute("message1", "Hủy phòng thành công, ví của bạn đã nhận lại số tiền: "+currentBill.getTotalPrice());
        return "redirect:/api/v1/user/findbyid/"+currentUser.getId();
    }

}
