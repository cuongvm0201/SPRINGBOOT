package vn.techmaster.hellojpa;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vn.techmaster.hellojpa.model.Employer;

@SpringBootApplication
@Transactional
public class HellojpaApplication implements ApplicationRunner{

	@Autowired private EntityManager em;

	public static void main(String[] args) {
		SpringApplication.run(HellojpaApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Faker faker = new Faker();
		for (int i = 0; i < 5; i++) {
			var employer = Employer.builder().name(faker.company().name())
			.email(faker.internet().emailAddress())
			.website("https://" + faker.internet().domainName()).build();
			em.persist(employer);			
		}
		em.flush();		
	}

}
