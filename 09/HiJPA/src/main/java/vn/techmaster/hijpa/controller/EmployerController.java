package vn.techmaster.hijpa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.hijpa.model.Employer;
import vn.techmaster.hijpa.repository.EmployerRepo;

@RestController
@RequestMapping("/api/employer")
public class EmployerController {
  @Autowired private EmployerRepo employerRepo;
  @GetMapping
  public List<Employer> getAll() {
    return employerRepo.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Employer> findById(@PathVariable("id") Long id) {
    return employerRepo.findById(id);
  }

}
