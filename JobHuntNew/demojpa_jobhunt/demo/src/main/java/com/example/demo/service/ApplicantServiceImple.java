package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Applicant;
import com.example.demo.model.Job;
import com.example.demo.repository.ApplicantRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicantServiceImple implements ApplicantService{

    @Autowired
    private ApplicantRepo applicantRepository;

    @Override
    public Collection<Applicant> getAll() {
        return applicantRepository.findAll();
    }

    @Override
    public Applicant addApplicantForJob(Job job, Applicant applicant) {
        String id = UUID.randomUUID().toString();
        applicant.setId(id);
        applicant.setJob(job);
        applicantRepository.save(applicant);
        return applicant;
    }

    @Override
    public Applicant findById(String id) {
        Optional<Applicant> applicant = applicantRepository.findById(id);
        return applicant.get();
    }

    @Override
    public Collection<Applicant> findApplicantsByJob_id(String id) {
        return applicantRepository.findApplicantsByJob_Id(id);
    }

    @Override
    public Applicant deleteById(String id) {
        Optional<Applicant> applicant = applicantRepository.findById(id);
        applicantRepository.deleteById(id);
        return applicant.get();
    }

    @Override
    public void update(Applicant applicant) {
          applicantRepository.save(applicant);
    }

    @Override
    public Map<Job, Long> countApplicantTotal() {
        List<Applicant> applicants = (List<Applicant>) getAll();
        return applicants
                .stream()
                .collect(Collectors.groupingBy(Applicant::getJob,Collectors.counting()));
    }
}
