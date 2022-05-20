package com.example.demo.service;

import com.example.demo.model.Employer;
import com.example.demo.model.Job;
import com.example.demo.request.SearchRequest;

import java.util.Collection;

public interface JobService {
    public Collection<Job> getAll();
    public Job addJobForEmployer(Employer employer, Job job);
    public Job findById(String id);
    public Collection<Job> findByEmpId(String empId);
    public Job deleteById(String id);
    public void update(Job job);
    // public Collection<Job> filterJob(SearchRequest searchRequest);
    // public JobReponse pageJob(int page);
    public Object filterJob(SearchRequest searchRequest);
}
