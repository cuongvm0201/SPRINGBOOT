package vn.techmaster.hijpa.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table
@Entity
public class Job {
  @Id @GeneratedValue private UUID id;
  private String title;
  private String description;
}
