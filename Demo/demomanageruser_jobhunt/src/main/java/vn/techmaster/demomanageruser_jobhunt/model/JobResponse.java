package vn.techmaster.demomanageruser_jobhunt.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {
    private List<Job> jobs;
    private int totalPage;
}
