package vn.techmaster.job_hunt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Applicant {
    private String id;
    private String job_id;
    private String name;
    private String email;
    private String mobile;
    private String skill;
}
