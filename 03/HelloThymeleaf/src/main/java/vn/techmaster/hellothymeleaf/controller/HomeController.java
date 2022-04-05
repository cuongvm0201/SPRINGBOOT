package vn.techmaster.hellothymeleaf.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.techmaster.hellothymeleaf.model.Book;
import vn.techmaster.hellothymeleaf.model.Student;

@Controller
@RequestMapping("/")
public class HomeController {
  @GetMapping
  public String homePage() {
    return "index";
  }

  @GetMapping(value="/json", produces = "application/json")
  @ResponseBody
  public Book returnBook() {
    return new Book("De men phieu luu ky", "To Hoai");
  }

  @GetMapping(value="/xml", produces = MediaType.APPLICATION_XML_VALUE)
  @ResponseBody
  public Book returnBookXML() {
    return new Book("De men phieu luu ky", "To Hoai");
  }

  @GetMapping("/book")
  public String getBook(Model model){
    model.addAttribute("book",new Book("De men phieu luu ky", "To Hoai"));
    model.addAttribute("student", new Student("Tom", 8.6f));
    return "book";
  }

  @GetMapping("/students")
  public String students(Model model){
    var students = List.of(new Student("Tom", 8.6f),new Student("Jane", 7.5f),new Student("Alice", 9f),new Student("Bob", 6f));
    model.addAttribute("students", students);
    return "students";
  }
}
