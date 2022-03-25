package vn.techmaster.job_listing.Controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.job_listing.Model.Job;
import vn.techmaster.job_listing.dto.JobRequest;

@RestController
@RequestMapping("/job")
public class UserController {
  private ConcurrentHashMap<String, Job> jobs;

  public UserController() {
    jobs = new ConcurrentHashMap<>();
    jobs.put("AB-01", new Job("AB-01", "Tuyển dụng 3 Dev Java", "Yêu cầu lập trình viên Java > 2 năm kinh nghiệm",
        "Hà Nội, Ha Noi, hanoi", 7000000, 15000000, "congnghe@company.com.vn"));
    jobs.put("AB-02", new Job("AB-02", "Tuyển dụng 5 Dev iOS", "Yêu cầu lập trình viên iOS > 1 năm kinh nghiệm",
        "TP HCM, Sài Gòn, saigon", 8000000, 18000000, "tuyendungdev@techcombank.com.vn"));
    jobs.put("CD-01", new Job("CD-01", "Tuyển dụng 2 Dev NodeJS", "Yêu cầu lập trình viên NodeJS > 3 năm kinh nghiệm",
        "Đà Nẵng, Da Nang, danang", 10000000, 25000000, "hr@vpbank.com.vn"));
        jobs.put("CD-02", new Job("CD-02", "Tuyển dụng 4 Dev Java/PHP/.Net", "Yêu cầu lập trình viên > 2 năm kinh nghiệm",
        "Hải Phòng, Hai Phong, haiphong", 15000000, 30000000, "tuyendung@tpbank.com.vn"));
  }

  @GetMapping
  public List<Job> getBooks() {
    return jobs.values().stream().toList();
  }

  @PostMapping
  public Job createNewJob(@RequestBody JobRequest jobRequest) {
    String uuid = UUID.randomUUID().toString();
    Job newJob = new Job(uuid, jobRequest.title(), jobRequest.description(), jobRequest.location(),
        jobRequest.min_salary(), jobRequest.max_salary(), jobRequest.email_to());
    jobs.put(uuid, newJob);
    return newJob;
  }

  @GetMapping(value = "/{id}")
  public Job getBookById(@PathVariable("id") String id) {
    return jobs.get(id);
  }

  @PutMapping(value = "/{id}")
  public Job updateBookById(@PathVariable("id") String id, @RequestBody JobRequest jobRequest) {
    Job updateJob = new Job(id, jobRequest.title(), jobRequest.description(), jobRequest.location(),
        jobRequest.min_salary(), jobRequest.max_salary(), jobRequest.email_to());
    // books.put(id, updateBook);
    jobs.replace(id, updateJob);
    return updateJob;
  }

  @DeleteMapping(value = "/{id}")
  public Job deleteJobById(@PathVariable("id") String id) {
    Job removedJob = jobs.remove(id);
    return removedJob;
  }

  @GetMapping(value = "/sortbylocation")
  public List<Job> sortByLocation() {
    return jobs.values().stream().sorted(Comparator.comparing(Job::getLocation)).collect(Collectors.toList());
  }

  @GetMapping(value = "/sortbyid")
  public List<Job> sortByID() {
    return jobs.values().stream().sorted(Comparator.comparing(Job::getId)).collect(Collectors.toList());
  }

  @GetMapping(value = "/sortbyminsalary")
  public List<Job> sortByReversedminSalary() {
    return jobs.values().stream().sorted(Comparator.comparing(Job::getMin_salary).reversed())
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/sortbytitle")
  public List<Job> sortByTitle() {
    return jobs.values().stream().sorted(Comparator.comparing(Job::getTitle)).collect(Collectors.toList());
  }

  @GetMapping(value = "/salary/{salary}")
  public List<Job> findBySalary(@PathVariable("salary") long salary) {

    return jobs.values().stream().filter(n -> ((n.getMin_salary() <= (salary)) && (n.getMax_salary() >= (salary))))
        .collect(Collectors.toList());
  }

  @GetMapping(value = "/keyword/{keyword}")
  public List<Job> findByKeyWord(@PathVariable("keyword") String keyword) {

    return jobs.values().stream().filter(n -> (n.getTitle().toLowerCase().contains(keyword.toLowerCase())
        || n.getDescription().toLowerCase().contains(keyword.toLowerCase()))).collect(Collectors.toList());
  }

  @GetMapping(value = "/query")
  public List<Job> findByLocateKeyword(@RequestParam("location") String location, @RequestParam("keyword") String keyword){

    return jobs.values().stream().filter(n -> ((n.getTitle().toLowerCase().contains(keyword.toLowerCase())
    || n.getDescription().toLowerCase().contains(keyword.toLowerCase())) && (n.getLocation().toLowerCase().contains(location.toLowerCase())))).collect(Collectors.toList());
  }
}
