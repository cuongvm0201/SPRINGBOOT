package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Applicant;

import java.util.Collection;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicant,String> {
    public Collection<Applicant> findApplicantsByJob_Id(String id);
}
