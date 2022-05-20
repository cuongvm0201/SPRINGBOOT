package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Applicant;
import com.example.demo.model.Employer;
import com.example.demo.model.Job;
import com.example.demo.model.Skill;
import com.example.demo.service.ApplicantService;
import com.example.demo.service.EmployerService;
import com.example.demo.service.JobService;
import com.example.demo.service.MailService;
import com.example.demo.request.ApplicantRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/applicant")
public class ApplicantController {
    @Autowired
    private JobService jobService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private MailService mailService;

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("applicants", applicantService.getAll());
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

        Applicant applicant = Applicant.builder()
        .name(applicantRequest.getName())
        .email(applicantRequest.getEmail())
        .phone(applicantRequest.getPhone())
        .skills(applicantRequest.getSkills())
        .build();
        Job job = jobService.findById(applicantRequest.getJob_id());

        applicantService.addApplicantForJob(job, applicant);


        try {
            mailService.sendEmail(employerService.findById(job.getEmployer().getId()).get().getEmail(), job.getTitle());
        } catch (Exception e) {
            return "error_page";
        }

        return "redirect:/job/" + applicantRequest.getJob_id();
    }

    @GetMapping(value = "/{id}")
    public String editId(Model model, @PathVariable("id") String id) {
        Optional<Applicant> applicantOpt = Optional.of(applicantService.findById(id));
        if (applicantOpt.isPresent()) {
            Applicant currentApplicant = applicantOpt.get();
            model.addAttribute("applicantReq", new ApplicantRequest(
                    currentApplicant.getId(),
                    currentApplicant.getJob().getId(),
                    currentApplicant.getName(),
                    currentApplicant.getEmail(),
                    currentApplicant.getPhone(),
                    (List<Skill>) currentApplicant.getSkills()));
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
        applicantService.update(Applicant.builder()
                .email(applicantRequest.getEmail())
                .id(applicantRequest.getId())
                .name(applicantRequest.getName())
                .job(jobService.findById(applicantRequest.getJob_id()))
                .phone(applicantRequest.getPhone())
                .skills(applicantRequest.getSkills())
                .build());

        // http://localhost:8080/employer/2f3fa6ef-77f1-460a-8fcb-3ac08219bb81
        return "redirect:/job/" + applicantRequest.getJob_id();
    }

    @PostMapping(value = "/edit", params = "action=delete")
    public String delete(@ModelAttribute("applicant") ApplicantRequest applicantRequest) {
        applicantService.deleteById(applicantRequest.getId());
        return "redirect:/job/" + applicantRequest.getJob_id();
    }
}
