package vn.techmaster.myfirstweb.controller;

import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.techmaster.myfirstweb.model.Book;
import vn.techmaster.myfirstweb.model.Cadao;
import vn.techmaster.myfirstweb.model.ChisoBMI;
import vn.techmaster.myfirstweb.model.ListStudent;
import vn.techmaster.myfirstweb.model.Message;
import vn.techmaster.myfirstweb.model.RandomStr;
import vn.techmaster.myfirstweb.model.Student;

@Controller
@RequestMapping("/")
public class HomeController {
  @GetMapping(value = "/hi", produces = MediaType.TEXT_HTML_VALUE)
  @ResponseBody
  public String hello() {
    return "<h1>Hello World</h1><h3>Cool</h3>";
  }

  @GetMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Book getBook() {
    return new Book("Dế Mèn Phiêu Luu Ky", "Tô Hoài", "1945");
  }

  @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
  @ResponseBody
  public Book book_xml() {
    return new Book("x111", "Dế Mèn Phiêu Lưu Ký", "Tô Hoài");
  }

  @GetMapping("/add/{a}/{b}")
  @ResponseBody
  public int add(@PathVariable("a") int aa, @PathVariable("b") int bb) {
    return aa + bb;
  }

  @GetMapping("/name/{your_name}")
  @ResponseBody
  public String hi(@PathVariable("your_name") String yourName) {
    return "Hi " + yourName;
  }

  @GetMapping("/year/{year}")
  @ResponseBody
  public int getAge(@PathVariable("year") int year) {
    return Calendar.getInstance().get(Calendar.YEAR) - year;
  }

  @GetMapping("/add")
  @ResponseBody
  public int add2(@RequestParam("a") int a, @RequestParam("b") int b) {
    return a + b;
  }

  @PostMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Message echoMessage(@RequestBody Message message) {
    return message;
  }

  // bai 1
  @GetMapping(value = "/random", produces = MediaType.TEXT_HTML_VALUE)
  @ResponseBody
  public String randomString() {
    return RandomStr.randomAlphaNumeric(8);
  }

  // bai 2
  @GetMapping(value = "/quote", produces = MediaType.TEXT_HTML_VALUE)
  @ResponseBody
  public String quoteCadao() {
    return Cadao.randomTucngu();
  }

  // bai 3
  @PostMapping(value = "/bmi", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public double chisoBMI(@RequestBody ChisoBMI chisobmi) {
    return chisobmi.getWeight() / (chisobmi.getHeight() * chisobmi.getHeight());
  }

  // bai 4
  ArrayList<Student> listAllStudent = ListStudent.infoStudent();
  @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ArrayList<Student> danhsachSinhvien(){
    return listAllStudent;
  }

  @PostMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE )
  @ResponseBody
  public Student themmoiSinhvien(@RequestBody Student student){
    listAllStudent.add(student);
    return student;
  }
}
  


