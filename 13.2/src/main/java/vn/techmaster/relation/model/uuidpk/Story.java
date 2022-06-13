package vn.techmaster.relation.model.uuidpk;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@NoArgsConstructor
public class Story {
  @Id //@GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;  
  private String name;

  public Story(String name) {
    this.id = UUID.randomUUID();
    this.name = name;
  }
}
