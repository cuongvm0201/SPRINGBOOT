package vn.techmaster.demomanageruser_jobhunt.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import vn.techmaster.demomanageruser_jobhunt.model.Applicant;
import vn.techmaster.demomanageruser_jobhunt.model.Job;

@Repository
public class ApplicantRepo {
  private ConcurrentHashMap<String, Applicant> applicants = new ConcurrentHashMap<>();

  public Collection<Applicant> getAll(){
    return applicants.values();
  }

  public Applicant add(Applicant applicant) {
    String id = UUID.randomUUID().toString();
    applicant.setId(id);
    applicants.put(id, applicant);
    return applicant;
  }

  public Applicant addApplicantForJob(Job job, Applicant applicant) {
    String id = UUID.randomUUID().toString();
    applicant.setId(id);
    applicant.setJob_id(job.getId());
    applicants.put(id, applicant);
    return applicant;
  }

  public Applicant findById(String id) {
    return applicants.get(id);
  }

  public Collection<Applicant> findByJobId(String jobId) {
    return applicants.entrySet().stream()
		.filter(x -> jobId.equals(x.getValue().getJob_id()))
		.map(x->x.getValue()).collect(Collectors.toList());
  }

  public Applicant deleteById(String id) {
    return applicants.remove(id);
  }

  public void update(Applicant applicant){
    applicants.put(applicant.getId(), applicant);
  }

  public Map<String, Long> countApplicant(){
    return applicants.values().stream().collect(Collectors.groupingBy(Applicant::getJob_id, Collectors.counting()));
  }
}
