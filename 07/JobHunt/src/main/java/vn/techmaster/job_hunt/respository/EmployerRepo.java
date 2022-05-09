package vn.techmaster.job_hunt.respository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.techmaster.job_hunt.model.Applicant;
import vn.techmaster.job_hunt.model.City;
import vn.techmaster.job_hunt.model.Employer;
import vn.techmaster.job_hunt.model.Job;
import vn.techmaster.job_hunt.model.Skill;

@Repository
public class EmployerRepo {
  private ConcurrentHashMap<String, Employer> employers = new ConcurrentHashMap<>();
  @Autowired
  private JobRepo jobRepo;
  @Autowired
  private ApplicantRepo applicantRepo;

  public EmployerRepo() {

  }

  // Cần sinh UUID ở đây
  public Employer add(Employer employer) {
    String id = UUID.randomUUID().toString();
    employer.setId(id);
    employers.put(id, employer);
    return employer;
  }

  //
  public Employer add(Employer employer, Job job) {
    String id = UUID.randomUUID().toString();
    employer.setId(id);
    employers.put(id, employer);
    //
    job.setEmp_id(id);
    jobRepo.addJob(job);
    return employer;
  }

  public Collection<Employer> getAll() {
    return employers.values();
  }

  public ConcurrentHashMap<String, Employer> getMapEmp(){
    return employers;
  }

  public Employer findById(String id) {
    return employers.get(id);
  }

  public Collection<Employer> findByName(String name) {
    return employers.entrySet().stream()
        .filter(x -> name.equals(x.getValue().getName()))
        .map(x -> x.getValue()).collect(Collectors.toList());
  }

  // Cập nhật logo của Employer
  public void updateLogo(String id, String logo_path) {
    var emp = employers.get(id);
    emp.setLogo_path(logo_path);
    employers.put(id, emp);
  }

  public void edit(Employer employer) {
    employers.put(employer.getId(), employer);
}

  public Employer deleteById(String id) {
    // var emp = employers.get(id);
    return employers.remove(id);
  }

  @PostConstruct
  public void addSomeData() {
    this.add(Employer.builder()
        .name("FPT")
        .website("https://fpt.com.vn")
        .logo_path("fpt.png")
        .email("vmcuong2192@gmail.com").build(),
        Job.builder()
            .title("Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.HaNoi).build());
    // Add more Job for FPT
    jobRepo.addJobForEmployer(this.findByName("FPT").iterator().next(),
        Job.builder()
            .title("02 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());
            //Add more Apllicant for 02 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("02 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("02 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("02 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            

    jobRepo.addJobForEmployer(this.findByName("FPT").iterator().next(),
        Job.builder()
            .title("03 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());
            //Add more Apllicant for 03 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("03 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("03 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("03 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
    jobRepo.addJobForEmployer(this.findByName("FPT").iterator().next(),
        Job.builder()
            .title("04 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());
            //Add more Apllicant for 02 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("04 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("04 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("04 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
    //
    this.add(Employer.builder()
        .name("CMC")
        .website("https://cmc.com.vn")
        .logo_path("cmc.png")
        .email("vmcuong2192@gmail.com").build(),
        Job.builder()
            .title("Backend C# Developer")
            .description("Long time job")
            .city(City.HoChiMinh).build());

    // Add more Job for CMC
    jobRepo.addJobForEmployer(this.findByName("CMC").iterator().next(),
        Job.builder()
            .title("05 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());
            //Add more Apllicant for 02 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("05 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("05 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("05 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());

    jobRepo.addJobForEmployer(this.findByName("CMC").iterator().next(),
        Job.builder()
            .title("06 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());

            //Add more Apllicant for 02 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("06 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("06 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("06 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());

    jobRepo.addJobForEmployer(this.findByName("CMC").iterator().next(),
        Job.builder()
            .title("07 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());

            //Add more Apllicant for 02 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("07 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("07 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("07 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());

    this.add(Employer.builder()
        .name("AMAZON")
        .website("https://amazon.com")
        .logo_path("amazon.png")
        .email("vmcuong2192@gmail.com").build(),
        Job.builder()
            .title("Automation Tester ")
            .description("Remote fulltime")
            .city(City.HaiPhong).build());

    // Add more Job for CMC
    jobRepo.addJobForEmployer(this.findByName("AMAZON").iterator().next(),
        Job.builder()
            .title("08 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());

            //Add more Apllicant for 02 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("08 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("08 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("08 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());

    jobRepo.addJobForEmployer(this.findByName("AMAZON").iterator().next(),
        Job.builder()
            .title("09 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());

            //Add more Apllicant for 02 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("09 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("09 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("09 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());

    jobRepo.addJobForEmployer(this.findByName("AMAZON").iterator().next(),
        Job.builder()
            .title("10 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());

            //Add more Apllicant for 02 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("10 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("10 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("10 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());

    this.add(Employer.builder()
        .name("GOOGLE")
        .website("https://google.com")
        .logo_path("google.png")
        .email("vmcuong2192@gmail.com").build(),
        Job.builder()
            .title("React Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());

    // Add more Job for CMC
    jobRepo.addJobForEmployer(this.findByName("GOOGLE").iterator().next(),
        Job.builder()
            .title("11 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());

            //Add more Apllicant for 02 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("11 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("11 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("11 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());

    jobRepo.addJobForEmployer(this.findByName("GOOGLE").iterator().next(),
        Job.builder()
            .title("12 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());

            //Add more Apllicant for 02 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("12 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("12 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("12 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());

    jobRepo.addJobForEmployer(this.findByName("GOOGLE").iterator().next(),
        Job.builder()
            .title("13 Fullstack Java Developer")
            .description("Remote fulltime")
            .city(City.DaNang).build());

            //Add more Apllicant for 02 Fullstack Java Developer
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("13 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura01@gmail.com")
            .name("Nakamura01")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("13 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura02@gmail.com")
            .name("Nakamura02")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());
            applicantRepo.addApplicantForJob(jobRepo.findByTitle("13 Fullstack Java Developer").iterator().next(), 
            Applicant.builder()
            .email("nakamura03@gmail.com")
            .name("Nakamura03")
            .phone("0977342466")
            .skills( new ArrayList<Skill>(EnumSet.allOf(Skill.class))).build());

  }

}
