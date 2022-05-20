package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Employer;
import com.example.demo.model.Job;
import com.example.demo.repository.JobRepo;
import com.example.demo.request.SearchRequest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobServiceImple implements JobService{
    @Autowired
    private JobRepo jobRepository;

    @Override
    public Collection<Job> getAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job addJobForEmployer(Employer employer, Job job) {
        String id = UUID.randomUUID().toString();
        job.setId(id);
        job.setEmployer(employer);
        return jobRepository.save(job);
    }

    @Override
    public Job findById(String id) {
        Optional<Job> job = jobRepository.findById(id);
        return  job.get();
    }

    @Override
    public Collection<Job> findByEmpId(String empId) {
        return jobRepository.findJobsByEmployer_Id(empId);
    }

    @Override
    public Job deleteById(String id) {
        Optional<Job> job = jobRepository.findById(id);
        jobRepository.deleteById(id);
        return job.get();
    }

    @Override
    public void update(Job job) {
         jobRepository.save(job);
    }

    @Override
    public List<Job> filterJob(SearchRequest searchRequest) {
        List<Job> jobs =(List<Job>) getAll();
        return  jobs.stream()
                .filter(job -> 
                job.getTitle().toLowerCase().contains(searchRequest.getKeyword().toLowerCase())
             && job.getCity().toString().equals(searchRequest.getCity().toString()))
                .collect(Collectors.toList());
    }
}


