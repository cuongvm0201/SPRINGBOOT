package vn.techmaster.job_hunt.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import vn.techmaster.job_hunt.model.Applicant;

@Repository
public class ApplicantRepo {
    private ConcurrentHashMap<String, Applicant> applicants = new ConcurrentHashMap<>();

    public Collection<Applicant> getAll(){
        return applicants.values();
      }

      public Applicant addApplicant(Applicant applicant) {
        String uuid = UUID.randomUUID().toString();
        applicant.setId(uuid);
        applicants.put(uuid, applicant);
        return applicant;
      }

      public Applicant findById(String id) {
        return applicants.get(id);
      }

      public List<Applicant> findByJobId(String job_id) {
        Optional<Entry<String, Applicant>>  opt = applicants.entrySet()
        .stream()
        .filter(e -> e.getValue().getJob_id().equalsIgnoreCase(job_id))
        .findFirst();
        // Optional.ofNullable(nullName).orElse("john");
        // Job job = opt.ofNullable(null).orElse("john");
        List<Applicant> applicantList = new ArrayList<>();
        if(opt.isPresent())
        applicantList.add(opt.get().getValue());
        return  applicantList;
      }


      public Applicant deleteById(String id) {
        return applicants.remove(id);
      }

      @PostConstruct
    public void addSomeData(){
  
      
    }
}
