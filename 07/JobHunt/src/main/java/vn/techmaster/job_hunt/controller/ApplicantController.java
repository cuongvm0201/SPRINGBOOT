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

import vn.techmaster.job_hunt.model.Applicant;
import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.request.ApplicantRequest;
import vn.techmaster.job_hunt.request.EmployerRequest;
import vn.techmaster.job_hunt.respository.ApplicantRepo;
import vn.techmaster.job_hunt.respository.EmployerRepo;
import vn.techmaster.job_hunt.respository.JobRepo;
import vn.techmaster.job_hunt.service.MailService;

@Controller
@RequestMapping(value = "/applicant")
public class ApplicantController {
  @Autowired
  private JobRepo jobRepo;
  @Autowired
  private EmployerRepo empRepo;
  @Autowired
  private ApplicantRepo applicantRepo;
  @Autowired
  private MailService mailService;

  @GetMapping
  public String listAll(Model model) {
    model.addAttribute("applicants", applicantRepo.getAll());
    return "applicants";
  }

  @GetMapping(value = "/apply/{job_id}")
  public String applyForm(Model model, @PathVariable String job_id) {
    model.addAttribute("applicantReq", new ApplicantRequest(null, job_id, null, null, null, null));
    return "applicant_apply";
  }

  @PostMapping(value = "/add")
  public String addApplicant(@Valid @ModelAttribute("applicantReq") ApplicantRequest applicantRequest,
      BindingResult result,
      Model model) {

    // Nêú có lỗi thì trả về trình duyệt
    if (result.hasErrors()) {
      return "applicant_apply";
    }

    // Thêm vào cơ sở dữ liệu
    applicantRepo.add(Applicant.builder()
        .job_id(applicantRequest.job_id())
        .name(applicantRequest.name())
        .email(applicantRequest.email())
        .phone(applicantRequest.phone())
        .skills(applicantRequest.skills())
        .build());
    // send email
    Job job = jobRepo.findById(applicantRequest.job_id());
    try {
      mailService.sendEmail(empRepo.findById(job.getEmp_id()).getEmail(), job.getTitle());
    } catch (Exception e) {
      return "error_page";
    }

    return "redirect:/job/" + applicantRequest.job_id();
  }

  @GetMapping(value = "/{id}")
  public String editId(Model model, @PathVariable("id") String id) {
    Optional<Applicant> applicantOpt = Optional.of(applicantRepo.findById(id));
    if (applicantOpt.isPresent()) {
      Applicant currentApplicant = applicantOpt.get();
      model.addAttribute("applicantReq", new ApplicantRequest(
          currentApplicant.getId(),
          currentApplicant.getJob_id(),
          currentApplicant.getName(),
          currentApplicant.getEmail(),
          currentApplicant.getPhone(),
          currentApplicant.getSkills()));
      // model.addAttribute("job", currentJob);
      // model.addAttribute("employer", empRepo.findById(currentJob.getEmp_id()));
    }
    return "applicant_edit";
  }

  @PostMapping(value = "/edit", params = "action=save")
  public String edit(@Valid @ModelAttribute("applicantReq") ApplicantRequest applicantRequest,
      BindingResult result,
      Model model) {

    // Nêú có lỗi thì trả về trình duyệt
    if (result.hasErrors()) {
      return "applicant_edit";
    }

    // Thêm vào cơ sở dữ liệu
    applicantRepo.update(Applicant.builder()
        .email(applicantRequest.email())
        .id(applicantRequest.id())
        .job_id(applicantRequest.job_id())
        .name(applicantRequest.name())
        .phone(applicantRequest.phone())
        .skills(applicantRequest.skills())
        .build());

    // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
    return "redirect:/job/" + applicantRequest.job_id();
  }

  @PostMapping(value = "/edit", params = "action=delete")
  public String delete(@ModelAttribute("applicant") ApplicantRequest applicantRequest) {
    applicantRepo.deleteById(applicantRequest.id());
    return "redirect:/job/" + applicantRequest.job_id();
  }
}
