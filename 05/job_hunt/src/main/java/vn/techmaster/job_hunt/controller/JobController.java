package vn.techmaster.job_hunt.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.repository.EmployerRepo;
import vn.techmaster.job_hunt.repository.JobRepo;
import vn.techmaster.job_hunt.request.EmployerRequest;
import vn.techmaster.job_hunt.request.JobRequest;

@Controller
@RequestMapping(value = "/job")
public class JobController {
    @Autowired private JobRepo jobRepo;
    @Autowired private EmployerRepo employerRepo;
    @GetMapping
    public String listAllJobs(Model model){
        model.addAttribute("jobs",jobRepo.getAll());
        return "jobs";
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
    @ModelAttribute EmployerRequest employerRequest, Model model){
        
        String uuid  = UUID.randomUUID().toString();
          Job newJob = new Job(uuid, id,
          jobRequest.title(),
          jobRequest.description(),
          jobRequest.city());
          jobRepo.addJob(newJob);
        return "redirect:/job";
    }

}
