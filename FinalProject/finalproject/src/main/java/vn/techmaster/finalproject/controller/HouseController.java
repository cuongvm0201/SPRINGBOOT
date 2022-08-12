package vn.techmaster.finalproject.controller;

import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import vn.techmaster.finalproject.model.City;
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
        int pageSize = 5;
    
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
        houseRepo.findById(id).get().increment();
        houseRepo.save(houseRepo.findById(id).get());
        model.addAttribute("house", houseRepo.findById(id).get());
        return "house";
    }


    @GetMapping("/searchhouse/{pageNumber}")
    public String searchEmptyHouse(@Valid @ModelAttribute("searchRequest") SearchRequest searchRequest, BindingResult result,
     Model model, HttpSession session, @PathVariable("pageNumber") int pageNumber) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        if (result.hasErrors()) {
            return "index";
        }
        
        if(searchRequest.price() == null){
            result.addError(new FieldError("searchRequest", "price", "Giá tiền không được để trống"));
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
        Page<House> page ;
        Pageable pageable = PageRequest.of(pageNumber-1,3);
        page = houseService.findHouseBySearch(searchRequest, pageable);
        List<House> abc = page.getContent();
       
        
        model.addAttribute("houses", abc);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("price", searchRequest.price());
        model.addAttribute("city", searchRequest.city());
        model.addAttribute("checkin", searchRequest.checkin());
        model.addAttribute("checkout", searchRequest.checkout());
        return "house_searchrequest";
       
    }

    @GetMapping("/city/{pageNumber}")
    public String filterHouseByCity(Model model,@PathVariable("pageNumber") int pageNumber, @RequestParam(value = "cityvalue") City city, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        Page<House> page ;
        Pageable pageable = PageRequest.of(pageNumber-1,3);
        page = houseRepo.findAllByCity(city, pageable);
        List<House> abc = page.getContent();
        model.addAttribute("houses", abc);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("cityvalue", city);
        return "house_search";
    }
    
}
