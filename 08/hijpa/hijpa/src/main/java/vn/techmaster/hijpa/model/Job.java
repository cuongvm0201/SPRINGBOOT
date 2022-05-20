package vn.techmaster.hijpa.model;

import java.util.UUID;


import javax.persistence.*;

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
