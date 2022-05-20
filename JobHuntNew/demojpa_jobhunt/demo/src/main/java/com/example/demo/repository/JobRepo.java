package com.example.demo.repository;

import java.util.Collection;

import com.example.demo.model.Job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<Job,String>{
    Collection<Job> findJobsByEmployer_Id(String employer_id);
}
