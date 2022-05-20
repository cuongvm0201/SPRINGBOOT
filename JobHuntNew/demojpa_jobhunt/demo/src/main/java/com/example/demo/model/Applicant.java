package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Applicant {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;

    @ElementCollection(targetClass=Skill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="applicant_skill")
    @Column(name="skills")
    Collection<Skill> skills;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id")
    private Job job;

    @PreRemove
    public void preRemove() {
        job.remove(this);
    }
     
}
