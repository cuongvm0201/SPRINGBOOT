package com.example.demo.service;

import com.example.demo.model.Applicant;
import com.example.demo.model.Job;

import java.util.Collection;
import java.util.Map;

public interface ApplicantService {
    public Collection<Applicant> getAll();
    public Applicant addApplicantForJob(Job job, Applicant applicant);
    public Applicant findById(String id);

    public Collection<Applicant> findApplicantsByJob_id(String id);
    public Applicant deleteById(String id);
    public void update(Applicant applicant);
    public Map<Job, Long> countApplicantTotal();


}
