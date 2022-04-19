package vn.techmaster.job_hunt.controller;

import java.util.UUID;

import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.job_hunt.model.Applicant;
import vn.techmaster.job_hunt.repository.ApplicantRepo;
import vn.techmaster.job_hunt.repository.EmployerRepo;
import vn.techmaster.job_hunt.repository.JobRepo;
import vn.techmaster.job_hunt.request.ApplicantRequest;
import vn.techmaster.job_hunt.request.JobRequest;

@Controller
@RequestMapping(value = "/applicant")
public class ApplicantController {
    @Autowired private JobRepo jobRepo;
    @Autowired private EmployerRepo employerRepo;
    @Autowired private ApplicantRepo applicantRepo;

    @GetMapping
    public String listAllApplicants(Model model){
        model.addAttribute("applicants",applicantRepo.getAll());
        return "applicants";
    }

    @GetMapping(value = "/add/{id}")
    public String addApplicantForm(Model model, @PathVariable("id") String id ){
        model.addAttribute("job", jobRepo.findById(id));
        model.addAttribute("applicant", Applicant.builder().job_id(id).name(null).email(null).mobile(null).skill(null).build());
        return "applicant_add";
    }

    @PostMapping(value = "/add/{id}")
    public String addApplicant(@ModelAttribute ApplicantRequest applicantRequest,
    @PathVariable String id, 
    @ModelAttribute JobRequest jobRequest, Model model, BindingResult result){
        if (result.hasErrors()) {
            return "applicant_add";
          }
        String uuid  = UUID.randomUUID().toString();
          Applicant newApplicant = new Applicant(uuid, jobRequest.id(),
          applicantRequest.name(),
          applicantRequest.email(),
          applicantRequest.mobile(),applicantRequest.skill());
          applicantRepo.addApplicant(newApplicant);
        return "redirect:/applicant";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteJob(@PathVariable String id){
        Applicant applicant = applicantRepo.deleteById(id);
        return "redirect:/applicant";
    }

}
