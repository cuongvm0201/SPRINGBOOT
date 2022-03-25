package vn.techmaster.job_listing.dto;

public record JobRequest(String title, String description, String location, long min_salary, long max_salary, String email_to ) {
    
}
