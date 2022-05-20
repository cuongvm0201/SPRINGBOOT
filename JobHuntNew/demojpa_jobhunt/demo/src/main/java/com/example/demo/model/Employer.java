package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Employer {
  @Id
  private String id;

  private String name;

  private String logo_path;

  private String website;

  private String email;

  @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
  @JoinColumn(name = "employer_id")
  private List<Job> employers = new ArrayList<>();

  public void addJob(Job job) {
    job.setEmployer(this);
    employers.add(job);    
  }

  public void removeComment(Job job) {
    job.setEmployer(null);
    employers.remove(job);   
  }
}
