package vn.techmaster.demomanageruser_jobhunt.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {
  private String id;
  private String emp_id;
  private String title;
  private String description;
  private City city;
  private LocalDateTime updated_at;
  private LocalDateTime created_at;
}
