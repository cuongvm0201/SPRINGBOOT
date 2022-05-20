package vn.techmaster.hellojpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.techmaster.hellojpa.model.Employer;

public interface EmployerRepo extends JpaRepository<Employer, Long>  {
  
}
