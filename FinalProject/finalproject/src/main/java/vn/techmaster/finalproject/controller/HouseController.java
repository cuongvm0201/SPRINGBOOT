package vn.techmaster.finalproject.controller;

import java.time.LocalDate;


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

import vn.techmaster.finalproject.repository.HouseRepo;
import vn.techmaster.finalproject.request.SearchRequest;
import vn.techmaster.finalproject.service.HouseService;



@Controller
@RequestMapping("api/v1/house")
public class HouseController {
    @Autowired private HouseRepo houseRepo;
    @Autowired private HouseService houseService;
    @GetMapping
    public String getAllHouse(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("searchRequest", new SearchRequest(null,null,null,null));
        model.addAttribute("houses", houseRepo.findAll());
        return "houses";
    }

    @GetMapping("/{id}")
    public String getHouseById(Model model, @PathVariable String id, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("house", houseRepo.findById(id).get());
        return "house";
    }

    @GetMapping("/searchhouse")
    public String searchEmptyHouse(@Valid @ModelAttribute("searchRequest") SearchRequest searchRequest, BindingResult result,
     Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        if (result.hasErrors()) {
            return "index";
        }
        if(searchRequest.price() < 0){
            result.addError(new FieldError("searchRequest", "price", "Giá tiền phải lớn hơn 0"));
            return "index";
        }


        if(searchRequest.checkin().isEmpty()){
            result.addError(new FieldError("searchRequest", "checkin", "Ngày check-in không được để trống"));
            return "index";
        }

        if(searchRequest.checkout().isEmpty()){
            result.addError(new FieldError("searchRequest", "checkout", "Ngày check-out không được để trống"));
            return "index";
        }
        LocalDate date1 = LocalDate.parse(searchRequest.checkin());
        LocalDate date2 = LocalDate.parse(searchRequest.checkout());
        if (date2.compareTo(LocalDate.now()) < 0) {
                    result.addError(new FieldError("searchRequest", "checkout", "Ngày không hợp lệ"));
                    return "index";
        }

        if(date1.compareTo(date2) > 0){
            result.addError(new FieldError("searchRequest", "checkin", "Ngày không hợp lệ"));
            return "index";
        }

        
        
        model.addAttribute("houses", houseService.findHouseBySearch(searchRequest));
        return "houses";
    }
    
}
