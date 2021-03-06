package vn.techmaster.finalproject.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.techmaster.finalproject.dto.UserDTO;
import vn.techmaster.finalproject.model.House;
import vn.techmaster.finalproject.repository.HouseRepo;
import vn.techmaster.finalproject.request.SearchRequest;
import vn.techmaster.finalproject.service.HouseService;



@Controller
@RequestMapping("api/v1/house")
public class HouseController {
    @Autowired private HouseRepo houseRepo;
    @Autowired private HouseService houseService;
    // @GetMapping
    // public String getAllHouse(Model model, HttpSession session) {
    //     UserDTO userDTO = (UserDTO) session.getAttribute("user");
    //     model.addAttribute("user", userDTO);
    //     model.addAttribute("searchRequest", new SearchRequest(null,null,null,null));
    //     model.addAttribute("houses", houseRepo.findAll());
    //     return "houses";
    // }

    
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        int pageSize = 3;
    
        Page <House> page = houseService.findPaginated(pageNo, pageSize);
        List <House> listHouses = page.getContent();
    
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listHouses", listHouses);
        return "houses";
    }

    @GetMapping("/all")
    public String viewHomePage(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
    return findPaginated(1, model, session);  
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
            result.addError(new FieldError("searchRequest", "price", "Gi?? ti???n ph???i l???n h??n 0"));
            return "index";
        }


        if(searchRequest.checkin().isEmpty()){
            result.addError(new FieldError("searchRequest", "checkin", "Ng??y check-in kh??ng ???????c ????? tr???ng"));
            return "index";
        }

        if(searchRequest.checkout().isEmpty()){
            result.addError(new FieldError("searchRequest", "checkout", "Ng??y check-out kh??ng ???????c ????? tr???ng"));
            return "index";
        }
        LocalDate date1 = LocalDate.parse(searchRequest.checkin());
        LocalDate date2 = LocalDate.parse(searchRequest.checkout());
        if (date2.compareTo(LocalDate.now()) < 0) {
                    result.addError(new FieldError("searchRequest", "checkout", "Ng??y kh??ng h???p l???"));
                    return "index";
        }

        if(date1.compareTo(date2) > 0){
            result.addError(new FieldError("searchRequest", "checkin", "Ng??y kh??ng h???p l???"));
            return "index";
        }

        
        
        model.addAttribute("houses", houseService.findHouseBySearch(searchRequest));
        return "house_search";
    }
    
}
