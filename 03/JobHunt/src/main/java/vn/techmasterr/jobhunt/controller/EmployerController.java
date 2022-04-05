package vn.techmasterr.jobhunt.controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.techmasterr.jobhunt.model.Employer;

@Controller
@RequestMapping("/employer")
public class EmployerController {
        private ConcurrentHashMap<String, Employer> employers;

        public EmployerController() {
                employers = new ConcurrentHashMap<>();
                employers.put("VP-01",
                                new Employer("VPBank", "https://tuyendung.vpbank.com.vn/", "hr@vpbank.talent.vn",
                                                "Hà Nội, Ha Noi, hanoi"));
                employers.put("TCB-01",
                                new Employer("Techcombank", "https://www.techcombankjobs.com/",
                                                "hr.ta@techcombank.com.vn",
                                                "Hà Nội, Ha Noi, hanoi"));
                employers.put("TPB-01",
                                new Employer("TPBank", "https://tuyendung.tpb.vn/tuyen-dung/", "tuyendung@tpb.com.vn",
                                                "Hà Nội, Ha Noi, hanoi"));
        }

        @GetMapping(value = "/testlist", produces = "application/json")
        @ResponseBody
        public List<Employer> getBooks(Model model) {
                return employers.values().stream().toList();
        }

        @GetMapping("/list")
        public String getEmployers(Model model) {
                var listEmployers = employers.values().stream().collect(Collectors.toList());
                model.addAttribute("listEmployers", listEmployers);
                return "list";
        }

        @GetMapping("/add")
        public String getEmployers1(Model model) {
                model.addAttribute("employer", new Employer(null, null, null, null));
                return "add";
        }

        @PostMapping("/add")
        public String insertNewEmployers(Model model, @ModelAttribute("employer") Employer employer) {
                String uuid = UUID.randomUUID().toString();
                Employer newEmployer = new Employer(employer.name(), employer.website(), employer.email(),
                                employer.address());
                employers.put(uuid, newEmployer);
                return "success";
        }
}
