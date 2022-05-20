package vn.techmaster.demomanageruser_jobhunt.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.techmaster.demomanageruser_jobhunt.dto.UserDTO;
import vn.techmaster.demomanageruser_jobhunt.model.City;
import vn.techmaster.demomanageruser_jobhunt.model.Employer;
import vn.techmaster.demomanageruser_jobhunt.model.Job;
import vn.techmaster.demomanageruser_jobhunt.model.JobResponse;
import vn.techmaster.demomanageruser_jobhunt.request.EmployerRequest;
import vn.techmaster.demomanageruser_jobhunt.request.JobRequest;
import vn.techmaster.demomanageruser_jobhunt.request.SearchRequest;
import vn.techmaster.demomanageruser_jobhunt.repository.ApplicantRepo;
import vn.techmaster.demomanageruser_jobhunt.repository.EmployerRepo;
import vn.techmaster.demomanageruser_jobhunt.repository.JobRepo;

@Controller
@RequestMapping(value = "/job")
public class JobController {
  @Autowired
  private JobRepo jobRepo;
  @Autowired
  private EmployerRepo empRepo;
  @Autowired
  private ApplicantRepo applicantRepo;

  

  @GetMapping(value = "/add/{emp_id}")
  public String addEmployerForm(Model model, @PathVariable String emp_id) {
    model.addAttribute("job", new JobRequest("","","","",null));
    return "job_add";
  }

  @PostMapping(value = "/add")
  public String addEmployer(@Valid @ModelAttribute("job") JobRequest jobRequest,
      BindingResult result,
      Model model) {

    // Nêú có lỗi thì trả về trình duyệt
    if (result.hasErrors()) {
      return "job_add";
    }

    // Thêm vào cơ sở dữ liệu
    jobRepo.addJob(Job.builder()
        .emp_id(jobRequest.emp_id())
        .title(jobRequest.title())
        .description(jobRequest.description())
        .city(jobRequest.city()).build());

    // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
    return "redirect:/employer/" + jobRequest.emp_id();
  }

  @GetMapping(value = "/edit/{id}")
  public String editJobId(Model model, @PathVariable("id") String id, HttpSession session) {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    model.addAttribute("user", userDTO);
    Optional<Job> job = Optional.of(jobRepo.findById(id));
    if (job.isPresent()) {
      Job currentJob = job.get();
      model.addAttribute("jobReq", new JobRequest(
          currentJob.getId(),
          currentJob.getEmp_id(),
          currentJob.getTitle(),
          currentJob.getDescription(),
          currentJob.getCity()));
      // model.addAttribute("job", currentJob);
      model.addAttribute("employer", empRepo.findById(currentJob.getEmp_id()));
    }
    return "job_edit";
  }

  

  @PostMapping(value = "/edit")
  public String edit(@Valid @ModelAttribute("jobReq") JobRequest jobRequest,
      BindingResult result,
      Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", userDTO);
    // Nêú có lỗi thì trả về trình duyệt
    if (result.hasErrors()) {
      return "job_edit";
    }

    // Thêm vào cơ sở dữ liệu
    jobRepo.update(Job.builder()
        .id(jobRequest.id())
        .emp_id(jobRequest.emp_id())
        .title(jobRequest.title())
        .description(jobRequest.description())
        .city(jobRequest.city())
        .created_at(jobRepo.findById(jobRequest.id()).getCreated_at())
        .build());

    // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
    return "redirect:/employer/" + jobRequest.emp_id();
  }

  @GetMapping(value = "/delete/{id}")
  public String deleteJobByID(@PathVariable String id, HttpSession session, Model model) {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    model.addAttribute("user", userDTO);
    Job jobDel = jobRepo.deleteById(id);
    return "redirect:/employer/" + jobDel.getEmp_id();
  }

  @GetMapping(value = "/{id}")
  public String showJobDetailByID(Model model, @PathVariable String id,HttpSession session) {
    Job job = jobRepo.findById(id);
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    model.addAttribute("user", userDTO);
    model.addAttribute("job", job);
    model.addAttribute("employer", empRepo.findById(job.getEmp_id()));
    return "job_detail";
  }
 
}
