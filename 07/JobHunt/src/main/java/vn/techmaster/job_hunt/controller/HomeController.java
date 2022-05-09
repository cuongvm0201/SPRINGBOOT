package vn.techmaster.job_hunt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.job_hunt.respository.ApplicantRepo;
import vn.techmaster.job_hunt.respository.EmployerRepo;
import vn.techmaster.job_hunt.respository.JobRepo;

@Controller
@RequestMapping("/")
public class HomeController {

  @GetMapping
  public String showHomePage() {
    return "redirect:/job";
  }




}
