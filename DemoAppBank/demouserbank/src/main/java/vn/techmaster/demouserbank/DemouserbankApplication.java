package vn.techmaster.demouserbank;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vn.techmaster.demouserbank.model.Account;
import vn.techmaster.demouserbank.model.User;

@SpringBootApplication
public class DemouserbankApplication implements CommandLineRunner {
	@Autowired EntityManager em;
	public static void main(String[] args) {
		SpringApplication.run(DemouserbankApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Account account1 = Account.builder()
		.id("1")
		.account("abc111")
		.hash_pass("123456")
		.balance(50000)
		.build();

		Account account2 = Account.builder()
		.id("2")
		.account("abc222")
		.hash_pass("123456")
		.balance(100000)
		.build();

		Account account3 = Account.builder()
		.id("3")
		.account("abc333")
		.hash_pass("123456")
		.balance(200000)
		.build();

		Account account4 = Account.builder()
		.id("4")
		.account("abc444")
		.hash_pass("123456")
		.balance(10000)
		.build();

		Account account5 = Account.builder()
		.id("5")
		.account("abc555")
		.hash_pass("123456")
		.balance(500000)
		.build();

		em.persist(account1);
		em.persist(account2);
		em.persist(account3);
		em.persist(account4);
		em.persist(account5);
		List<Account> listBob = new ArrayList<>();
		listBob.add(account1);
		listBob.add(account2);
		List<Account> listAlice = new ArrayList<>();
		listAlice.add(account3);
		List<Account> listTom = new ArrayList<>();
		listTom.add(account4);
		List<Account> listSara = new ArrayList<>();
		listSara.add(account5);
		User user1 = User.builder().id("11").name("Bob").address("HaNoi").accounts(listBob).build();
		User user2 = User.builder().id("22").name("Alice").address("HoChiMinh").accounts(listAlice).build();
		User user3 = User.builder().id("33").name("Tom").address("HaiPhong").accounts(listTom).build();
		User user4 = User.builder().id("44").name("Sara").address("DaNang").accounts(listSara).build();
		em.persist(user1);
		em.persist(user2);
		em.persist(user3);
		em.persist(user4);

	}

	

}
