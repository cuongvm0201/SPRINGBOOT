package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Job {
  @Id
  private String id;

  private String title;

  private String description;

  @Enumerated(EnumType.STRING)
  private City city;


  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private Employer employer;

  @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = false)
  private List<Applicant> applicants = new ArrayList<>();


  public void add(Applicant applicant) {
      applicant.setJob(this);
      applicants.add(applicant);
  }

  public void remove(Applicant applicant) {
      applicant.setJob(null);
      applicants.remove(applicant);
  }

  @PreRemove
  public void preRemove() {
      applicants.stream().forEach(p -> p.setJob(null));
      applicants.clear();
  }

  private LocalDateTime updated_at;
  private LocalDateTime created_at;
}
  


