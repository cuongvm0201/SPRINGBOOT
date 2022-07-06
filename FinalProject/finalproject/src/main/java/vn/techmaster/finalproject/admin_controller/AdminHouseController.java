package vn.techmaster.finalproject.admin_controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import vn.techmaster.finalproject.dto.UserDTO;
import vn.techmaster.finalproject.model.House;
import vn.techmaster.finalproject.model.Reverse;
import vn.techmaster.finalproject.repository.HouseRepo;
import vn.techmaster.finalproject.request.HouseRequest;
import vn.techmaster.finalproject.service.HouseService;
import vn.techmaster.finalproject.service.StorageService;
import vn.techmaster.finalproject.ulties.FileUploadUtil;

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
        model.addAttribute("houseRequest", 
        new HouseRequest(null,null,null,null,null,null,0L,null,null,null,null,null,null,null,null,userDTO.getId()));

        return "house_creat";
    }

    @PostMapping(value = "/creat-house", consumes = { "multipart/form-data" })
  public String addHouse(@Valid @ModelAttribute("houseRequest") HouseRequest houseRequest,
      BindingResult result, Model model,HttpSession session) throws IOException {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
    if (houseRequest.getLogo().getOriginalFilename().isEmpty()) {
      result.addError(new FieldError("houseRequest", "logo", "Logo is required"));
      return "house_creat";
    }
    if (houseRequest.getLogo1().getOriginalFilename().isEmpty()) {
      result.addError(new FieldError("houseRequest", "logo1", "Logo is required"));
      return "house_creat";
    }
    if (houseRequest.getLogo2().getOriginalFilename().isEmpty()) {
      result.addError(new FieldError("houseRequest", "logo2", "Logo is required"));
      return "house_creat";
    }
    if (houseRequest.getLogo3().getOriginalFilename().isEmpty()) {
      result.addError(new FieldError("houseRequest", "logo3", "Logo is required"));
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
        String logo = StringUtils.cleanPath(houseRequest.getLogo().getOriginalFilename());
        String logo1 = StringUtils.cleanPath(houseRequest.getLogo1().getOriginalFilename());
        String logo2 = StringUtils.cleanPath(houseRequest.getLogo2().getOriginalFilename());
        String logo3 = StringUtils.cleanPath(houseRequest.getLogo3().getOriginalFilename());
        
        house.setLogo_main(logo);
        house.setLogo_sub_main1(logo1);
        house.setLogo_sub_main2(logo2);
        house.setLogo_sub_main3(logo3);
         
        houseRepo.save(house);
        String uploadDir = "firstphotos/";
         
        FileUploadUtil.saveFile(uploadDir, logo, houseRequest.getLogo());
        FileUploadUtil.saveFile(uploadDir, logo1, houseRequest.getLogo1());
        FileUploadUtil.saveFile(uploadDir, logo2, houseRequest.getLogo2());
        FileUploadUtil.saveFile(uploadDir, logo3, houseRequest.getLogo3());
    
    
    model.addAttribute("houses", houseService.showAllHouse());
    return "house_admin";
  }

  @GetMapping(value = "/edit/{id}")
  public String editHouseID(Model model, @PathVariable("id") String id, HttpSession session) {
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
        currentHouse.getLogo_sub_main1(),
        null,
        currentHouse.getLogo_sub_main2(),
        null,
        currentHouse.getLogo_sub_main3(),
        null,
        null));
      model.addAttribute("house", currentHouse);
    }
    return "house_edit";
  }

  @PostMapping(value = "/edit", consumes = { "multipart/form-data" })
  public String editHouse(@Valid @ModelAttribute("houseRequest") HouseRequest houseRequest,
      BindingResult result,
      Model model, HttpSession session) throws IOException {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);

        
    // Nêú có lỗi thì trả về trình duyệt
    if (result.hasErrors()) {
      return "house_edit";
    }
    String uploadDir = "firstphotos/";
    String logo = null;
    String logo1 = null;
    String logo2 = null;
    String logo3 = null;
    if (!houseRequest.getLogo().getOriginalFilename().isEmpty()) {
      logo = StringUtils.cleanPath(houseRequest.getLogo().getOriginalFilename());
      FileUploadUtil.saveFile(uploadDir, logo, houseRequest.getLogo());
    }

    if (!houseRequest.getLogo1().getOriginalFilename().isEmpty()) {
      logo1 = StringUtils.cleanPath(houseRequest.getLogo1().getOriginalFilename());
      FileUploadUtil.saveFile(uploadDir, logo1, houseRequest.getLogo1());
    }
    if (!houseRequest.getLogo2().getOriginalFilename().isEmpty()) {
      logo2 = StringUtils.cleanPath(houseRequest.getLogo2().getOriginalFilename());
      FileUploadUtil.saveFile(uploadDir, logo2, houseRequest.getLogo2());
    }
    if (!houseRequest.getLogo3().getOriginalFilename().isEmpty()) {
      logo3 = StringUtils.cleanPath(houseRequest.getLogo3().getOriginalFilename());
      FileUploadUtil.saveFile(uploadDir, logo3, houseRequest.getLogo3());
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
        .logo_main(logo == null ? currentHouse.getLogo_main() : logo)
        .logo_sub_main1(logo1 == null ? currentHouse.getLogo_sub_main1() : logo1)
        .logo_sub_main2(logo2 == null ? currentHouse.getLogo_sub_main2() : logo2)
        .logo_sub_main3(logo3 == null ? currentHouse.getLogo_sub_main3() : logo3)
        .reverses(currtentReverse)
        .updateAt(LocalDateTime.now())
        .build());
        
       
    return "redirect:/api/v1/house/all";
  }

  @GetMapping(value = "/delete/{id}")
  public String deleteEmployerByID(@PathVariable String id,Model model, HttpSession session) {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    model.addAttribute("user", userDTO);
    Optional<House> house = houseService.findById(id);
    storageService.deleteFile(house.get().getLogo_main());
    storageService.deleteFile(house.get().getLogo_sub_main1());
    storageService.deleteFile(house.get().getLogo_sub_main2());
    storageService.deleteFile(house.get().getLogo_sub_main3());
    houseService.deleteById(id);
    return "redirect:/api/v1/house/all";
  }
}
