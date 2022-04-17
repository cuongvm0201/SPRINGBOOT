package vn.techmaster.job_hunt.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.model.Job;

@Repository
public class EmployerRepo {
    private HashMap<String, List<Job>> employer_jobs;
    private ConcurrentHashMap<String, Employer> employers = new ConcurrentHashMap<>();
    public EmployerRepo() {
        
    }


    public Employer add(Employer employer){
        String id  = UUID.randomUUID().toString();
        employer.setId(id);
        employers.put(id, employer);
        return employer;
    }

    public Collection<Employer> getAll(){
        return employers.values();
    }

    public Employer findById(String id){
        return employers.get(id);
    }
    public void updateLogo(String id, String logo_path) { 
        var emp = employers.get(id);
        emp.setLogo_path(logo_path);
        employers.put(id, emp);
      }

    public Employer deleteByID(String id){
        // var emp = employers.get(id);
        // employers.remove(emp);
        return employers.remove(id);
    }


    @PostConstruct
    public void addSomeData(){
        this.add(Employer.builder()
        .name("FPT")
        .website("https://fpt.com.vn")
        .logo_path("fpt.jpg")
        .email("hr@fpt.com.vn")
        .build());

        this.add(Employer.builder()
        .name("CMC")
        .website("https://cmc.com.vn")
        .logo_path("cmc.png")
        .email("hr@cmc.com.vn")
        .build());

        this.add(Employer.builder()
        .name("AMAZON")
        .website("https://amazon.com")
        .logo_path("amazon.png")
        .email("hr@amazon.com")
        .build());

        this.add(Employer.builder()
        .name("GOOGLE")
        .website("https://google.com")
        .logo_path("google.png")
        .email("hr@google.com")
        .build());
    }

    
}
