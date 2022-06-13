package vn.techmaster.relation.model.manymany.separate_primary_key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "subject")
@Table(name = "subject")
@Data
@NoArgsConstructor
public class Subject {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  public Subject(String name) {
    this.name = name;
  }

  @OneToMany(mappedBy = "subject")
  @JsonBackReference
  private List<StudentSubject> studentSubjects = new ArrayList<>();

  @JsonGetter(value = "students")
  @Transient
  public Map<String, Float> getStudents() {
    Map<String, Float> studentScore = new HashMap<>();
    studentSubjects.stream().forEach(studentSubject -> {
      studentScore.put(studentSubject.getStudent().getName(), studentSubject.getScore());
    });
    return studentScore;
  }
}
