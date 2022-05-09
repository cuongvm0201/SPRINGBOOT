package vn.techmaster.job_hunt.controller;

import java.io.IOException;
import java.util.Optional;

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

import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.request.EmployerRequest;
import vn.techmaster.job_hunt.respository.EmployerRepo;
import vn.techmaster.job_hunt.respository.JobRepo;
import vn.techmaster.job_hunt.service.StorageService;

@Controller
@RequestMapping(value = "/employer")
public class EmployerController {
  @Autowired
  private EmployerRepo employerRepo;

  @Autowired
  private JobRepo jobRepo;

  @Autowired
  private StorageService storageService;

  @GetMapping
  public String listAllEmployers(Model model) {
    model.addAttribute("employers", employerRepo.getAll());
    return "employers";
  }

  @GetMapping(value = "/{id}")
  public String showEmployerDetailByID(Model model, @PathVariable String id) {
    model.addAttribute("employer", employerRepo.findById(id));
    model.addAttribute("jobs", jobRepo.findByEmpId(id));
    return "employer";
  }

  @GetMapping(value = "/add")
  public String addEmployerForm(Model model) {
    model.addAttribute("employer", new EmployerRequest("", "", "", "", "", null));
    return "employer_add";
  }

  @PostMapping(value = "/add", consumes = { "multipart/form-data" })
  public String addEmployer(@Valid @ModelAttribute("employer") EmployerRequest employerRequest,
      BindingResult result,
      Model model) {
    if (employerRequest.logo().getOriginalFilename().isEmpty()) {
      result.addError(new FieldError("employer", "logo", "Logo is required"));
    }

    // Nêú có lỗi thì trả về trình duyệt
    if (result.hasErrors()) {
      return "employer_add";
    }

    // Thêm vào cơ sở dữ liệu
    Employer emp = employerRepo.add(Employer.builder()
        .name(employerRequest.name())
        .website(employerRequest.website())
        .email(employerRequest.email())
        .build());

    // Lưu logo vào ổ cứng
    try {
      String logoFileName = storageService.saveFile(employerRequest.logo(), emp.getId());
      employerRepo.updateLogo(emp.getId(), logoFileName);
    } catch (IOException e) {
      // Nếu lưu file bị lỗi thì hãy xoá bản ghi Employer
      employerRepo.deleteById(emp.getId());
      e.printStackTrace();
      return "employer_add";
    }
    return "redirect:/employer";
  }

  @GetMapping(value = "/edit/{id}")
  public String editEmpId(Model model, @PathVariable("id") String id) {
    Optional<Employer> employer = Optional.of(employerRepo.findById(id));
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
      Model model) {

    // Nêú có lỗi thì trả về trình duyệt
    if (result.hasErrors()) {
      return "employer_edit";
    }

    String logoFileName = null;
    // Cập nhật logo vào ổ cứng
    if (!employerRequest.logo().getOriginalFilename().isEmpty()) {
      try {
        logoFileName = storageService.saveFile(employerRequest.logo(), employerRequest.id());
        // employerRepo.updateLogo(employerRequest.id(), logoFileName);
      } catch (IOException e) {
        // Nếu lưu file bị lỗi thì hãy xoá bản ghi Employer
        employerRepo.deleteById(employerRequest.id());
        e.printStackTrace();
        return "employer_edit";
      }
    }
    // Cập nhật lại Employer
    employerRepo.edit(Employer.builder()
        .id(employerRequest.id())
        .name(employerRequest.name())
        .website(employerRequest.website())
        .email(employerRequest.email())
        .logo_path(logoFileName == null ? employerRequest.logo_path() : logoFileName)
        .build());

    return "redirect:/employer";
  }

  @GetMapping(value = "/delete/{id}")
  public String deleteEmployerByID(@PathVariable String id) {
    Employer emp = employerRepo.deleteById(id);
    storageService.deleteFile(emp.getLogo_path());
    return "redirect:/employer";
  }
}
