package vn.techmaster.job_hunt.request;

import vn.techmaster.job_hunt.model.City;

public record JobRequest(String id, String emp_id, String title, String description, City city ) {
    
}
