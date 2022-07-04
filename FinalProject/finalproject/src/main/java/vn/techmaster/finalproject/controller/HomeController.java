package vn.techmaster.finalproject.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.finalproject.dto.UserDTO;
import vn.techmaster.finalproject.request.SearchRequest;
import vn.techmaster.finalproject.service.HouseService;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired HouseService houseService;
    @GetMapping
    public String getAllHouse(Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("searchRequest", new SearchRequest(null,null,null,0L));
        model.addAttribute("houses", houseService.showAllHouse());
        return "index";
    }
}
