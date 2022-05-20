package vn.techmaster.hellojpa;

import javax.persistence.EntityManager;

import com.github.javafaker.Faker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import vn.techmaster.hellojpa.model.Employer;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class TestEmployer {
  @Autowired private EntityManager em;
  @Test
  public void addEmployer() {
    Faker faker = new Faker();
    var employer = Employer.builder().name(faker.company().name())
			.email(faker.internet().emailAddress())
			.website("https://" + faker.internet().domainName()).build();
			em.persist(employer);
      Long id = employer.getId();
      var emp2 = em.find(Employer.class, id);
      assertThat(employer).isEqualTo(emp2);
  }

  @Test
  public void deleteEmployer() {
    var emp = em.find(Employer.class, 2L);
    em.remove(emp);    
    var emp2 = em.find(Employer.class, 2L);
    assertThat(emp2).isNull();
  }
}
