package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.Employer;
import com.example.demo.model.Roles;
import com.example.demo.request.EmployerRequest;
import com.example.demo.service.EmployerService;
import com.example.demo.service.JobService;
import com.example.demo.service.StorageService;

import javax.management.relation.Role;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(value = "/employer")
public class EmployerController {
  @Autowired
  private EmployerService employerService;

  @Autowired
  private JobService jobService;


  @Autowired
  private StorageService storageService;

  @GetMapping
  public String listAllEmployers(Model model, HttpSession session) {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    System.out.println("Session ID: " + session.getId());
    model.addAttribute("user", userDTO);
    model.addAttribute("employers", employerService.getAll());
    return "employers";
  }

  @GetMapping(value = "/{id}")
  public String showEmployerDetailByID(Model model, @PathVariable String id, HttpSession session) {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    System.out.println("Session ID: " + session.getId());
    model.addAttribute("user", userDTO);
    model.addAttribute("employer", employerService.findById(id));
    model.addAttribute("jobs", jobService.findByEmpId(id));
    return "employer";
  }

  @GetMapping(value = "/add")
  public String addEmployerForm(Model model, HttpSession session) {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    System.out.println("Session ID: " + session.getId());
    model.addAttribute("user", userDTO);
    model.addAttribute("employer", new EmployerRequest("", "", "", "", "", null));
    return "employer_add";
  }

  @PostMapping(value = "/add", consumes = { "multipart/form-data" })
  public String addEmployer(@Valid @ModelAttribute("employer") EmployerRequest employerRequest,
      BindingResult result) throws IOException {
    //     UserDTO userDTO = (UserDTO) session.getAttribute("user");
    // System.out.println("Session ID: " + session.getId());
    // model.addAttribute("user", userDTO);
    if (employerRequest.getLogo().getOriginalFilename().isEmpty()) {
      result.addError(new FieldError("employer", "logo", "Logo is required"));
    }

    // N???? c?? l???i th?? tr??? v??? tr??nh duy???t
    if (result.hasErrors()) {
      return "employer_add";
    }

    // Th??m v??o c?? s??? d??? li???u
    Employer emp = employerService.add(Employer.builder()
        .name(employerRequest.getName())
        .website(employerRequest.getWebsite())
        .email(employerRequest.getEmail())
        .build());

    // L??u logo v??o ??? c???ng
    try {
      String logoFileName = storageService.saveFile(employerRequest.getLogo(), emp.getId());
      employerService.updateLogo(emp.getId(), logoFileName);
    } catch (IOException e) {
      // N???u l??u file b??? l???i th?? h??y xo?? b???n ghi Employer
      employerService.deleteById(emp.getId());
      e.printStackTrace();
      return "employer_add";
    }
    return "redirect:/employer";
  }

  @GetMapping(value = "/edit/{id}")
  public String editEmpId(Model model, @PathVariable("id") String id, HttpSession session) {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    System.out.println("Session ID: " + session.getId());
    model.addAttribute("user", userDTO);
    Optional<Employer> employer = employerService.findById(id);
    if (employer.isPresent()) {
      Employer currentEmp = employer.get();
      model.addAttribute("employerReq", new EmployerRequest(currentEmp.getId(),
          currentEmp.getName(),
          currentEmp.getWebsite(),
          currentEmp.getEmail(),
          currentEmp.getLogo_path(),
          null));
      model.addAttribute("employer", currentEmp);
    }
    return "employer_edit";
  }

  @PostMapping(value = "/edit", consumes = { "multipart/form-data" })
  public String editEmployer(@Valid @ModelAttribute("employerReq") EmployerRequest employerRequest,
      BindingResult result,
      Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
    // N???? c?? l???i th?? tr??? v??? tr??nh duy???t
    if (result.hasErrors()) {
      return "employer_edit";
    }

    String logoFileName = null;
    // C???p nh???t logo v??o ??? c???ng
    if (!employerRequest.getLogo().getOriginalFilename().isEmpty()) {
      try {
        logoFileName = storageService.saveFile(employerRequest.getLogo(), employerRequest.getId());
        // employerRepo.updateLogo(employerRequest.id(), logoFileName);
      } catch (IOException e) {
        // N???u l??u file b??? l???i th?? h??y xo?? b???n ghi Employer
        employerService.deleteById(employerRequest.getId());
        e.printStackTrace();
        return "employer_edit";
      }
    }
    // C???p nh???t l???i Employer
    employerService.edit(Employer.builder()
        .id(employerRequest.getId())
        .name(employerRequest.getName())
        .website(employerRequest.getWebsite())
        .email(employerRequest.getEmail())
        .logo_path(logoFileName == null ? employerRequest.getLogo_path() : logoFileName)
        .build());

    return "redirect:/employer";
  }

  @GetMapping(value = "/delete/{id}")
  public String deleteEmployerByID(@PathVariable String id,Model model, HttpSession session) {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    System.out.println("Session ID: " + session.getId());
    model.addAttribute("user", userDTO);
    Optional<Employer> emp = employerService.findById(id);
    storageService.deleteFile(emp.get().getLogo_path());
    employerService.deleteById(id);
    return "redirect:/employer";
  }
}
