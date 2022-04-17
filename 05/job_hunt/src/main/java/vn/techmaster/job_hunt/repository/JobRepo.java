package vn.techmaster.job_hunt.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import vn.techmaster.job_hunt.model.City;
import vn.techmaster.job_hunt.model.Job;


@Repository
public class JobRepo {
  private ConcurrentHashMap<String, Job> jobs = new ConcurrentHashMap<>();

  public Collection<Job> getAll(){
    return jobs.values();
  }

  public Job addJob(Job job) {
    String uuid = UUID.randomUUID().toString();
    job.setId(uuid);
    jobs.put(uuid, job);
    return job;
  }

  public Job findById(String id) {
    return jobs.get(id);
  }

  public List<Job> findByEmpId(String emp_Id) {
    Optional<Entry<String, Job>>  opt = jobs.entrySet()
    .stream()
    .filter(e -> e.getValue().getEmp_id().equalsIgnoreCase(emp_Id))
    .findFirst();
    // Optional.ofNullable(nullName).orElse("john");
    // Job job = opt.ofNullable(null).orElse("john");
    List<Job> jobList = new ArrayList<>();
    if(opt.isPresent())
    jobList.add(opt.get().getValue());
    return  jobList;
  }
  public Job deleteById(String id) {
    return jobs.remove(id);
  }

  public void update(Job job){
    jobs.put(job.getId(), job);
  }


    @PostConstruct
    public void addSomeData(){
  
      
    }
}