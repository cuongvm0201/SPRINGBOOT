package vn.techmaster.finalproject.admin_controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import vn.techmaster.finalproject.model.House;
import vn.techmaster.finalproject.model.Reverse;
import vn.techmaster.finalproject.repository.HouseRepo;
import vn.techmaster.finalproject.request.HouseRequest;
import vn.techmaster.finalproject.service.HouseService;
import vn.techmaster.finalproject.service.StorageService;

@Controller
@RequestMapping("/api/v1/admin")
public class AdminHouseController {
    @Autowired private HouseRepo houseRepo;
    @Autowired private HouseService houseService;
    @Autowired private StorageService storageService;
    @GetMapping("/allhouse")
    public String getAllHouseByAdmin(Model model, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("houses", houseService.showAllHouse());
        return "house_admin";
    }

    @GetMapping("/creat-house")
    public String creatNewHouseByAdmin(Model model,HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
        model.addAttribute("houseRequest", new HouseRequest(null,null,null,null,null,null,0L,null,null,userDTO.getId()));

        return "house_creat";
    }

    @PostMapping(value = "/creat-house", consumes = { "multipart/form-data" })
  public String addEmployer(@Valid @ModelAttribute("houseRequest") HouseRequest houseRequest,
      BindingResult result, Model model,HttpSession session) throws IOException {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
    if (houseRequest.getLogo().getOriginalFilename().isEmpty()) {
      result.addError(new FieldError("houseRequest", "logo", "Logo is required"));
      return "house_creat";
    }

    // Nêú có lỗi thì trả về trình duyệt
    if (result.hasErrors()) {
      return "house_creat";
    }

    // Thêm vào cơ sở dữ liệu
    House house = houseService.add(
        House.builder()
        .name(houseRequest.getName())
        .description(houseRequest.getDescription())
        .city(houseRequest.getCity())
        .typeHouse(houseRequest.getTypeHouse())
        .address(houseRequest.getAddress())
        .price(houseRequest.getPrice())
        .creatAt(LocalDateTime.now())
        .build());

    // Lưu logo vào ổ cứng
    try {
      String logoFileName = storageService.saveFile(houseRequest.getLogo(), house.getId());
      houseService.updateLogo(house.getId(), logoFileName);
    } catch (IOException e) {
      // Nếu lưu file bị lỗi thì hãy xoá bản ghi Employer
      houseRepo.deleteById(house.getId());
      e.printStackTrace();
      return "house_creat";
    }
    return "house_admin";
  }

  @GetMapping(value = "/edit/{id}")
  public String editEmpId(Model model, @PathVariable("id") String id, HttpSession session) {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    System.out.println("Session ID: " + session.getId());
    model.addAttribute("user", userDTO);
    Optional<House> house = houseService.findById(id);
    if (house.isPresent()) {
      House currentHouse = house.get();
      model.addAttribute("houseRequest", new HouseRequest(
        currentHouse.getId(),
        currentHouse.getName(),
        currentHouse.getDescription(),
        currentHouse.getCity(),
        currentHouse.getTypeHouse(),
        currentHouse.getAddress(),
        currentHouse.getPrice(),
        currentHouse.getLogo_main(),
        null,
        null));
      model.addAttribute("house", currentHouse);
    }
    return "house_edit";
  }

  @PostMapping(value = "/edit", consumes = { "multipart/form-data" })
  public String editEmployer(@Valid @ModelAttribute("houseRequest") HouseRequest houseRequest,
      BindingResult result,
      Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
    // Nêú có lỗi thì trả về trình duyệt
    if (result.hasErrors()) {
      return "house_edit";
    }

    String logoFileName = null;
    // Cập nhật logo vào ổ cứng
    if (!houseRequest.getLogo().getOriginalFilename().isEmpty()) {
      try {
        logoFileName = storageService.saveFile(houseRequest.getLogo(), houseRequest.getId());
      
      } catch (IOException e) {
        // Nếu lưu file bị lỗi thì hãy xoá bản ghi House
        houseService.deleteById(houseRequest.getId());
        e.printStackTrace();
        return "house_edit";
      }
    }
    List<Reverse> currtentReverse = houseService.findById(houseRequest.getId()).get().getReverses();
    House currentHouse = houseService.findById(houseRequest.getId()).get();
    // Cập nhật lại House
    houseService.edit(House.builder()
        .id(houseRequest.getId())
        .name(houseRequest.getName())
        .description(houseRequest.getDescription())
        .city(houseRequest.getCity())
        .typeHouse(houseRequest.getTypeHouse())
        .address(houseRequest.getAddress())
        .price(houseRequest.getPrice())
        .logo_main(logoFileName == null ? currentHouse.getLogo_main() : logoFileName)
        .logo_sub_main1(currentHouse.getLogo_sub_main1())
        .logo_sub_main2(currentHouse.getLogo_sub_main2())
        .reverses(currtentReverse)
        .updateAt(LocalDateTime.now())
        .build());

    return "redirect:/api/v1/house";
  }

  @GetMapping(value = "/delete/{id}")
  public String deleteEmployerByID(@PathVariable String id,Model model, HttpSession session) {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    model.addAttribute("user", userDTO);
    Optional<House> house = houseService.findById(id);
    storageService.deleteFile(house.get().getLogo_main());
    houseService.deleteById(id);
    return "redirect:/api/v1/house";
  }
}
