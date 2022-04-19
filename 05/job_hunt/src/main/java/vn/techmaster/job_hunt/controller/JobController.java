package vn.techmaster.job_hunt.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.job_hunt.model.Applicant;
import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.repository.ApplicantRepo;
import vn.techmaster.job_hunt.repository.EmployerRepo;
import vn.techmaster.job_hunt.repository.JobRepo;
import vn.techmaster.job_hunt.request.EmployerRequest;
import vn.techmaster.job_hunt.request.JobRequest;

@Controller
@RequestMapping(value = "/job")
public class JobController {
    @Autowired private JobRepo jobRepo;
    @Autowired private EmployerRepo employerRepo;
    @Autowired private ApplicantRepo applicantRepo;
    @GetMapping
    public String listAllJobs(Model model){
        
        model.addAttribute("jobs",jobRepo.getAll());
        return "jobs";
    }

    @GetMapping(value = "/{id}")
    public String showEmployerDetailByID(Model model, @PathVariable String id){
        List<Applicant> listApplicant = applicantRepo.findByJobId(id);
        model.addAttribute("applicants",listApplicant);
        model.addAttribute("job", jobRepo.findById(id));
        return "job";
    }

    @GetMapping(value = "/add/{id}")
    public String addJobForm(Model model, @PathVariable("id") String id ){
        model.addAttribute("employer", employerRepo.findById(id));
        model.addAttribute("job", Job.builder().emp_id(id).title(null).description(null).city(null).build());
        return "job_add";
    }

    @PostMapping(value = "/add/{id}")
    public String addJob(@ModelAttribute JobRequest jobRequest,
    @PathVariable String id, 
    @ModelAttribute EmployerRequest employerRequest, Model model, BindingResult result){
        if (result.hasErrors()) {
            return "job_add";
          }
        String uuid  = UUID.randomUUID().toString();
          Job newJob = new Job(uuid, id,
          jobRequest.title(),
          jobRequest.description(),
          jobRequest.city());
          jobRepo.addJob(newJob);
        return "redirect:/job";
    }

    @GetMapping(value = "/update/{emp_id}/{id}")
    public String updateJobForm(Model model, @PathVariable("emp_id") String emp_id, @PathVariable("id") String id ){
        model.addAttribute("jobs", Job.builder().title(null).description(null).city(null).build());
        return "job_update";
    }


    @PutMapping(value = "/update/{emp_id}/{id}" )
    public String updateJob(Model model, @PathVariable("id") String id,@PathVariable("emp_id") String emp_id,@ModelAttribute JobRequest jobRequest ){
        Job newJob = new Job(id, emp_id,
        jobRequest.title(),
        jobRequest.description(),
        jobRequest.city());
        jobRepo.update(newJob);
        return "redirect:/job";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteJob(@PathVariable String id){
        Job job = jobRepo.deleteById(id);
        return "redirect:/job";
    }

}
