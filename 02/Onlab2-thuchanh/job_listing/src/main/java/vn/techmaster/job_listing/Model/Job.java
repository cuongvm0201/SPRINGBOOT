package vn.techmaster.job_listing.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Job {
    private String id;
    private String title;
    private String description;
    private String location;
    private long min_salary;
    private long max_salary;
    private String email_to;
}
