package com.example.demo;

import com.example.demo.model.Applicant;
import com.example.demo.model.City;
import com.example.demo.model.Employer;
import com.example.demo.model.Job;
import com.example.demo.model.Skill;
import com.example.demo.repository.ApplicantRepo;
import com.example.demo.repository.EmployerRepo;
import com.example.demo.repository.JobRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;

import javax.persistence.EntityManager;

import javax.transaction.Transactional;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired EntityManager em;
    @Autowired
	private EmployerRepo employerRepo;

	@Autowired
	private JobRepo jobRepo;

    @Autowired
    private ApplicantRepo applicantRepo;

	
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);


    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
       Employer employer1 = Employer.builder()
       .id("1").name("FPT")
       .website("https://fpt.com.vn")
       .email("vmcuong2192@gmail.com")
       .logo_path("fpt.png")
       .build();
       Employer employer2 = Employer.builder()
       .id("2").name("CMC")
       .website("https://cmc.com.vn")
       .email("vmcuong2192@gmail.com")
       .logo_path("cmc.jpg")
       .build();
       Employer employer3 = Employer.builder()
       .id("3")
       .name("AMAZON")
       .website("https://amazon.com")
       .email("vmcuong2192@gmail.com")
       .logo_path("amazon.png")
       .build();
       Employer employer4 = Employer.builder()
       .id("4")
       .name("GOOGLE")
       .website("https://google.com")
       .email("vmcuong2192@gmail.com")
       .logo_path("google.png")
       .build();
       employerRepo.save(employer1);
       employerRepo.save(employer2);
       employerRepo.save(employer3);
       employerRepo.save(employer4);

        Job job = Job.builder()
        .id("11")
        .title("Fullstack Java Developer")
        .description("Remote fulltime")
        .city(City.HaNoi)
        .created_at(LocalDateTime.now())
        .updated_at(LocalDateTime.now())
        .employer(employer2)
        .build();

        Job job1 = Job.builder()
        .id("12")
        .title("Fullstack C# Developer")
        .description("Remote fulltime")
        .city(City.HaNoi)
        .created_at(LocalDateTime.now())
        .updated_at(LocalDateTime.now())
        .employer(employer1)
        .build();
		jobRepo.save(job);
		jobRepo.save(job1);

        Applicant applicant = Applicant.builder()
        .id("111")
        .name("Nakamura02")
        .email("nakamura02@gmail.com")
        .phone("0977342466")
        .skills(new ArrayList<Skill>(EnumSet.allOf(Skill.class)))
        .job(job)
        .build();
	
        Applicant applicant1 = Applicant.builder()
        .id("222")
        .name("Nakamura01")
        .email("nakamura02@gmail.com")
        .phone("0977342466")
        .skills(new ArrayList<Skill>(EnumSet.allOf(Skill.class)))
        .job(job1)
        .build();
		applicantRepo.save(applicant);
		applicantRepo.save(applicant1);

    }

}
