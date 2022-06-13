package vn.techmaster.relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.techmaster.relation.service.inheritance.mappedsuperclass.ProductService;
import vn.techmaster.relation.service.inheritance.singletable.EletronicsService;
import vn.techmaster.relation.service.inheritance.tableperclass.AnimalService;
import vn.techmaster.relation.service.manymany.ArticleTagService;
import vn.techmaster.relation.service.manymany.StudentSubjectService;
import vn.techmaster.relation.service.onemany.bidirection.CustomerAddressService;
import vn.techmaster.relation.service.onemany.bidirection.PostService;
import vn.techmaster.relation.service.onemany.bidirection.ProfessorDepartmentService;
import vn.techmaster.relation.service.onemany.unidirection.ProductCategoryService;

import vn.techmaster.relation.service.selfreference.EmployeeService;
import vn.techmaster.relation.service.selfreference.FamilyService;
import vn.techmaster.relation.service.uuidpk.StoryService;

@Component
public class AppRunner implements CommandLineRunner {
  
  @Autowired private PostService postService;

  @Autowired private ProductCategoryService productCategoryService;

  @Autowired private CustomerAddressService personAddressService;
  
  @Autowired private ProfessorDepartmentService profDeptService;
  
  @Autowired private StudentSubjectService studentSubjectService;

  @Autowired private ArticleTagService articleTagService;

  @Autowired private EmployeeService employeeService;

  @Autowired private FamilyService familyService;

  @Autowired private ProductService productService;

  @Autowired private EletronicsService electronicsService;

  @Autowired private AnimalService animalService;

  @Autowired private StoryService storyService;

  @Override
  public void run(String... args) throws Exception {
    // userService.generateUsers();

    storyService.generateStory();
    
    postService.createPostAndComments();

    productCategoryService.generateCategoryProduct();

    personAddressService.generateCustomerAddress();

    profDeptService.generateProfessorDepartment();

    studentSubjectService.generateStudentSubject();

    articleTagService.generateArticleTag();

    employeeService.generateEmployee();

    familyService.generateFamilyTree();

    productService.generateProduct();

    electronicsService.generateEletronicsProducts();

    animalService.generateAnimals();
  }
  
}
