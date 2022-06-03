package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.Employer;
import com.example.demo.model.Job;
import com.example.demo.repository.ApplicantRepo;
import com.example.demo.repository.EmployerRepo;
import com.example.demo.repository.JobRepo;
import com.example.demo.service.ApplicantService;
import com.example.demo.service.EmployerService;
import com.example.demo.service.JobService;
import com.example.demo.request.JobRequest;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.SearchRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private JobRepo jobRepo;
  
    @GetMapping
    public String listAllJob(Model model, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        model.addAttribute("searchRequest", new SearchRequest());
        model.addAttribute("jobs", jobRepo.findAll());
        model.addAttribute("employers", employerService.getAll());
        model.addAttribute("totalApplicantMap", applicantService.countApplicantTotal());
        return "job_home";
        
    }
    @GetMapping(value = "admin/{id}")
    public String showJobDetailByID(Model model, @PathVariable String id, HttpSession session) {
        Job job = jobService.findById(id);
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        model.addAttribute("job", job);
        model.addAttribute("employer", employerService.findById(job.getEmployer().getId()));
        model.addAttribute("applicants", applicantService.findApplicantsByJob_id(job.getId()));
        return "job";
    }

    @GetMapping(value = "/{id}")
    public String showJobApplyByID(Model model, @PathVariable String id, HttpSession session) {
        Job job = jobService.findById(id);
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        model.addAttribute("job", job);
        model.addAttribute("employer", employerService.findById(job.getEmployer().getId()));
        model.addAttribute("applicants", applicantService.findApplicantsByJob_id(job.getId()));
        return "job_detail";
    }

    @GetMapping(value = "/add/{emp_id}")
    public String addEmployerForm(Model model, @PathVariable String emp_id, HttpSession session ) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        model.addAttribute("job", new JobRequest("", emp_id, "", "", null));
        return "job_add";
    }


    @GetMapping(value = "/search")
    public String searchKeyword(@RequestBody @ModelAttribute("searchRequest") SearchRequest searchRequest
    , Model model, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        model.addAttribute("jobs", jobService.filterJob(searchRequest));
        model.addAttribute("employers", employerService.getAll());
        model.addAttribute("totalApplicantMap", applicantService.countApplicantTotal());
        return "job_home";
    }

    @PostMapping(value = "/add")
    public String addEmployer(@Valid @ModelAttribute("job") JobRequest jobRequest,
                              BindingResult result,
                              Model model, HttpSession session) {

        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "job_add";
        }
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        Job newJob  = new Job();
        newJob.setTitle(jobRequest.getTitle());
        newJob.setDescription(jobRequest.getDescription());
        newJob.setCity(jobRequest.getCity());

        Optional<Employer> employer = employerService.findById(jobRequest.getEmp_id());

        // Thêm vào cơ sở dữ liệu
        jobService.addJobForEmployer(employer.get(),newJob);

        // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
        return "redirect:/employer/" + jobRequest.getEmp_id();
    }

    @GetMapping(value = "/edit/{id}")
    public String editJobId(Model model, @PathVariable("id") String id, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        model.addAttribute("user", userDTO);
        Optional<Job> job = Optional.of(jobService.findById(id));
        if (job.isPresent()) {
            Job currentJob = job.get();
            model.addAttribute("jobReq", new JobRequest(
                    currentJob.getId(),
                    currentJob.getEmployer().getId(),
                    currentJob.getTitle(),
                    currentJob.getDescription(),
                    currentJob.getCity()));
            // model.addAttribute("job", currentJob);
            model.addAttribute("employer", employerService.findById(currentJob.getEmployer().getId()));
        }
        return "job_edit";
    }

    @PostMapping(value = "/edit")
    public String edit(@Valid @ModelAttribute("jobReq") JobRequest jobRequest,
                       BindingResult result,
                       Model model, HttpSession session) {
                        UserDTO userDTO = (UserDTO) session.getAttribute("user");
                        System.out.println("Session ID: " + session.getId());
                        model.addAttribute("user", userDTO);               
        // Nêú có lỗi thì trả về trình duyệt
        if (result.hasErrors()) {
            return "job_edit";
        }

        // Thêm vào cơ sở dữ liệu
        jobService.update(Job.builder()
                .id(jobRequest.getId())
                .employer(employerService.findById(jobRequest.getEmp_id()).get())
                .title(jobRequest.getTitle())
                .description(jobRequest.getDescription())
                .city(jobRequest.getCity()).build());

        // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
        return "redirect:/employer/" + jobRequest.getEmp_id();
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteJobByID(@PathVariable String id, HttpSession session, Model model) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
                        System.out.println("Session ID: " + session.getId());
                        model.addAttribute("user", userDTO); 
        Job jobDel = jobService.deleteById(id);
        return "redirect:/employer/" + jobDel.getEmployer().getId();
    }
}
