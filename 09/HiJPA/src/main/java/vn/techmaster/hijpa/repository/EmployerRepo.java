package vn.techmaster.hijpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.techmaster.hijpa.model.Employer;

@Repository
public interface EmployerRepo extends JpaRepository<Employer, Long> {
  
}
