package vn.techmaster.finalproject.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.finalproject.dto.UserDTO;
import vn.techmaster.finalproject.model.House;
import vn.techmaster.finalproject.request.SearchRequest;
import vn.techmaster.finalproject.service.HouseService;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired HouseService houseService;
    @GetMapping
    public String getAllHouse(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        List<House> list1 = new ArrayList<>();
        List<House> list2 = new ArrayList<>();
        List<House> list3 = new ArrayList<>();
        for(int i = 0; i < houseService.sortHouse().size();i++){
            if(i < 3){
                list1.add(houseService.sortHouse().get(i));
            }
            else if(i >= 3 && i < 6){
                list2.add(houseService.sortHouse().get(i));
            }
            else if(i >= 6 && i < 9){
                list3.add(houseService.sortHouse().get(i));
            }
            else {
                break;
            }
        }
        model.addAttribute("user", userDTO);
        model.addAttribute("searchRequest", new SearchRequest(null,null,null,0L));
        model.addAttribute("houses", houseService.showAllHouse());
        model.addAttribute("list1", list1);
        model.addAttribute("list2", list2);
        model.addAttribute("list3", list3);
        
        return "index";
    }
}
